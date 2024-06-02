package kinopoisk.lab.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kinopoisk.lab.data.db.dao.FilmDao
import kinopoisk.lab.data.db.model.FilmEntity

@Database(
    entities = [FilmEntity::class],
    version = 1
)
abstract class FilmDb : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}