package com.haphap.app.data.remote.datasource.impl.passcard

import com.haphap.app.data.remote.datasource.api.passcard.PassCardDataSource
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.passcard.PassCardDetailResponseDto
import com.haphap.app.data.remote.dto.passcard.PassCardStorageResponseDto
import com.haphap.app.data.remote.service.PassCardService
import jakarta.inject.Inject

class PassCardDataSourceImpl @Inject constructor(
    private val passCardService: PassCardService,
) : PassCardDataSource {
    override suspend fun getPassCardStorage(): BaseResponse<PassCardStorageResponseDto> {
        return passCardService.getPassCardStorage()
    }

    override suspend fun getPassCardDetail(registrationId: Int): BaseResponse<PassCardDetailResponseDto> {
        return passCardService.getPassCardDetail(registrationId)
    }
}