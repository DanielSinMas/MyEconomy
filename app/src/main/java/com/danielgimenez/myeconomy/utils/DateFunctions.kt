package com.danielgimenez.myeconomy.utils

import android.content.Context
import android.text.format.DateFormat
import java.text.Format
import java.util.*

class DateFunctions{
    companion object{
        fun formatDate(calendar: Calendar, context: Context): String{
            val dateFormat: Format = DateFormat.getDateFormat(context)
            return dateFormat.format(calendar.time)
        }
    }
}
