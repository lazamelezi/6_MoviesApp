package com.lazamelezi.moviesapp.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazamelezi.moviesapp.BuildConfig
import com.lazamelezi.moviesapp.domain.MovieDetailDomain
import com.lazamelezi.moviesapp.domain.local.toFavoriteMoviesEntity
import com.lazamelezi.moviesapp.usecases.DeleteFavoriteMovieUseCase
import com.lazamelezi.moviesapp.usecases.GetDetailsMovieResult
import com.lazamelezi.moviesapp.usecases.GetDetailsMovieUseCase
import com.lazamelezi.moviesapp.usecases.GetFavoriteMovieByIdUseCase
import com.lazamelezi.moviesapp.usecases.InsertFavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class DetailsMovieViewModel @Inject constructor(
    private val getDetailsMovieUseCase: GetDetailsMovieUseCase,
    savedStateHandle: SavedStateHandle,
    private val insertFavoriteMovieUseCase: InsertFavoriteMovieUseCase,
    private val getFavoriteMovieUseCase: GetFavoriteMovieByIdUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase
):ViewModel(){

    private val _detailsMovie = MutableStateFlow<GetDetailsMovieResult>(GetDetailsMovieResult.Loading(false))
    val detailsMovie = _detailsMovie.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000,1),
        initialValue = GetDetailsMovieResult.Loading(false)
    )

    private val _isFavoriteMovie = MutableStateFlow<Boolean>(false)
    val isFavoriteMovie = _isFavoriteMovie.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000,1),
        initialValue = false
    )

    init {
        val idMovie = savedStateHandle.get<String>("movieId")?: ""
        start(idMovie)
    }

    private fun start(idMovie:String) = viewModelScope.launch{
        getDetailsMovie(idMovie).join()
        getFavoriteMovieById(idMovie)
    }

    private fun getDetailsMovie(id:String) = viewModelScope.launch(Dispatchers.IO) {
        getDetailsMovieUseCase.invoke(
            api_key = BuildConfig.API_KEY,
            language = "en-EN",
            id = id
        ).onStart {
            _detailsMovie.value = GetDetailsMovieResult.Loading(true)
            delay(1.seconds) // Just to see the loading screen
        }.onEach {
            _detailsMovie.value = GetDetailsMovieResult.Success(it)
        }.catch {
            Timber.e("DetailsMovieViewModel: ${it.message}")
            _detailsMovie.value = GetDetailsMovieResult.Error("Error, ${it.message}")
        }.launchIn(viewModelScope)
    }

    fun saveOrRemoveFavoriteMovie(movie:MovieDetailDomain) = viewModelScope.launch{
        if(isFavoriteMovie.value){
            unMarkFavoriteMovie(movie)
            return@launch
        }
        //mark favorite movie if is not favorite
        markFavoriteMovie(movie)
    }

    private fun markFavoriteMovie(movie:MovieDetailDomain) = viewModelScope.launch(Dispatchers.IO) {
        insertFavoriteMovieUseCase.invoke(movie.toFavoriteMoviesEntity())
    }

    private fun unMarkFavoriteMovie(movie:MovieDetailDomain) = viewModelScope.launch(Dispatchers.IO) {
        val favoriteMovie = (movie.id?:0)
        deleteFavoriteMovieUseCase.invoke(favoriteMovie)

    }

    private fun getFavoriteMovieById(idMovie:String) = viewModelScope.launch(Dispatchers.IO) {
        val favoriteMovie = (idMovie.toIntOrNull()?:0)
        getFavoriteMovieUseCase
            .invoke(favoriteMovie)
            .onStart {
                _isFavoriteMovie.value = false
            }.onEach {
               when(it){
                   null -> _isFavoriteMovie.value = false
                   else -> _isFavoriteMovie.value = true
               }
            }.catch {
                Timber.e("getFavoriteMovieById error: ${it.message}")
                _isFavoriteMovie.value = false
            }.launchIn(viewModelScope)
    }

    //save or remove favorite movie

}