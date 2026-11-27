package com.haphap.app.data.repository.api.passcard

import com.haphap.app.data.model.passcard.PassCardDetailModel
import com.haphap.app.data.model.passcard.PassCardStorageModel
import com.haphap.app.data.model.passcard.PassCardUnconfirmedModel

interface PassCardRepository {
    suspend fun getPassCardStorage(): Result<List<PassCardStorageModel>>
    suspend fun getPassCardDetail(registrationId: Int): Result<PassCardDetailModel>
    suspend fun getUnconfirmedPassCards(): Result<List<PassCardUnconfirmedModel>>
    suspend fun patchPassCardConfirm(registrationId: Int): Result<Unit>
}