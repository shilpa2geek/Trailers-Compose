package com.geek.cinemax.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.geek.cinemax.data.local.converter.CreditResponseTypeConverter
import com.geek.cinemax.data.local.converter.GenreListTypeConverter
import com.geek.cinemax.data.local.converter.LongListTypeConverter
import com.geek.cinemax.data.local.converter.TvApiResponseTypeConverter
import com.geek.cinemax.data.local.converter.VideoResponseTypeConverter
import com.geek.cinemax.data.local.dao.TvDao
import com.geek.cinemax.data.local.dao.TvRemoteKeyDao
import com.geek.cinemax.data.local.entity.TvEntity
import com.geek.cinemax.data.local.entity.TvRemoteKey

@Database(
    entities = [TvEntity::class, TvRemoteKey::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    GenreListTypeConverter::class,
    LongListTypeConverter::class,
    CreditResponseTypeConverter::class,
    VideoResponseTypeConverter::class,
    TvApiResponseTypeConverter::class
)
abstract class TvDatabase: RoomDatabase() {
    abstract val tvDao: TvDao
    abstract val remoteKeyDao: TvRemoteKeyDao
}