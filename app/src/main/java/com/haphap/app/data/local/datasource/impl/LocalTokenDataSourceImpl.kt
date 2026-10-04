package com.haphap.app.data.local.datasource.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.haphap.app.core.security.TokenCipher
import com.haphap.app.data.local.datasource.api.LocalTokenDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalTokenDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val tokenCipher: TokenCipher,
) : LocalTokenDataSource {

    override suspend fun getAccessToken(): String? = getDecryptedToken(ACCESS_TOKEN)

    override suspend fun getRefreshToken(): String? = getDecryptedToken(REFRESH_TOKEN)

    override suspend fun setAccessToken(accessToken: String) {
        setEncryptedToken(ACCESS_TOKEN, accessToken)
    }

    override suspend fun setRefreshToken(refreshToken: String) {
        setEncryptedToken(REFRESH_TOKEN, refreshToken)
    }

    override suspend fun clearTokens() {
        dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN)
            prefs.remove(REFRESH_TOKEN)
        }
    }

    /**
     * 저장된 토큰을 복호화하여 반환합니다.
     * 기존 평문 토큰이거나 복호화에 실패한 경우(키 유실 등) 토큰을 모두 삭제하고 null 을 반환하여 재로그인을 유도합니다.
     */
    private suspend fun getDecryptedToken(key: Preferences.Key<String>): String? {
        val snapshot = dataStore.data.firstOrNull() ?: return null
        val stored = snapshot[key] ?: return null

        val decrypted = withContext(Dispatchers.IO) { tokenCipher.decrypt(stored, key.name) }
        if (decrypted == null) clearTokensIfUnchanged(snapshot)
        return decrypted
    }

    /**
     * access/refresh 저장값이 모두 [snapshot] 과 같은 경우에만 토큰을 삭제합니다.
     * 복호화하는 사이 새로 저장된 토큰(반대 토큰 포함)을 지우지 않도록 비교와 삭제를 한 트랜잭션에서 처리합니다.
     */
    private suspend fun clearTokensIfUnchanged(snapshot: Preferences) {
        dataStore.edit { prefs ->
            if (prefs[ACCESS_TOKEN] == snapshot[ACCESS_TOKEN] &&
                prefs[REFRESH_TOKEN] == snapshot[REFRESH_TOKEN]
            ) {
                prefs.remove(ACCESS_TOKEN)
                prefs.remove(REFRESH_TOKEN)
            }
        }
    }

    /**
     * 토큰을 암호화하여 저장합니다.
     * 암호화(키 오류 판정 → 키 삭제 → 생성 → 재암호화 포함)와 저장을 한 트랜잭션에서 처리하여,
     * 이전 키로 만든 암호문이 키 교체 후 늦게 저장되지 않도록 합니다.
     * 키가 재생성된 경우 이전 키로 암호화된 기존 세션을 정리하고 요청된 토큰만 저장합니다.
     */
    private suspend fun setEncryptedToken(key: Preferences.Key<String>, token: String) {
        withContext(Dispatchers.IO) {
            dataStore.edit { prefs ->
                val encrypted = tokenCipher.encrypt(token, key.name)
                if (encrypted.isKeyRecreated) {
                    prefs.remove(ACCESS_TOKEN)
                    prefs.remove(REFRESH_TOKEN)
                }
                prefs[key] = encrypted.value
            }
        }
    }

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        private val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
    }
}
