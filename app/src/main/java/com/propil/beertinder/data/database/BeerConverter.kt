package com.propil.beertinder.data.database

import androidx.room.TypeConverter

class BeerConverter {

    @TypeConverter
    fun mapListToString(list:List<String>): String {
        return list.joinToString()
    }

    @TypeConverter
    fun mapStringToList(string: String): List<String> {
        return string.split(",")
    }
}