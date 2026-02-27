package com.geek.cinemax.module

import android.content.Context
import androidx.room.Room
import com.geek.cinemax.data.local.MovieDatabase
import com.geek.cinemax.data.local.dao.MovieDao
import com.geek.cinemax.data.local.dao.MovieRemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDbModule {
    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext appContext: Context): MovieDatabase {
        return Room.databaseBuilder(
            appContext,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }

    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao
    }

    @Provides
    fun provideMovieRemoteKeyDao(database: MovieDatabase): MovieRemoteKeyDao {
        return database.remoteKeyDao
    }
}