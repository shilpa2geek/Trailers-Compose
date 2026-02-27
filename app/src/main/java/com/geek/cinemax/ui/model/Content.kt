package com.geek.cinemax.ui.model

import androidx.compose.ui.graphics.Color
import com.geek.cinemax.AppConstants
import com.geek.cinemax.AppConstants.genreColors
import com.geek.cinemax.data.local.entity.MovieEntity
import com.geek.cinemax.data.local.entity.TvEntity
import com.geek.cinemax.data.remote.model.Genre
import com.geek.cinemax.data.remote.model.Video
import com.geek.cinemax.utils.getFormattedDate
import java.util.*

data class Content(
    val id: Long,
    val title: String,
    val description: String,
    val posterUrl: String,
    val backdropUrl: String,
    val genres: List<Genre>,
    val trailers: List<Video>,
    val status: String,
    val durationInMins: String,
    val cast: List<Artist>,
    val crew: List<Artist>,
    val similarMovies: List<SimilarContent>,
    val genreBgColor: Color
)

data class SimilarContent(
    val id: Long,
    val posterUrl: String
)

fun MovieEntity.toSimilarContent() = SimilarContent(
    id = this.remoteId,
    posterUrl = getPosterUrl(this.posterPath)
)

fun MovieEntity.toContent() = Content(
    id = this.remoteId,
    title = this.title,
    description = this.overview,
    posterUrl = getPosterUrl(this.posterPath),
    backdropUrl = getBackdropUrl(this.backdropPath),
    genres = this.genres ?: emptyList(),
    trailers = this.videoApiResponse?.videos ?: emptyList(),
    status = this.status ?: "",
    durationInMins = this.getDuration(),
    cast = this.credits?.cast?.map { it.toArtist() } ?: emptyList(),
    crew = this.credits?.crew?.map { it.toArtist() } ?: emptyList(),
    similarMovies = this.similarMoviesApiResponse?.results?.map { it.toSimilarContent() } ?: emptyList(),
    genreBgColor = genreColors[Random().nextInt(genreColors.size)]
)

fun TvEntity.toSimilarContent() = SimilarContent(
    id = this.remoteId,
    posterUrl = getPosterUrl(this.posterPath)
)

fun TvEntity.toContent() = Content(
    id = this.remoteId,
    title = this.title,
    description = this.overview,
    posterUrl = getPosterUrl(this.posterPath),
    backdropUrl = getBackdropUrl(this.backdropPath),
    genres = this.genres ?: emptyList(),
    trailers = this.videoApiResponse?.videos ?: emptyList(),
    status = this.status ?: "",
    durationInMins = this.releaseDate.getFormattedDate(),
    cast = this.credits?.cast?.map { it.toArtist() } ?: emptyList(),
    crew = this.credits?.crew?.map { it.toArtist() } ?: emptyList(),
    similarMovies = this.similarTvApiResponse?.results?.map { it.toSimilarContent() } ?: emptyList(),
    genreBgColor = genreColors[Random().nextInt(genreColors.size)]
)

private fun MovieEntity.getDuration() = if (AppConstants.MOVIE_STATUS_RELEASED == status)
    String.format("%s mins", runtime.toString()) else releaseDate.getFormattedDate()

private fun getPosterUrl(posterPath: String?) = String.format(AppConstants.IMAGE_URL, posterPath)
private fun getBackdropUrl(backdropPath: String?) = String.format(AppConstants.IMAGE_URL, backdropPath)
