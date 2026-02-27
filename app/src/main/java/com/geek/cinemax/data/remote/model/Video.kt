package com.geek.cinemax.data.remote.model

import com.geek.cinemax.AppConstants

data class Video(
    val id: String,
    val key: String
)

fun Video.youtubeUrl() = String.Companion.format(AppConstants.YOUTUBE_THUMBNAIL_URL, this.key)