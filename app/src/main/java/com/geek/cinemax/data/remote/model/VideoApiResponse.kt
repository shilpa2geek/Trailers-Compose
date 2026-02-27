package com.geek.cinemax.data.remote.model

import com.google.gson.annotations.SerializedName

data class VideoApiResponse(
    @SerializedName("results")
    val videos: List<Video>
)