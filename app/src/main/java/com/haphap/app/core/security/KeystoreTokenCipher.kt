package com.haphap.app.core.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.util.Base64
import timber.log.Timber
import java.security.KeyStore
import java.security.UnrecoverableEntryException
import java.security.UnrecoverableKeyException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Android Keystore 의 AES/GCM 키로 토큰을 암복호화합니다.
 *
 * 저장 형식: [CIPHER_PREFIX] + Base64(IV(12byte) + 암호문 + 인증 태그(16byte))
 */
@Singleton
class KeystoreTokenCipher @Inject constructor() : TokenCipher {

    private val keyStore: KeyStore by lazy {
        KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
    }

    private val keyLock = Any()

    /**
     * 키를 더 이상 쓸 수 없는 경우([isKeyUnusable])에만 키를 재생성하여 한 번 더 시도하고, 그 외 예외는 그대로 던집니다.
     * 실패 → 삭제 → 생성 → 재시도 전체를 하나의 락으로 묶어, 동시 요청이 서로 새로 만든 키를 지우지 않도록 합니다.
     */
    override fun encrypt(plainText: String, tokenName: String): EncryptedToken = synchronized(keyLock) {
        try {
            EncryptedToken(encryptWith(getKey() ?: createKey(), plainText, tokenName), isKeyRecreated = false)
        } catch (e: Exception) {
            if (!isKeyUnusable(e)) throw e

            Timber.w(e, "Token key is unusable. Regenerating key.")
            deleteKey()
            EncryptedToken(encryptWith(createKey(), plainText, tokenName), isKeyRecreated = true)
        }
    }

    override fun decrypt(cipherText: String, tokenName: String): String? {
        if (!cipherText.startsWith(CIPHER_PREFIX)) {
            Timber.w("Token is not in encrypted format.")
            return null
        }

        return try {
            val decoded = Base64.decode(cipherText.removePrefix(CIPHER_PREFIX), Base64.NO_WRAP)
            require(decoded.size >= IV_LENGTH + GCM_TAG_LENGTH / Byte.SIZE_BITS) {
                "Encrypted token is too short."
            }
            val key = requireNotNull(getKey()) { "Token key not found." }
            val cipher = Cipher.getInstance(TRANSFORMATION).apply {
                init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(GCM_TAG_LENGTH, decoded, 0, IV_LENGTH))
                updateAAD(tokenName.toByteArray(Charsets.UTF_8))
            }
            String(cipher.doFinal(decoded, IV_LENGTH, decoded.size - IV_LENGTH), Charsets.UTF_8)
        } catch (e: Exception) {
            Timber.w(e, "Token decryption failed.")
            null
        }
    }

    private fun encryptWith(key: SecretKey, plainText: String, tokenName: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE, key)
            updateAAD(tokenName.toByteArray(Charsets.UTF_8))
        }
        val encrypted = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
        return CIPHER_PREFIX + Base64.encodeToString(cipher.iv + encrypted, Base64.NO_WRAP)
    }

    private fun getKey(): SecretKey? =
        (keyStore.getEntry(KEY_ALIAS, null) as? KeyStore.SecretKeyEntry)?.secretKey

    private fun createKey(): SecretKey {
        val spec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(KEY_SIZE)
            .build()

        return KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
            .apply { init(spec) }
            .generateKey()
    }

    private fun deleteKey() {
        try {
            keyStore.deleteEntry(KEY_ALIAS)
        } catch (e: Exception) {
            Timber.w(e, "Token key deletion failed.")
            throw e
        }
    }

    /**
     * 키 자체를 더 이상 쓸 수 없음을 나타내는 예외인지 감싸진 원인(cause)까지 확인합니다.
     */
    private fun isKeyUnusable(throwable: Throwable): Boolean =
        generateSequence(throwable) { it.cause }
            .take(MAX_CAUSE_DEPTH)
            .any {
                it is KeyPermanentlyInvalidatedException ||
                    it is UnrecoverableKeyException ||
                    it is UnrecoverableEntryException
            }

    companion object {
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val KEY_ALIAS = "haphap_token_key"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val KEY_SIZE = 256
        private const val IV_LENGTH = 12
        private const val GCM_TAG_LENGTH = 128
        private const val CIPHER_PREFIX = "enc:v1:"
        private const val MAX_CAUSE_DEPTH = 10
    }
}
