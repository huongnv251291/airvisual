package org.duystudio.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [City::class, Country::class, State::class], version = 1, exportSchema = false)
public abstract class AppDatabase : RoomDatabase() {
    abstract val daoCity: DaoCity
    abstract val daoState: DaoState
    abstract val daoCountry: DaoCountry

    companion object {
        private val LOG_TAG = AppDatabase::class.java.simpleName
        private const val DATABASE_NAME = "airquality"
        private var sInstance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (sInstance == null) {
                synchronized(AppDatabase::class.java) {
                    Log.d(LOG_TAG, "Creating new database instance")
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .enableMultiInstanceInvalidation()
                        .build()
                }
            }
            Log.d(LOG_TAG, "Getting the database instance")
            return sInstance
        }
    }
}