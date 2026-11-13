package com.haphap.app.data.repository.api.passcard

import com.haphap.app.data.model.passcard.PassCardDetailModel
import com.haphap.app.data.model.passcard.PassCardStorageModel

interface PassCardRepository {
    suspend fun getPassCardStorage(): Result<List<PassCardStorageModel>>
    suspend fun getPassCardDetail(registrationId: Int): Result<PassCardDetailModel>
}