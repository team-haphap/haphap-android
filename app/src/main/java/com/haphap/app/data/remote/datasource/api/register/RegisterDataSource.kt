package com.haphap.app.data.remote.datasource.api.register

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.register.RegisterNameListResponseDto
import com.haphap.app.data.remote.dto.register.RegisterRequestDto
import com.haphap.app.data.remote.dto.register.RegisterStageListResponseDto
import com.haphap.app.data.remote.dto.register.RegistrationCheckRequestDto
import com.haphap.app.data.remote.dto.register.RegistrationResponseDto
import com.haphap.app.data.remote.dto.register.VerificationImageUploadResponseDto
import okhttp3.MultipartBody

interface RegisterDataSource {
    suspend fun getRegisterPostNames(): BaseResponse<RegisterNameListResponseDto>
    suspend fun getRegisterPostStages(postingId: Int): BaseResponse<RegisterStageListResponseDto>
    suspend fun postRegister(request: RegisterRequestDto): BaseResponse<RegistrationResponseDto>
    suspend fun getRegisterCheck(postingId: Int, stageId: Int, request: RegistrationCheckRequestDto): BaseResponse<Unit>
    suspend fun postVerificationImage(file: MultipartBody.Part): BaseResponse<VerificationImageUploadResponseDto>
}
