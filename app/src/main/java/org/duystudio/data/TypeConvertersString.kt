package org.duystudio.data

import android.text.TextUtils
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConvertersString {
    @TypeConverter
    fun stringToSomeObjectList(data: String): ArrayList<String>? {
        if (TextUtils.isEmpty(data)) {
            return ArrayList()
        }
        val listType = object : TypeToken<ArrayList<String?>?>() {}.type
        val gson = Gson()
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: ArrayList<String?>?): String {
        if (someObjects == null) {
            return ""
        }
        val gson = Gson()
        return gson.toJson(someObjects)
    }
}