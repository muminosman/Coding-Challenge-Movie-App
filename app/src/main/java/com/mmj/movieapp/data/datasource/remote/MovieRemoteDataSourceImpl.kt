package com.mmj.movieapp.data.datasource.remote

import com.mmj.movieapp.core.generic.dto.ResponseDto
import com.mmj.movieapp.core.network.MovieApi
import com.mmj.movieapp.data.model.remote.dto.response.MovieDetailsResponse
import com.mmj.movieapp.data.model.remote.dto.response.MovieResponseDto
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val api: MovieApi
) : MovieRemoteDataSource {

    override suspend fun getMovies(

        pageNumber: Int
    ): ResponseDto<List<MovieResponseDto>> {
        return api.getMovies(pageNumber = pageNumber)
    }

    override suspend fun fetchMovieDetails(movieId: Int): MovieDetailsResponse {
        return api.fetchMovieDetails(movieId)

    }

    override suspend fun getSearchMovies(
        query: String, pageNumber: Int
    ): ResponseDto<List<MovieResponseDto>> {
        return api.getSearchMovies(query = query, pageNumber = pageNumber)
    }


}