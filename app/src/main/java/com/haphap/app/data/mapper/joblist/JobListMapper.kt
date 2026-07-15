package com.haphap.app.data.mapper.joblist

import com.haphap.app.data.model.list.JobItemModel
import com.haphap.app.data.remote.dto.joblist.JobItemDto
import com.haphap.app.data.remote.dto.joblist.JobListResponseDto

fun JobListResponseDto.toModel(): List<JobItemModel> = postings.map { it.toModel() }

private fun JobItemDto.toModel(): JobItemModel =
    JobItemModel(
        id = id,
        imageUrl = imageUrl,
        category = category,
        stage = nextStage ?: "",
        dDay = daysUntilNextStage,
        title = companyName,
        content = title,
    )
