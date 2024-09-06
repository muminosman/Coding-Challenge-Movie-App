package com.mmj.movieapp.domain.usecase

import com.mmj.movieapp.core.generic.usecase.BaseUseCase
import com.mmj.movieapp.domain.model.MovieDetails
import com.mmj.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) : BaseUseCase<Int, Flow<MovieDetails>> {

    override suspend fun execute(input: Int): Flow<MovieDetails> =
        repository.getMovieDetails(input)


}