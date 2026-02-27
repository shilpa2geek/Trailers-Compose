package com.geek.cinemax.data.remote.model

import com.google.gson.annotations.SerializedName

data class Cast(
    val id: Long,
    val name: String,
    val character: String,
    @SerializedName("profile_path")
    val profilePath: String
)