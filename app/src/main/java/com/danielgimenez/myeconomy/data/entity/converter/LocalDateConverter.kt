package com.danielgimenez.myeconomy.data.entity.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class LocalDateConverter {

    @TypeConverter
    fun intToLocalDateTime(data: String?): LocalDate?{
        if (data == null) return null
        return getLocalDateFromStringISO8601(data)
    }

    @TypeConverter
    fun TypeToLong(localdate: LocalDate?): String?{
        if(localdate == null) return null
        return localdate.toString()
    }

    fun getLocalDateFromStringISO8601(dateString: String): LocalDate {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        formatter = formatter.withLocale(Locale.getDefault())
        return LocalDate.parse(dateString, formatter)
    }

}