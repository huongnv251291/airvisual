package org.duystudio.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "Country")
public class Country {
    @PrimaryKey
    var country = ""

    @TypeConverters(TypeConvertersState::class)
    var arrayList = ArrayList<State>()
}