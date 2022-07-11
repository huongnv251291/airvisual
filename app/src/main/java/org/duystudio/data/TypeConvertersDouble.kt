package org.duystudio.data

import android.text.TextUtils
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConvertersDouble {
    @TypeConverter
    fun stringToSomeObjectList(data: String): ArrayList<Double>? {
        if (TextUtils.isEmpty(data)) {
            return null
        }
        val listType = object : TypeToken<ArrayList<Double?>?>() {}.type
        val gson = Gson()
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: ArrayList<Double?>?): String {
        if (someObjects == null) {
            return ""
        }
        val gson = Gson()
        return gson.toJson(someObjects)
    }
}