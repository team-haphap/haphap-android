package com.haphap.app.data.remote.datasource.impl.viewcount

import com.haphap.app.data.remote.datasource.api.viewcount.ViewCountDataSource
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.service.ViewCountService
import javax.inject.Inject

class ViewCountDataSourceImpl @Inject constructor(
    private val viewCountService: ViewCountService,
) : ViewCountDataSource {
    override suspend fun patchViewCount(postingId: Int): BaseResponse<Unit> {
        return viewCountService.patchViewCount(postingId)
    }

    override suspend fun patchRecordView(postingId: Int): BaseResponse<Unit> =
        viewCountService.patchRecordView(postingId)
}