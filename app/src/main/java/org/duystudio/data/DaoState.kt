package org.duystudio.data

import androidx.room.*

@Dao
interface DaoState {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(person: State)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(person: List<State>)
}