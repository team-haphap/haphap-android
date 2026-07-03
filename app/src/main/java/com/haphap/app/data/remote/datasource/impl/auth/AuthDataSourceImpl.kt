package com.haphap.app.data.remote.datasource.impl.auth

import com.haphap.app.data.remote.datasource.api.auth.AuthDataSource
import com.haphap.app.data.remote.dto.ErrorResponseDto
import com.haphap.app.data.remote.dto.auth.KakaoLoginRequestDto
import com.haphap.app.data.remote.dto.auth.KakaoLoginResponseDto
import com.haphap.app.data.remote.exception.auth.AuthApiException
import com.haphap.app.data.remote.service.auth.AuthService
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService,
    private val json: Json,
) : AuthDataSource {

    override suspend fun kakaoLogin(accessToken: String): KakaoLoginResponseDto {
        return try {
            val response = authService.kakaoLogin(KakaoLoginRequestDto(accessToken = accessToken))
            response.data ?: throw AuthApiException(
                code = response.code,
                message = response.message,
            )
        } catch (e: HttpException) {
            throw parseErrorResponse(e)
        }
    }

    private fun parseErrorResponse(e: HttpException): AuthApiException {
        val errorBody = e.response()?.errorBody()?.string()
        return runCatching {
            val parsed = json.decodeFromString<ErrorResponseDto>(errorBody.orEmpty())
            AuthApiException(code = parsed.code, message = parsed.message)
        }.getOrElse {
            AuthApiException(code = "UNKNOWN_ERROR", message = "로그인 처리 중 오류가 발생했습니다.")
        }
    }
}