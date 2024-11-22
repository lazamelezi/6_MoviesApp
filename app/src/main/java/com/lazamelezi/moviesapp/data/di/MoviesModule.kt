package com.lazamelezi.moviesapp.data.di

import com.lazamelezi.moviesapp.data.local.FavoriteMoviesLocalDataSourceImpl
import com.lazamelezi.moviesapp.data.local.IFavoriteMoviesLocalDataSource
import com.lazamelezi.moviesapp.data.remote.IMoviesRemoteDataSource
import com.lazamelezi.moviesapp.data.remote.IMoviesService
import com.lazamelezi.moviesapp.data.remote.MoviesRemoteDataSource
import com.lazamelezi.moviesapp.data.remote.MoviesService
import com.lazamelezi.moviesapp.data.remote.MoviesServiceImpl
import com.lazamelezi.moviesapp.data.repository.IMoviesRepository
import com.lazamelezi.moviesapp.data.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesModule {

    //Remote
    @Singleton
    @Binds
    abstract fun provideMovieServices(
        moviesServiceImpl: MoviesServiceImpl
    ): IMoviesService

    @Singleton
    @Binds
    abstract fun provideRemoteDataSource(
        remoteDataSource: MoviesRemoteDataSource
    ): IMoviesRemoteDataSource

    @Singleton
    @Binds
    abstract fun provideMoviesRepository(
        moviesRepositoryImpl: MoviesRepository
    ): IMoviesRepository


    //Local

    @Singleton
    @Binds
    abstract fun provideMoviesLocalDataSource(
        moviesLocalDataSourceImpl: FavoriteMoviesLocalDataSourceImpl
    ): IFavoriteMoviesLocalDataSource

}


@Module
@InstallIn(SingletonComponent::class)
object MoviesModuleObj {

    @Singleton
    @Provides
    fun provideMoviesService(
        retrofit: Retrofit
    ): MoviesService {
        return retrofit.create(MoviesService::class.java)
    }
}

