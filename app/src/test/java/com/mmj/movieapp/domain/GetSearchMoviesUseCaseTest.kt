package com.mmj.movieapp.domain

import androidx.paging.PagingData
import com.mmj.movieapp.domain.model.Movie
import com.mmj.movieapp.domain.repository.MovieRepository
import com.mmj.movieapp.domain.usecase.GetSearchMoviesUseCase
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetSearchMoviesUseCaseTest {

    private val repository = mock<MovieRepository>()
    private lateinit var useCase: GetSearchMoviesUseCase

    @Before
    fun setUp() {
        useCase = GetSearchMoviesUseCase(repository)
    }

    @Test
    fun `should emit a page from the repository`() {
        runBlocking {

            //Given
            whenever(repository.getSearchMovies("test")) doReturn flow { PagingData.empty<Movie>() }
            //When
            val act = useCase.execute("test")

            //Then
            Assert.assertNotNull(act)
        }
    }


    @Test
    fun `should call get movies from MoviesRepository`() {
        runBlocking {
            //When
            useCase.execute("test")

            //Then
            verify(repository, times(1)).getSearchMovies("test")
        }
    }
}