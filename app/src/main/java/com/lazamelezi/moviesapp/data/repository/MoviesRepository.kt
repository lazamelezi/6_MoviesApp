package com.lazamelezi.moviesapp.data.repository

import com.lazamelezi.moviesapp.data.local.IFavoriteMoviesLocalDataSource
import com.lazamelezi.moviesapp.data.remote.IMoviesRemoteDataSource
import com.lazamelezi.moviesapp.domain.MoviesDetailResponse
import com.lazamelezi.moviesapp.domain.PopularsMovieResponse
import com.lazamelezi.moviesapp.domain.local.FavoriteMoviesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IMoviesRepository {
    suspend fun getPopularMovies(
        api_key: String,
        language: String,
        page: Int
    ): Flow<PopularsMovieResponse>

    suspend fun getMovieDetail(
        api_key: String,
        language: String,
        id: String
    ): Flow<MoviesDetailResponse>

    suspend fun searchMovie(
        query: String,
        api_key: String,
        language: String,
    ): Flow<PopularsMovieResponse>


    //Local
    fun getFavoriteMovies(): Flow<List<FavoriteMoviesEntity>>
    fun getFavoriteMovieById(id: Int): Flow<FavoriteMoviesEntity>
    suspend fun insertFavoriteMovie(favoriteMoviesEntity: FavoriteMoviesEntity)
    suspend fun deleteFavoriteMovie(id: Int)

}

class MoviesRepository @Inject constructor(
    private val remote: IMoviesRemoteDataSource,
    private val local: IFavoriteMoviesLocalDataSource
) : IMoviesRepository {

       override suspend fun getPopularMovies(
            api_key: String,
            language: String,
            page: Int
        ) = remote.getPopularMovies(api_key, language, page)

        override suspend fun getMovieDetail(
            api_key: String,
            language: String,
            id: String
        ) = remote.getMovieDetail(api_key, language, id)

    override suspend fun searchMovie(
        query: String,
        api_key: String,
        language: String
    ): Flow<PopularsMovieResponse> {
        return remote.searchMovie(query, api_key, language)
    }

    override fun getFavoriteMovies(): Flow<List<FavoriteMoviesEntity>> {
        return local.getFavoriteMovies()
    }

    override fun getFavoriteMovieById(id: Int): Flow<FavoriteMoviesEntity> {
        return local.getFavoriteMovieById(id)
    }

    override suspend fun insertFavoriteMovie(favoriteMoviesEntity: FavoriteMoviesEntity) {
        local.insertFavoriteMovie(favoriteMoviesEntity)
    }

    override suspend fun deleteFavoriteMovie(id: Int) {
        local.deleteFavoriteMovie(id)
    }
}