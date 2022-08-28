package com.propil.beertinder.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [BeerDbModel::class], version = 1, exportSchema = false)
@TypeConverters(BeerConverter::class)
abstract class BeerDatabase: RoomDatabase() {
    companion object {

        private var INSTANCE: BeerDatabase? = null
        private const val DB_NAME = "Beer.db"
        private val LOCK = Any()

        fun getInstance(application: Application): BeerDatabase {
            INSTANCE?.let {
                return it
            }
            val db = Room.databaseBuilder(
                application,
                BeerDatabase::class.java,
                DB_NAME
            ).build()
            INSTANCE = db
            return db
        }
    }
}