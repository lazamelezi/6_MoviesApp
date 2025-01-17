package com.lazamelezi.moviesapp.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lazamelezi.moviesapp.domain.local.FavoriteMoviesEntity
import com.lazamelezi.moviesapp.usecases.data.FakeRepositoryErrorApi
import com.lazamelezi.moviesapp.usecases.data.FakeRepositorySuccessApi
import com.lazamelezi.moviesapp.usecases.utils.MainCoroutineScopeRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetFavoriteMoviesUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule : InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    // SUT -> System Under Test
    private lateinit var sut: GetFavoriteMoviesUseCase
    private lateinit var sutSuccess: GetFavoriteMoviesUseCase

    // DOC -> Depedency of Component
    private lateinit var fakeMovieRepository: FakeRepositoryErrorApi
    private lateinit var fakeMovieRepositorySuccess: FakeRepositorySuccessApi

    //Save collect result
    private val listResult = mutableListOf<List<FavoriteMoviesEntity>>()

    @Before
    fun setUp() {
        fakeMovieRepository = FakeRepositoryErrorApi()
        sut = GetFavoriteMoviesUseCase(fakeMovieRepository)

        fakeMovieRepositorySuccess = FakeRepositorySuccessApi()
        sutSuccess = GetFavoriteMoviesUseCase(fakeMovieRepositorySuccess)
    }


    @Test
    fun `should return empty if not exist favorite movies`() {
        //Arrange

        //Act
        val result = sut.invoke()

        runBlocking {
            result.collect {
                listResult.add(it)
            }
        }

        //Assert
        assertEquals(listResult[0].size, 0)
    }

    @Test
    fun `should return list of favorite movies`() {
        //Arrange

        //Act
        val result = sutSuccess.invoke()

        runBlocking {
            result.collect {
                listResult.add(it)
            }
        }

        //Assert
        assertEquals(listResult[0].size, 1)
    }




    @After
    fun tearDown() {
        listResult.clear()
    }

}