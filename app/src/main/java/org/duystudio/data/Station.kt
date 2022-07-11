package org.duystudio.data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Station(
    var id: Long,
    val location: Location,
    val station: String
)