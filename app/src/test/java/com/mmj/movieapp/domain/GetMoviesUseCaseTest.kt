package com.mmj.movieapp.domain

import androidx.paging.PagingData
import com.mmj.movieapp.domain.model.Movie
import com.mmj.movieapp.domain.repository.MovieRepository
import com.mmj.movieapp.domain.usecase.GetMoviesUseCase
import com.nhaarman.mockitokotlin2.*
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
class GetMoviesUseCaseTest {

    private val repository = mock<MovieRepository>()
    private lateinit var useCase: GetMoviesUseCase
    @Before
    fun setUp() {
        useCase = GetMoviesUseCase(repository)
    }

    @Test
    fun `should emit a page from the repository`() {
        runBlocking {

            //Given
            whenever(repository.getMovies()) doReturn flow { PagingData.empty<Movie>() }
            //When
            val act = useCase.execute(Unit)

            //Then
            Assert.assertNotNull(act)
        }
    }


    @Test
    fun `should call get movies from MoviesRepository`() {
        runBlocking {
            //When
            useCase.execute(Unit)

            //Then
            verify(repository, times(1)).getMovies()
        }
    }
}