package com.lazamelezi.moviesapp.data.remote

import com.lazamelezi.moviesapp.domain.MoviesDetailResponse
import com.lazamelezi.moviesapp.domain.PopularsMovieResponse
import com.lazamelezi.moviesapp.framework.network.BaseDataSource
import javax.inject.Inject

interface IMoviesService {
    suspend fun getPopularMovies(
        api_key: String,
        language: String,
        page: Int
    ): PopularsMovieResponse

    suspend fun getMovieDetail(
        api_key: String,
        language: String,
        id: String
    ): MoviesDetailResponse

    suspend fun searchMovie(
        query: String,
        api_key: String,
        language: String,
    ): PopularsMovieResponse
}


class MoviesServiceImpl @Inject constructor(
    private val moviesService: MoviesService
):BaseDataSource() , IMoviesService {
    override suspend fun getPopularMovies(
        api_key: String,
        language: String,
        page: Int
    )  = getResult (
        call = { moviesService.getPopularMovies(api_key = api_key, language = language, page = page) },
        forceError = false
    )


    override suspend fun getMovieDetail(
        api_key: String,
        language: String,
        id: String
    ) = getResult (
        call = { moviesService.getMovieDetail(api_key = api_key, language = language, id = id) },
        forceError = false
    )

    override suspend fun searchMovie(
        query: String,
        api_key: String,
        language: String
    ) = getResult (
        call = { moviesService.searchMovie(query = query, api_key = api_key, language = language) },
        forceError = false
    )
}