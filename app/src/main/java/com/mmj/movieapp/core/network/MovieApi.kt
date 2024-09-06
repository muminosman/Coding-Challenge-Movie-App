package com.mmj.movieapp.core.network

import com.mmj.movieapp.core.generic.dto.ResponseDto
import com.mmj.movieapp.data.model.remote.dto.response.MovieDetailsResponse
import com.mmj.movieapp.data.model.remote.dto.response.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") pageNumber: Int
    ): ResponseDto<List<MovieResponseDto>>

    @GET("movie/{movie_id}")
    suspend fun fetchMovieDetails(@Path("movie_id") id: Int): MovieDetailsResponse

    @GET("search/movie")
    suspend fun getSearchMovies(
        @Query("query") query: String, @Query("page") pageNumber: Int
    ): ResponseDto<List<MovieResponseDto>>
}