package pt.ipg.covid

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Medicos(
    var id: Long = -1,
    var Nome: String,
    var Telemovel: String,
    var Email: String,

) {

    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaMedicos.CAMPO_NOME, Nome)
            put(TabelaMedicos.CAMPO_NUM_TELEMOVEL, Telemovel)
            put(TabelaMedicos.CAMPO_EMAIL, Email)

        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Medicos {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaMedicos.CAMPO_NOME)
            val colTelemovel = cursor.getColumnIndex(TabelaMedicos.CAMPO_NUM_TELEMOVEL)
            val colEmail = cursor.getColumnIndex(TabelaMedicos.CAMPO_EMAIL)

            val id = cursor.getLong(colId)
            val Nome = cursor.getString(colNome)
            val Telemovel = cursor.getString(colTelemovel)
            val Email = cursor.getString(colEmail)


            return Medicos(
                id,
                Nome,
                Telemovel,
                Email
            )
        }
    }
}
