package org.duystudio.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "State")
public class State {
    @PrimaryKey
    var state = ""
    @TypeConverters(TypeConvertersCountryObject::class)
     var country :Country? = null

    @TypeConverters(TypeConvertersCity::class)
    var arrayList = ArrayList<City>()

}