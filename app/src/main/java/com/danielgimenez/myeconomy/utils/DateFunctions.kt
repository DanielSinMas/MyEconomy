package com.danielgimenez.myeconomy.utils

import android.content.Context
import android.text.format.DateFormat
import java.lang.Exception
import java.text.Format
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DateFunctions{
    companion object{
        fun formatDate(calendar: Calendar, context: Context): String{
            val dateFormat: Format = DateFormat.getDateFormat(context)
            return dateFormat.format(calendar.time)
        }

        fun getLocalDate(date: String): LocalDate{
            val split = date.split("/")
            var newString = ""
            newString = if(split[0].toInt() < 10) newString + "0"+split[0]+"/"
            else newString + split[0]+"/"
            newString = if(split[1].toInt() < 10) newString + "0"+split[1]+"/"
            else newString + split[1]+"/"
            newString += split[2]
            try{
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yy")
                return LocalDate.parse(newString, formatter)
            } catch(e: Exception){
                val formatter = DateTimeFormatter.ofPattern("MM/dd/yy")
                return LocalDate.parse(newString, formatter)
            }
        }

        fun getDateToShow(date: String): String {
            val split = date.split("-")
            return split[2]+"/"+split[1]
        }
    }
}
