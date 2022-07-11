package org.duystudio.data

import android.text.TextUtils
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.duystudio.data.Station

class TypeConvertersStateObject {
    @TypeConverter
    fun stringToSomeObjectList(data: String): State? {
        if (TextUtils.isEmpty(data)) {
            return null
        }
        val listType = object : TypeToken<State?>() {}.type
        val gson = Gson()
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: State?): String {
        if (someObjects == null) {
            return ""
        }
        val gson = Gson()
        return gson.toJson(someObjects)
    }
}