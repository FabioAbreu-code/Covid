package pt.ipg.covid

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Teste(
    var id: Long = -1,
    var Data_do_Teste: Int,
    var Resultado: String,

) {

    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaTeste.CAMPO_DATA_TESTE, Data_do_Teste)
            put(TabelaTeste.CAMPO_RESULTADO, Resultado)

        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Teste {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colData_do_Teste = cursor.getColumnIndex(TabelaTeste.CAMPO_DATA_TESTE)
            val colResultado = cursor.getColumnIndex(TabelaTeste.CAMPO_RESULTADO)


            val id = cursor.getLong(colId)
            val Data_do_Teste = cursor.getInt(colData_do_Teste)
            val Resultado = cursor.getString(colResultado)


            return Teste(
                id,
                Data_do_Teste,
                Resultado
            )
        }
    }
}