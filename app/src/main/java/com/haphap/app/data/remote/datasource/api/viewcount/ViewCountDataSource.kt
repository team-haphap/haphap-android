package com.haphap.app.data.remote.datasource.api.viewcount

import com.haphap.app.data.remote.dto.BaseResponse

interface ViewCountDataSource {
    suspend fun patchViewCount(postingId: Int): BaseResponse<Unit>

    suspend fun patchRecordView(postingId: Int): BaseResponse<Unit>
}