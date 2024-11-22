package com.lazamelezi.moviesapp.usecases

import com.lazamelezi.moviesapp.data.repository.IMoviesRepository
import javax.inject.Inject

class DeleteFavoriteMovieUseCase @Inject constructor(
    private val repository: IMoviesRepository
) {
    suspend operator fun invoke(id: Int) = repository.deleteFavoriteMovie(id)
}