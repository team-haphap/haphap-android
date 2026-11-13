package com.haphap.app.data.repository.impl.passcard

import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.mapper.passcard.toModel
import com.haphap.app.data.model.passcard.PassCardDetailModel
import com.haphap.app.data.model.passcard.PassCardStorageModel
import com.haphap.app.data.remote.datasource.api.passcard.PassCardDataSource
import com.haphap.app.data.remote.dto.checkData
import com.haphap.app.data.repository.api.passcard.PassCardRepository
import jakarta.inject.Inject

class PassCardRepositoryImpl @Inject constructor(
    private val passCardDataSource: PassCardDataSource,
) : PassCardRepository {
    override suspend fun getPassCardStorage(): Result<List<PassCardStorageModel>> =
        suspendRunCatching {
            passCardDataSource.getPassCardStorage()
                .checkData()
                .cards
                .map { it.toModel() }
        }

    override suspend fun getPassCardDetail(registrationId: Int): Result<PassCardDetailModel> =
        suspendRunCatching {
            passCardDataSource.getPassCardDetail(registrationId)
                .checkData()
                .toModel()
        }
}