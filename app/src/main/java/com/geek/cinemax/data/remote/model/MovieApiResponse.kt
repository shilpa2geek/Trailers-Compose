package com.geek.cinemax.data.remote.model

import com.geek.cinemax.data.local.entity.MovieEntity
import com.google.gson.annotations.SerializedName

data class MovieApiResponse(
    val page: Long,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("total_results")
    val totalResults: Long,
    val results: List<MovieEntity>
)
