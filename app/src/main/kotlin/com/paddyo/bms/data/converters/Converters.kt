package com.paddyo.bms.data.converters

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromStringList(list: List<String>): String = list.joinToString(",")

    @TypeConverter
    fun toStringList(str: String): List<String> = if (str.isEmpty()) emptyList() else str.split(",")
}