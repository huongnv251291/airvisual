package org.duystudio.data

import androidx.room.TypeConverters

data class Location(
    @TypeConverters(TypeConvertersDouble::class)
    val coordinates: List<Double>,
    val type: String
)