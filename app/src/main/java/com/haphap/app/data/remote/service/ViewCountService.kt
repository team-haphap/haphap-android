package com.haphap.app.data.remote.service

import com.haphap.app.data.remote.dto.BaseResponse
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ViewCountService {
    @PATCH("api/v1/postings/{postingId}/card-clicks")
    suspend fun patchViewCount(
        @Path("postingId") postingId: Int,
    ): BaseResponse<Unit>

    @PATCH("api/v1/postings/{postingId}/views")
    suspend fun patchRecordView(
        @Path("postingId") postingId: Int,
    ): BaseResponse<Unit>
}
