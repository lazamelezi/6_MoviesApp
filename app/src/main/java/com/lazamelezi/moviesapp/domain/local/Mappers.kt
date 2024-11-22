package com.lazamelezi.moviesapp.domain.local

import com.lazamelezi.moviesapp.domain.MovieDetailDomain


fun MovieDetailDomain.toFavoriteMoviesEntity(): FavoriteMoviesEntity {
    return FavoriteMoviesEntity(
        id = this.id?.toInt() ?: 0,
        poster_path = this.poster_path?: "",
        overview = this.overview?: "",
        title = this.title?: "",
        vote_average = (this.vote_average?: 0f).toFloat(),
        release_date = this.release_date?: ""
    )
}