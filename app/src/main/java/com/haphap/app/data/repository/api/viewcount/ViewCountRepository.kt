package com.haphap.app.data.repository.api.viewcount

interface ViewCountRepository {
    suspend fun patchViewCount(postingId: Int): Result<Unit>

    suspend fun patchRecordView(postingId: Int): Result<Unit>
}
