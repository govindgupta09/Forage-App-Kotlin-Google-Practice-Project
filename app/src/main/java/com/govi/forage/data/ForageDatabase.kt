package com.govi.forage.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.govi.forage.model.Forageable

/**
 * Room database to persist data for the Forage app.
 * This database stores a [Forageable] entity
 */
// create the database with all necessary annotations, methods, variables, etc.
@Database(entities = [Forageable::class], version = 1, exportSchema = false)
abstract class ForageDatabase : RoomDatabase(){
    abstract fun forageableDao(): ForageableDao

    companion object {
        @Volatile
        private var INSTANCE: ForageDatabase? = null

        fun getDatabase(context: Context) = INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                ForageDatabase::class.java,
                "forageable_database"
            ).build().apply {
                INSTANCE = this
            }
        }
    }
}
