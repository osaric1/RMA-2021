package ba.etf.rma21.projekat

import androidx.room.TypeConverter

class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun toString(lista: List<String>): String {
            return lista.joinToString(separator = ",")
        }

        @TypeConverter
        @JvmStatic
        fun fromString(opcije: String): List<String>{
            return opcije.split(",").toList()
        }
    }
}