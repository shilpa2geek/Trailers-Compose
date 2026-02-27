package com.geek.cinemax.data.local.converter

import androidx.room.TypeConverter
import com.geek.cinemax.data.remote.model.VideoApiResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class VideoResponseTypeConverter {
    @TypeConverter
    fun fromString(value: String): VideoApiResponse {
        val listType = object : TypeToken<VideoApiResponse>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: VideoApiResponse): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}