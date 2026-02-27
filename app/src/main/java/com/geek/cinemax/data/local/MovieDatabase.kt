package com.geek.cinemax.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.geek.cinemax.data.local.converter.CreditResponseTypeConverter
import com.geek.cinemax.data.local.converter.GenreListTypeConverter
import com.geek.cinemax.data.local.converter.LongListTypeConverter
import com.geek.cinemax.data.local.converter.MovieApiResponseTypeConverter
import com.geek.cinemax.data.local.converter.VideoResponseTypeConverter
import com.geek.cinemax.data.local.dao.MovieDao
import com.geek.cinemax.data.local.dao.MovieRemoteKeyDao
import com.geek.cinemax.data.local.entity.MovieEntity
import com.geek.cinemax.data.local.entity.MovieRemoteKey

@Database(
    entities = [MovieEntity::class, MovieRemoteKey::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    GenreListTypeConverter::class,
    LongListTypeConverter::class,
    CreditResponseTypeConverter::class,
    VideoResponseTypeConverter::class,
    MovieApiResponseTypeConverter::class
)
abstract class MovieDatabase: RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val remoteKeyDao: MovieRemoteKeyDao
}