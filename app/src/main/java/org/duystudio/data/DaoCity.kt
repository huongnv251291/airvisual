package org.duystudio.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.TypeConverters

@Dao
interface DaoCity {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(person: City)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(person: List<City>)
}