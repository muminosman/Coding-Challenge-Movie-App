package com.mmj.movieapp.domain.usecase

import androidx.paging.PagingData
import com.mmj.movieapp.core.generic.usecase.BaseUseCase
import com.mmj.movieapp.domain.model.Movie
import com.mmj.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) : BaseUseCase<String, Flow<PagingData<Movie>>> {
    override suspend fun execute(input: String): Flow<PagingData<Movie>> {
        return repository.getSearchMovies(input)
    }
}