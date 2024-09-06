package com.mmj.movieapp

import com.mmj.movieapp.core.network.MovieApi
import com.mmj.movieapp.data.enqueueResponse
import com.mmj.movieapp.data.initMockOkHttpClient
import com.mmj.movieapp.data.initMockRetrofit
import com.mmj.movieapp.data.initMockWebserver
import com.mmj.movieapp.data.initMoviesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

@ExperimentalCoroutinesApi
class RemoteDataSourceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var retrofit: Retrofit
    private lateinit var api: MovieApi

    @Before
    fun setUp() {
        mockWebServer = initMockWebserver()

        mockWebServer.start()

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS


        okHttpClient =
            initMockOkHttpClient().newBuilder().addInterceptor(httpLoggingInterceptor).build()

        retrofit = initMockRetrofit(mockWebServer, okHttpClient)


        api = initMoviesApi(retrofit)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch Movies correctly given 200 response`() {
        mockWebServer.enqueueResponse(moviesResponse200, 200)

        runBlocking {
            val actual = api.getMovies(1)
            assertNotNull(actual)
        }
    }

    @Test
    fun `should fetch Movie details correctly given 200 response`() {
        mockWebServer.enqueueResponse(movieResponse200, 200)

        runBlocking {
            val actual = api.fetchMovieDetails(505642)
            assertNotNull(actual)
        }
    }


    @Test
    fun `should fetch Movies correctly given correct data`() {
        mockWebServer.enqueueResponse(moviesResponse200, 200)

        runBlocking {
            val actual = api.getMovies(1)
            assertEquals(actual.results?.get(0)?.originalTitle, "Black Panther: Wakanda Forever")
        }
    }

    @Test
    fun `should fetch Movie details correctly given correct data`() {
        mockWebServer.enqueueResponse(movieResponse200, 200)

        runBlocking {
            val actual = api.fetchMovieDetails(505642)
            assertEquals(actual.originalTitle, "Black Panther: Wakanda Forever")
        }
    }

    val moviesResponse200 =
        "{\n" + "  \"page\": 1,\n" + "  \"results\": [\n" + "    {\n" + "      \"adult\": false,\n" + "      \"backdrop_path\": \"/xDMIl84Qo5Tsu62c9DGWhmPI67A.jpg\",\n" + "      \"genre_ids\": [\n" + "        28,\n" + "        12,\n" + "        878\n" + "      ],\n" + "      \"id\": 505642,\n" + "      \"original_language\": \"en\",\n" + "      \"original_title\": \"Black Panther: Wakanda Forever\",\n" + "      \"overview\": \"Queen Ramonda, Shuri, M’Baku, Okoye and the Dora Milaje fight to protect their nation from intervening world powers in the wake of King T’Challa’s death.  As the Wakandans strive to embrace their next chapter, the heroes must band together with the help of War Dog Nakia and Everett Ross and forge a new path for the kingdom of Wakanda.\",\n" + "      \"popularity\": 9051.476,\n" + "      \"poster_path\": \"/sv1xJUazXeYqALzczSZ3O6nkH75.jpg\",\n" + "      \"release_date\": \"2022-11-09\",\n" + "      \"title\": \"Black Panther: Wakanda Forever\",\n" + "      \"video\": false,\n" + "      \"vote_average\": 7.5,\n" + "      \"vote_count\": 2671\n" + "    }\n" + "  ]\n" + "}"


    val movieResponse200 =
        "{\n" + "  \"adult\": false,\n" + "  \"backdrop_path\": \"/xDMIl84Qo5Tsu62c9DGWhmPI67A.jpg\",\n" + "  \"budget\": 250000000,\n" + "  \"genres\": [\n" + "    {\n" + "      \"id\": 28,\n" + "      \"name\": \"Action\"\n" + "    },\n" + "    {\n" + "      \"id\": 12,\n" + "      \"name\": \"Adventure\"\n" + "    },\n" + "    {\n" + "      \"id\": 878,\n" + "      \"name\": \"Science Fiction\"\n" + "    }\n" + "  ],\n" + "  \"homepage\": \"https://wakandaforevertickets.com\",\n" + "  \"id\": 505642,\n" + "  \"imdb_id\": \"tt9114286\",\n" + "  \"original_language\": \"en\",\n" + "  \"original_title\": \"Black Panther: Wakanda Forever\",\n" + "  \"overview\": \"Queen Ramonda, Shuri, M’Baku, Okoye and the Dora Milaje fight to protect their nation from intervening world powers in the wake of King T’Challa’s death.  As the Wakandans strive to embrace their next chapter, the heroes must band together with the help of War Dog Nakia and Everett Ross and forge a new path for the kingdom of Wakanda.\",\n" + "  \"popularity\": 8234.58,\n" + "  \"poster_path\": \"/sv1xJUazXeYqALzczSZ3O6nkH75.jpg\",\n" + "  \"release_date\": \"2022-11-09\",\n" + "  \"revenue\": 835000000,\n" + "  \"spoken_languages\": [\n" + "    {\n" + "      \"english_name\": \"English\",\n" + "      \"iso_639_1\": \"en\",\n" + "      \"name\": \"English\"\n" + "    },\n" + "    {\n" + "      \"english_name\": \"French\",\n" + "      \"iso_639_1\": \"fr\",\n" + "      \"name\": \"Français\"\n" + "    },\n" + "    {\n" + "      \"english_name\": \"Haitian; Haitian Creole\",\n" + "      \"iso_639_1\": \"ht\",\n" + "      \"name\": \"\"\n" + "    },\n" + "    {\n" + "      \"english_name\": \"Spanish\",\n" + "      \"iso_639_1\": \"es\",\n" + "      \"name\": \"Español\"\n" + "    },\n" + "    {\n" + "      \"english_name\": \"Xhosa\",\n" + "      \"iso_639_1\": \"xh\",\n" + "      \"name\": \"\"\n" + "    }\n" + "  ],\n" + "  \"title\": \"Black Panther: Wakanda Forever\",\n" + "  \"vote_average\": 7.488,\n" + "  \"vote_count\": 2745\n" + "}"
}