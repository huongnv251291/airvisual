package org.duystudio.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "City")
public class City {
    @PrimaryKey
    var city = ""
    @TypeConverters(TypeConvertersStateObject::class)
    var state: State? = null

    @TypeConverters(TypeConvertersStation::class)
    var list = ArrayList<Station>()

}