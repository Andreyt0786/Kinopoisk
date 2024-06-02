package kinopoisk.lab.domain.mapper

import kinopoisk.lab.data.db.model.FilmEntity
import kinopoisk.lab.data.network.model.CurrentFilmResponse
import kinopoisk.lab.data.network.model.FilmPreviewResponse
import kinopoisk.lab.domain.model.FilmModel
import kinopoisk.lab.ui.list.FilmListUiModel

fun FilmPreviewResponse.toModel(): FilmModel =
    FilmModel(
        id = filmId,
        nameRus = nameRu ?: "",
        nameEng = nameEn ?: "",
        year = year?.toInt() ?: 0,
        genres = genres.joinToString { it.genre ?: "" },
        genre = genres.firstOrNull()?.genre ?: "",
        countries = countries.joinToString { it.country ?: "" },
        rating = rating ?: "",
        posterUrlPreview = posterUrlPreview ?: "",
        posterUrl = posterUrl ?: ""
    )

fun CurrentFilmResponse.toEntity(): FilmEntity =
    FilmEntity(
        id = filmId,
        nameRus = nameRu ?: "",
        nameEng = nameEn ?: "",
        year = year?.toInt() ?: 0,
        genresString = genres.map { it.genre }.joinToString(", "),
        genre = genres.firstOrNull()?.genre ?: "",
        countriesString = countries.map { it.country }.joinToString(", "),
        rating = rating ?: "",
        posterUrlPreview = posterUrlPreview ?: "",
        posterUrl = posterUrl ?: "",
        filmLength = filmLength ?: 0,
        description = description ?: ""
    )

fun FilmModel.toUiModel(isFavorite: Boolean): FilmListUiModel =
    FilmListUiModel(
        filmId = id,
        filmPosterUrl = posterUrlPreview,
        filmTitle = nameRus,
        filmGenre = genre,
        filmYear = year,
        isFavorite = isFavorite
    )

fun FilmEntity.toModel(): FilmModel =
    FilmModel(
        id = id,
        nameRus = nameRus,
        nameEng = nameEng,
        year = year,
        genres = genresString,
        genre = genre,
        countries = countriesString,
        rating = rating,
        posterUrlPreview = posterUrlPreview,
        posterUrl = posterUrl
    )
