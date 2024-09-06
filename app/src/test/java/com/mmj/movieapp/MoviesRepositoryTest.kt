package com.mmj.movieapp

import androidx.paging.PagingSource
import com.mmj.movieapp.core.network.MovieApi
import com.mmj.movieapp.data.FakeMovies
import com.mmj.movieapp.data.datasource.remote.MovieRemoteDataSourceImpl
import com.mmj.movieapp.data.model.remote.dto.response.MovieResponseDto
import com.mmj.movieapp.data.model.remote.mapper.mapFromListModel
import com.mmj.movieapp.data.repository.MovieRepositoryImpl
import com.mmj.movieapp.data.repository.paging.MoviePagingSource
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
internal class MoviesRepositoryTest {

    private val api = mock<MovieApi>()
    private lateinit var repositoryImpl: MovieRepositoryImpl

    @Before
    fun setUp() {
        repositoryImpl = MovieRepositoryImpl(MovieRemoteDataSourceImpl(api))
    }

    @Test
    fun `get movies list should return a paging data`() {
        runBlocking {
            val pagingSource = MoviePagingSource(MovieRemoteDataSourceImpl(api))

            whenever(api.getMovies(1)) doReturn FakeMovies.getFakeMovieList()

            val actual = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null, loadSize = 1, placeholdersEnabled = false
                )
            ) as PagingSource.LoadResult.Page

            val expected = PagingSource.LoadResult.Page(
                data = FakeMovies.getFakeMovieList().results as List<MovieResponseDto>,
                prevKey = null,
                nextKey = 1
            )
            Assert.assertEquals(
                actual.data, expected.data.mapFromListModel()
            )
        }
    }

    @Test
    fun `call getMovieDetails should return correct movie details`() {
        runBlocking {
            whenever(api.fetchMovieDetails(1)) doReturn FakeMovies.getFakeMovieDetails()

            val actual = repositoryImpl.getMovieDetails(1).first()

            Assert.assertEquals(
                actual.budget, 250000000
            )
        }
    }

    @Test
    fun `call fetchMovies should call it once`() {
        runBlocking {
            api.getMovies(1)

            verify(api, times(1)).getMovies(1)
        }
    }

    @Test
    fun `call fetchMovieDetails should call it once`() {
        runBlocking {
            api.fetchMovieDetails(1)

            verify(api, times(1)).fetchMovieDetails(1)
        }
    }
}