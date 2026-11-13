package com.haphap.app.data.repository.impl.scrap

import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.mapper.scrap.toModels
import com.haphap.app.data.model.scrap.ScrapModel
import com.haphap.app.data.remote.datasource.api.scrap.ScrapDataSource
import com.haphap.app.data.remote.dto.checkData
import com.haphap.app.data.repository.api.scrap.ScrapRepository
import javax.inject.Inject

class ScrapRepositoryImpl @Inject constructor(
    private val scrapDataSource: ScrapDataSource,
) : ScrapRepository {
    override suspend fun getScraps(): Result<List<ScrapModel>> =
        suspendRunCatching {
            scrapDataSource.getScraps().checkData().toModels()
        }

    override suspend fun postScrap(postingId: Int): Result<Boolean> =
        suspendRunCatching {
            scrapDataSource.postScrap(postingId).checkData().isScrapped
        }

    override suspend fun deleteScrap(postingId: Int): Result<Boolean> =
        suspendRunCatching {
            scrapDataSource.deleteScrap(postingId).checkData().isScrapped
        }
}
