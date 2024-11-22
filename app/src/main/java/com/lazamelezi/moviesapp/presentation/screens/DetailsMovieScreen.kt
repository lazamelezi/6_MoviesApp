package com.lazamelezi.moviesapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.lazamelezi.moviesapp.domain.MovieDetailDomain
import com.lazamelezi.moviesapp.presentation.composables.CustomNoInternetConnectionScreen
import com.lazamelezi.moviesapp.presentation.composables.CustomErrorScreenSomethingHappens
import com.lazamelezi.moviesapp.presentation.composables.LoadingScreen
import com.lazamelezi.moviesapp.usecases.GetDetailsMovieResult

@Composable
fun DetailsMovieScreen(
    navController: NavController,
    stateMovieDetail: GetDetailsMovieResult,
    onClickFavorite: (MovieDetailDomain) -> Unit,
    isFavoriteMovie:Boolean,
) {
    var isLoading by remember { mutableStateOf(false)}
    var isError by remember { mutableStateOf(false)}
    var isSuccess by remember { mutableStateOf(false)}
    var isInternetError by remember { mutableStateOf(false)}

    var item by remember { mutableStateOf(MovieDetailDomain())}


    LaunchedEffect(key1 = stateMovieDetail){
        when(stateMovieDetail) {
            is GetDetailsMovieResult.Success -> {
                isLoading = false
                isError = false
                isInternetError = false
                isSuccess = true
                item = stateMovieDetail.data
            }
            is GetDetailsMovieResult.Error -> {
                isLoading = false
                isSuccess = false
                isError = true
                isInternetError = false
            }
            is GetDetailsMovieResult.Loading -> {
                isError = false
                isSuccess = false
                isInternetError = true
                isLoading = stateMovieDetail.isLoading
            }

            is GetDetailsMovieResult.InternetError -> {
                isLoading = false
                isError = false
                isInternetError = true
                isSuccess = false
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        when{
            isLoading -> {
                LoadingScreen()
            }
            isError -> {
                CustomErrorScreenSomethingHappens()
            }
            isInternetError -> {
                CustomNoInternetConnectionScreen()
            }
            isSuccess -> {
                DetailsMovieContent(
                    onClickBack = {
                        navController.popBackStack()
                    },
                    onClickFavorite = { onClickFavorite(item) },
                    title = item.title?: "",
                    description = item.overview?: "",
                    imageBackdrop = item.backdrop_path?: "",
                    imagePoster = item.poster_path?: "",
                    genres = item.genres?: listOf(),
                    releaseDate = item.release_date?: "",
                    voteAverage = item.vote_average?.toString()?: "",
                    runtime = item.runtimeWithMinutes?: "",
                    isFavoriteMovie = isFavoriteMovie
                )
            }
        }
    }
}

