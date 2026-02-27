package com.geek.cinemax

import com.geek.cinemax.data.local.entity.MovieEntity
import com.geek.cinemax.data.local.entity.TvEntity
import com.geek.cinemax.data.remote.model.MovieApiResponse
import com.geek.cinemax.data.remote.model.TvApiResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ContentTest {
    fun getPopularMovies(): List<MovieEntity> {
        val reader = MockResponseFileReader("movie_list_api_response.json")
        val listType = object : TypeToken<MovieApiResponse>() {}.type
        return Gson().fromJson<MovieApiResponse?>(reader.content, listType).results
    }

    fun getTvList(): List<TvEntity> {
        val reader = MockResponseFileReader("tv_list_api_response.json")
        val listType = object : TypeToken<TvApiResponse>() {}.type
        return Gson().fromJson<TvApiResponse?>(reader.content, listType).results
    }
}