package com.haphap.app.data.remote.datasource.api.passcard

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.passcard.PassCardDetailResponseDto
import com.haphap.app.data.remote.dto.passcard.PassCardStorageResponseDto
import com.haphap.app.data.remote.dto.passcard.PassCardUnconfirmedResponseDto

interface PassCardDataSource {
    suspend fun getPassCardStorage(): BaseResponse<PassCardStorageResponseDto>

    suspend fun getPassCardDetail(registrationId: Int): BaseResponse<PassCardDetailResponseDto>

    suspend fun getUnconfirmedPassCards(): BaseResponse<PassCardUnconfirmedResponseDto>

    suspend fun patchPassCardConfirm(registrationId: Int)
}