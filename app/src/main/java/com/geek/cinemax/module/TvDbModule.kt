package com.geek.cinemax.module

import android.content.Context
import androidx.room.Room
import com.geek.cinemax.data.local.TvDatabase
import com.geek.cinemax.data.local.dao.TvDao
import com.geek.cinemax.data.local.dao.TvRemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TvDbModule {
    @Provides
    @Singleton
    fun provideTvDatabase(@ApplicationContext appContext: Context): TvDatabase {
        return Room.databaseBuilder(
            appContext,
            TvDatabase::class.java,
            "tv_database"
        ).build()
    }

    @Provides
    fun provideTvDao(database: TvDatabase): TvDao {
        return database.tvDao
    }

    @Provides
    fun provideTvRemoteKeyDao(database: TvDatabase): TvRemoteKeyDao {
        return database.remoteKeyDao
    }
}