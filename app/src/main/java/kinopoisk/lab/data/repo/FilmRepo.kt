package kinopoisk.lab.data.repo

import kinopoisk.lab.data.db.FilmDb
import kinopoisk.lab.data.db.model.FilmEntity
import kinopoisk.lab.data.network.KinopoiskApi
import kinopoisk.lab.data.network.model.CurrentFilmResponse
import kinopoisk.lab.domain.mapper.toModel
import kinopoisk.lab.domain.model.FilmModel
import javax.inject.Inject

class FilmRepo @Inject constructor(
    private val filmDb: FilmDb,
    private val kinopoiskApi: KinopoiskApi
) {

    suspend fun getAllPopularFilms(): List<FilmModel> =
        kinopoiskApi.getTopFilms().films.map { it.toModel() }

    suspend fun getPopularFilmById(filmId: Long): CurrentFilmResponse =
        kinopoiskApi.getFilmById(filmId = filmId)

    suspend fun saveFilmInDb(filmEntity: FilmEntity) = filmDb.filmDao().insertFilm(filmEntity)

    suspend fun deleteFilmFromDb(filmId: Long) = filmDb.filmDao().deleteFilmById(filmId)

    suspend fun getFavoriteFilms(): List<FilmModel> =
        filmDb.filmDao().getAllFilms().map { it.toModel() }

    suspend fun getFavoriteFilmById(filmId: Long): FilmEntity =
        filmDb.filmDao().getFilmById(filmId = filmId)
}