package com.haphap.app.data.repository.impl.register

import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.mapper.register.toModel
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterProcessModel
import com.haphap.app.data.remote.datasource.api.register.RegisterDataSource
import com.haphap.app.data.remote.dto.checkData
import com.haphap.app.data.repository.api.register.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val postingDataSource: RegisterDataSource,
) : RegisterRepository {

    override suspend fun getPostingNames(): Result<List<RegisterDropDownItemModel>> =
        suspendRunCatching {
            postingDataSource.getPostingNames().checkData().postings.map { it.toModel() }
        }

    override suspend fun getPostingStages(postingId: Int): Result<List<RegisterProcessModel>> =
        suspendRunCatching {
            postingDataSource.getPostingStages(postingId).checkData().stages.map { it.toModel() }
        }
}