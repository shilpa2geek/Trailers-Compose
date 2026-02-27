package com.geek.cinemax.data.repository

import com.geek.cinemax.data.remote.api.MovieApiService
import com.geek.cinemax.data.remote.api.TvApiService
import com.geek.cinemax.data.remote.model.MovieApiResponse
import com.geek.cinemax.data.remote.model.TvApiResponse
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val tvApiService: TvApiService,
    private val movieApiService: MovieApiService
) {
    suspend fun searchMovies(
        query: String,
        nextPage: Long = 1L
    ): MovieApiResponse {
        return try {
            val response = movieApiService.searchMovies(
                query = query,
                page = nextPage
            )
            response
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
    }

    suspend fun searchTvShows(
        query: String,
        nextPage: Long
    ): TvApiResponse {
        return try {
            val response = tvApiService.searchTvList(
                query = query,
                page = nextPage
            )
            response
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
    }
}