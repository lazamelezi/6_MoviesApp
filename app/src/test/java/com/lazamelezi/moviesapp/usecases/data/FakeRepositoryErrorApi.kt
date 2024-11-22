package com.lazamelezi.moviesapp.usecases.data

import com.lazamelezi.moviesapp.data.repository.IMoviesRepository
import com.lazamelezi.moviesapp.domain.MoviesDetailResponse
import com.lazamelezi.moviesapp.domain.PopularsMovieResponse
import com.lazamelezi.moviesapp.domain.local.FavoriteMoviesEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException

class FakeRepositoryErrorApi : IMoviesRepository {
    override suspend fun getPopularMovies(
        api_key: String,
        language: String,
        page: Int
    ): Flow<PopularsMovieResponse> {
        return flow {
            throw HttpException(
                retrofit2.Response.error<PopularsMovieResponse>(
                    500,
                    "Error".toResponseBody("application/json".toMediaTypeOrNull())
                )
            )
        }
    }

    override suspend fun getMovieDetail(
        api_key: String,
        language: String,
        id: String
    ): Flow<MoviesDetailResponse> {
        return flow {
            throw HttpException(
                retrofit2.Response.error<MoviesDetailResponse>(
                    500,
                    "Error".toResponseBody("application/json".toMediaTypeOrNull())
                )
            )
        }
    }

    override suspend fun searchMovie(
        query: String,
        api_key: String,
        language: String
    ): Flow<PopularsMovieResponse> {
        return flow {
            throw HttpException(
                retrofit2.Response.error<MoviesDetailResponse>(
                    500,
                    "Error".toResponseBody("application/json".toMediaTypeOrNull())
                )
            )
        }
    }

    //Local
    override fun getFavoriteMovies(): Flow<List<FavoriteMoviesEntity>> {
        return flow {
            emit(emptyList())
        }
    }

    override fun getFavoriteMovieById(id: Int): Flow<FavoriteMoviesEntity> {
        return flow {
            throw HttpException(
                retrofit2.Response.error<MoviesDetailResponse>(
                    500,
                    "Error".toResponseBody("application/json".toMediaTypeOrNull())
                )
            )
        }
    }

    override suspend fun insertFavoriteMovie(favoriteMoviesEntity: FavoriteMoviesEntity) {
        //
    }

    override suspend fun deleteFavoriteMovie(id: Int) {
        //throw Exception("Error")
    }

}