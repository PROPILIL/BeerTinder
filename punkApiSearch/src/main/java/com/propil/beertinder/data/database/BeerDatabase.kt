package com.propil.beertinder.data.database

import android.app.Application
import androidx.room.*

@Database(entities = [BeerDbModel::class], version = 2,
    autoMigrations =[AutoMigration (from = 1, to = 2)], exportSchema = true)
@TypeConverters(BeerConverter::class)
abstract class BeerDatabase: RoomDatabase() {

    abstract fun beerDao(): BeerDao

    companion object {

        private var INSTANCE: BeerDatabase? = null
        private const val DB_NAME = "Beer.db"
        private val LOCK = Any()

        //TODO: Move it into di/DataModule
        fun getInstance(application: Application): BeerDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let{
                    return it
                }
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