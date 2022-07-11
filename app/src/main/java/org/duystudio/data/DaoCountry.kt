package org.duystudio.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface DaoCountry {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(person: Country)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(person: List<Country>)
}