package com.geek.cinemax.ui.model

import com.geek.cinemax.data.remote.model.Cast
import com.geek.cinemax.data.remote.model.Crew
import com.geek.cinemax.AppConstants

data class Artist(
    val id: Long,
    val name: String,
    val job: String,
    val imageUrl: String
)

fun Cast.toArtist() = Artist(
    id = this.id,
    name = this.name,
    job = this.character,
    imageUrl = String.Companion.format(AppConstants.IMAGE_URL, profilePath)
)

fun Crew.toArtist() = Artist(
    id = this.id,
    name = this.name,
    job = this.job,
    imageUrl = String.Companion.format(AppConstants.IMAGE_URL, profilePath)
)
