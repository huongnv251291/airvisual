package org.duystudio.data

import android.text.TextUtils
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConvertersCountryObject {
    @TypeConverter
    fun stringToSomeObjectList(data: String): Country? {
        if (TextUtils.isEmpty(data)) {
            return null
        }
        val listType = object : TypeToken<Country?>() {}.type
        val gson = Gson()
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: Country?): String {
        if (someObjects == null) {
            return ""
        }
        val gson = Gson()
        return gson.toJson(someObjects)
    }
}