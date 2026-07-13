package com.haphap.app.data.repository.api.register

import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterProcessModel

interface RegisterRepository {
    suspend fun getPostingNames(): Result<List<RegisterDropDownItemModel>>
    suspend fun getPostingStages(postingId: Int): Result<List<RegisterProcessModel>>
}