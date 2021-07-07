package pt.ipg.covid

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Utentes(
    var id: Long = -1,
    var Numero_de_Utente: String,
    var Nome: String,
    var Sexo: String,
    var Data_de_Nascimento: Int,
    var Telemovel: String,
    var Email: String,
    var Morada: String,
    var Id_Medico: Long,
    var Id_Unidade_Hospitalar: Long,
    var Id_Teste: Long,
    var nomeMedicos: String?
) {

    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {

            put(TabelaUtentes.CAMPO_NUM_UTENTE, Numero_de_Utente)
            put(TabelaUtentes.CAMPO_NOME, Nome)
            put(TabelaUtentes.CAMPO_SEXO, Sexo)
            put(TabelaUtentes.CAMPO_DATA_NASCIMENTO, Data_de_Nascimento)
            put(TabelaUtentes.CAMPO_NUM_TELEMOVEL, Telemovel)
            put(TabelaUtentes.CAMPO_EMAIL, Email)
            put(TabelaUtentes.CAMPO_MORADA, Morada)
            put(TabelaUtentes.CAMPO_ID_MEDICO, Id_Medico)
            put(TabelaUtentes.CAMPO_ID_UNIDADE_HOSPITALAR, Id_Unidade_Hospitalar)
            put(TabelaUtentes.CAMPO_ID_TESTE, Id_Teste)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Utentes {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNumero_de_Utente = cursor.getColumnIndex(TabelaUtentes.CAMPO_NUM_UTENTE)
            val colNome = cursor.getColumnIndex(TabelaUtentes.CAMPO_NOME)
            val colSexo = cursor.getColumnIndex(TabelaUtentes.CAMPO_SEXO)
            val colData_de_Nascimento = cursor.getColumnIndex(TabelaUtentes.CAMPO_DATA_NASCIMENTO)
            val colTelemovel = cursor.getColumnIndex(TabelaUtentes.CAMPO_NUM_TELEMOVEL)
            val colEmail = cursor.getColumnIndex(TabelaUtentes.CAMPO_EMAIL)
            val colMorada = cursor.getColumnIndex(TabelaUtentes.CAMPO_MORADA)
            val colId_Medico = cursor.getColumnIndex(TabelaUtentes.CAMPO_ID_MEDICO)
            val colId_Unidade_Hospitalar = cursor.getColumnIndex(TabelaUtentes.CAMPO_ID_UNIDADE_HOSPITALAR)
            val colId_Teste = cursor.getColumnIndex(TabelaUtentes.CAMPO_ID_TESTE)
            val colNomeMedic = cursor.getColumnIndex(TabelaUtentes.CAMPO_EXTERNO_NOME_MEDICOS)


            val id = cursor.getLong(colId)
            val Numero_de_Utente = cursor.getString(colNumero_de_Utente)
            val Nome = cursor.getString(colNome)
            val Sexo = cursor.getString(colSexo)
            val Data_de_Nascimento = cursor.getInt(colData_de_Nascimento)
            val Telemovel = cursor.getString(colTelemovel)
            val Email = cursor.getString(colEmail)
            val Morada = cursor.getString(colMorada)
            val Id_Medico = cursor.getLong(colId_Medico)
            val Id_Unidade_Hospitalar = cursor.getLong(colId_Unidade_Hospitalar)
            val Id_Teste = cursor.getLong(colId_Teste)
            val nomeMedicos = if (colNomeMedic != -1) cursor.getString(colNomeMedic) else null


            return Utentes(
                id,
                Numero_de_Utente,
                Nome,
                Sexo,
                Data_de_Nascimento,
                Telemovel,
                Email,
                Morada,
                Id_Medico,
                Id_Unidade_Hospitalar,
                Id_Teste,
                    nomeMedicos
            )
        }
    }
}