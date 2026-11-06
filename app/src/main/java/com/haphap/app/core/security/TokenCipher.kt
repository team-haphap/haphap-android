package com.haphap.app.core.security

/**
 * 로컬에 저장되는 토큰을 암복호화합니다.
 *
 * [tokenName] 은 암호문에 인증 데이터(AAD)로 묶이므로,
 * 다른 이름으로 암호화된 토큰(예: access ↔ refresh 바꿔치기)은 복호화되지 않습니다.
 */
interface TokenCipher {
    /**
     * 평문을 암호화하여 저장 가능한 문자열로 반환합니다.
     * 키를 쓸 수 없어 재생성한 경우 [EncryptedToken.isKeyRecreated] 가 true 이며,
     * 이전 키로 암호화된 값은 더 이상 복호화되지 않으므로 호출부에서 기존 값을 정리해야 합니다.
     *
     * @throws Exception 암호화에 실패한 경우
     */
    fun encrypt(plainText: String, tokenName: String): EncryptedToken

    /**
     * [encrypt]로 생성된 문자열을 복호화합니다.
     *
     * @return 복호화된 평문, 암호문 형식이 아니거나(기존 평문 등) 복호화에 실패한 경우 null
     */
    fun decrypt(cipherText: String, tokenName: String): String?
}

/**
 * [TokenCipher.encrypt] 의 결과입니다.
 *
 * @property value 저장할 암호문
 * @property isKeyRecreated 암호화 과정에서 키를 재생성했는지 여부
 */
data class EncryptedToken(
    val value: String,
    val isKeyRecreated: Boolean,
)
