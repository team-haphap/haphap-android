package com.haphap.app.data.repository.impl.viewcount

import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.remote.datasource.api.viewcount.ViewCountDataSource
import com.haphap.app.data.remote.dto.checkNullData
import com.haphap.app.data.repository.api.viewcount.ViewCountRepository
import jakarta.inject.Inject

class ViewCountRepositoryImpl @Inject constructor(
    private val viewCountDataSource: ViewCountDataSource,
) : ViewCountRepository {
    override suspend fun patchViewCount(postingId: Int): Result<Unit> =
        suspendRunCatching {
            viewCountDataSource.patchViewCount(postingId).checkNullData()
        }

    override suspend fun patchRecordView(postingId: Int): Result<Unit> =
        suspendRunCatching {
            viewCountDataSource.patchRecordView(postingId)
        }
}
