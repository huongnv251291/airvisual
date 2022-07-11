package org.duystudio.data

import android.text.TextUtils
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.duystudio.data.Station
import java.util.ArrayList

class TypeConvertersStation {
    @TypeConverter
    fun stringToSomeObjectList(data: String): ArrayList<Station>? {
        if (TextUtils.isEmpty(data)) {
            return ArrayList()
        }
        val listType = object : TypeToken<ArrayList<Station?>?>() {}.type
        val gson = Gson()
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: ArrayList<Station?>?): String {
        if (someObjects == null) {
            return ""
        }
        val gson = Gson()
        return gson.toJson(someObjects)
    }
}