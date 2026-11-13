package com.haphap.app.data.repository.api.scrap

import com.haphap.app.data.model.scrap.ScrapModel

interface ScrapRepository {
    suspend fun getScraps(): Result<List<ScrapModel>>

    suspend fun postScrap(postingId: Int): Result<Boolean>

    suspend fun deleteScrap(postingId: Int): Result<Boolean>
}
