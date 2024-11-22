package com.lazamelezi.moviesapp.usecases

import com.lazamelezi.moviesapp.data.repository.IMoviesRepository
import com.lazamelezi.moviesapp.domain.local.FavoriteMoviesEntity
import javax.inject.Inject

class InsertFavoriteMovieUseCase @Inject constructor(
    private val repository: IMoviesRepository
) {
    suspend operator fun invoke(favoriteMoviesEntity: FavoriteMoviesEntity) = repository.insertFavoriteMovie(favoriteMoviesEntity)
}