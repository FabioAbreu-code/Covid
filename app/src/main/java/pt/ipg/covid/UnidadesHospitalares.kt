package pt.ipg.covid

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

class UnidadesHospitalares (
    var id: Long = -1,
    var Nome: String,
    var Morada: String) {

        fun toContentValues(): ContentValues {
            val valores = ContentValues().apply {
                put(TabelaUnidadeHospitalar.CAMPO_NOME, Nome)
                put(TabelaUnidadeHospitalar.CAMPO_MORADA, Morada)
            }

            return valores
        }

        companion object {
            fun fromCursor(cursor: Cursor): UnidadesHospitalares {
                val colId = cursor.getColumnIndex(BaseColumns._ID)
                val colNome = cursor.getColumnIndex(TabelaUnidadeHospitalar.CAMPO_NOME)
                val colMorada = cursor.getColumnIndex(TabelaUnidadeHospitalar.CAMPO_MORADA)

                val id = cursor.getLong(colId)
                val Nome = cursor.getString(colNome)
                val Morada = cursor.getString(colMorada)

                return UnidadesHospitalares(
                    id,
                    Nome,
                    Morada
                )
            }
        }
}