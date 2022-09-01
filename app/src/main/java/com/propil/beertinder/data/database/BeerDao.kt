package com.propil.beertinder.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BeerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteBeer(beerDbModel: BeerDbModel)

    @Query("SELECT * FROM beers")
    fun getBeerList(): LiveData<List<BeerDbModel>>

    @Query("SELECT * FROM beers WHERE id = :beerId LIMIT 1")
    suspend fun getBeer(beerId: Int): BeerDbModel

    @Query("DELETE FROM beers WHERE id = :beerId")
    suspend fun deleteFavoriteBeer(beerId: Int)

}