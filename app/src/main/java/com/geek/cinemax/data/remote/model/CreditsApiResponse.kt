package com.geek.cinemax.data.remote.model

data class CreditsApiResponse(
    val cast: List<Cast>,
    val crew: List<Crew>
)