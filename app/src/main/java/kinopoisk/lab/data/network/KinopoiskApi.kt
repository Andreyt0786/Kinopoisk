package kinopoisk.lab.data.network

import kinopoisk.lab.data.network.model.CurrentFilmResponse
import kinopoisk.lab.data.network.model.FilmsResponse
import kinopoisk.lab.util.API_PARAMETER_FILMS
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {

    @GET("top")
    suspend fun getTopFilms(
        @Query("type") type: String = API_PARAMETER_FILMS,
        @Query("page") page: Int = 1
    ): FilmsResponse

    @GET("{id}")
    suspend fun getFilmById(@Path("id") filmId: Long): CurrentFilmResponse
}