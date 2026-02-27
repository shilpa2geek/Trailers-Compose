package com.geek.cinemax.data.remote.model

import com.geek.cinemax.data.local.entity.TvEntity
import com.google.gson.annotations.SerializedName

data class TvApiResponse(
    val page: Long,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("total_results")
    val totalResults: Long,
    val results: List<TvEntity>
)
