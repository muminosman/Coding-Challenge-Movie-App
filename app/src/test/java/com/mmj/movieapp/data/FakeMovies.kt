package com.mmj.movieapp.data

import com.mmj.movieapp.core.generic.dto.ResponseDto
import com.mmj.movieapp.data.model.remote.dto.response.GenreResponse
import com.mmj.movieapp.data.model.remote.dto.response.MovieDetailsResponse
import com.mmj.movieapp.data.model.remote.dto.response.MovieResponseDto
import com.mmj.movieapp.data.model.remote.dto.response.SpokenLanguageResponse

internal object FakeMovies {
    fun getFakeMovieList(): ResponseDto<List<MovieResponseDto>> {
        return ResponseDto(
            page = 1, results = listOf(
                MovieResponseDto(
                    adult = false,
                    backdropPath = "/xDMIl84Qo5Tsu62c9DGWhmPI67A.jpg",
                    genreIds = listOf(28, 12, 878),
                    id = 505642,
                    originalLanguage = "en",
                    originalTitle = "Black Panther : Wakanda Forever",
                    overview = "Queen Ramonda, Shuri, M’Baku, Okoye and the Dora Milaje fight to protect their nation from intervening world powers in the wake of King T’Challa’s death.  As the Wakandans strive to embrace their next chapter, the heroes must band together with the help of War Dog Nakia and Everett Ross and forge a new path for the kingdom of Wakanda.",
                    popularity = 9051.476,
                    posterPath = "/sv1xJUazXeYqALzczSZ3O6nkH75.jpg",
                    releaseDate = "2022 - 11 - 09",
                    title = "Black Panther : Wakanda Forever",
                    video = false,
                    voteAverage = 7.5,
                    voteCount = 2671
                )
            ), totalResults = 1, totalPages = 1
        )

    }

    fun getFakeMovieDetails() = MovieDetailsResponse(
        adult = false,
        backdropPath = "/xDMIl84Qo5Tsu62c9DGWhmPI67A.jpg",
        budget = 250000000,
        genreResponses = listOf(GenreResponse(id = 28, name = "Action")),
        homepage = "https://wakandaforevertickets.com",
        id = 505642,
        imdbId = "tt9114286",
        originalLanguage = "en",
        originalTitle = "Black Panther: Wakanda Forever",
        overview = "Queen Ramonda, Shuri, M’Baku, Okoye and the Dora Milaje fight to protect their nation from intervening world powers in the wake of King T’Challa’s death.  As the Wakandans strive to embrace their next chapter, the heroes must band together with the help of War Dog Nakia and Everett Ross and forge a new path for the kingdom of Wakanda.",
        popularity = 8234.58,
        posterPath = "/sv1xJUazXeYqALzczSZ3O6nkH75.jpg",
        releaseDate = "2022-11-09",
        revenue = 835000000,
        spokenLanguageResponses = listOf(
            SpokenLanguageResponse(
                name = "English",
                englishName = "English"
            )
        ),
        title = "Black Panther: Wakanda Forever",
        voteAverage = 7.488f,
        voteCount = 2745
    )
}