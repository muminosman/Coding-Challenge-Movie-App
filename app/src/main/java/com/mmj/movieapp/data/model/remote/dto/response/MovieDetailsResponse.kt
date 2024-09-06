package com.mmj.movieapp.data.model.remote.dto.response

import com.google.gson.annotations.SerializedName
import com.mmj.movieapp.domain.model.Genre
import com.mmj.movieapp.domain.model.MovieDetails
import com.mmj.movieapp.domain.model.SpokenLanguage


data class MovieDetailsResponse(
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("budget") val budget: Int?,
    @SerializedName("genres") val genreResponses: List<GenreResponse>?,
    @SerializedName("homepage") val homepage: String?,
    @SerializedName("id") val id: Int,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("revenue") val revenue: Int?,
    @SerializedName("spoken_languages") val spokenLanguageResponses: List<SpokenLanguageResponse>?,
    @SerializedName("title") val title: String?,
    @SerializedName("vote_average") val voteAverage: Float?,
    @SerializedName("vote_count") val voteCount: Int?
)

internal fun MovieDetailsResponse.toDomain() = MovieDetails(
    adult, backdropPath, budget, genreResponses?.map { it.toDomain() },
    homepage, id, imdbId, originalLanguage, originalTitle, overview, popularity,
    posterPath, releaseDate, revenue, spokenLanguageResponses?.map { it.toDomain() },
    title, voteAverage, voteCount
)

data class SpokenLanguageResponse(
    @SerializedName("english_name") val englishName: String,
    @SerializedName("name") val name: String?
)

internal fun SpokenLanguageResponse.toDomain() = SpokenLanguage(
    englishName, name
)

data class GenreResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

internal fun GenreResponse.toDomain() = Genre(
    id, name
)