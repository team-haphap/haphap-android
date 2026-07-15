package com.haphap.app.data.remote.datasource.api.joblist

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.home.HomeAnnouncementsResponseDto
import com.haphap.app.data.remote.dto.home.HomeBannerListDto
import com.haphap.app.data.remote.dto.home.HomePostingsResponseDto
import com.haphap.app.data.remote.dto.home.HomeTodayResponseDto
import com.haphap.app.data.remote.dto.joblist.JobListResponseDto

interface JobListDataSource {
    suspend fun getJobList(category: List<String>?): BaseResponse<JobListResponseDto>
}
