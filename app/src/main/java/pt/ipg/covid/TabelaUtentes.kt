package pt.ipg.covid

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaUtentes(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria(){
        db.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,$CAMPO_NUM_UTENTE TEXT NOT NULL, $CAMPO_NOME TEXT NOT NULL, $CAMPO_SEXO TEXT NOT NULL, $CAMPO_DATA_NASCIMENTO DATE NOT NULL, $CAMPO_NUM_TELEMOVEL TEXT NOT NULL, $CAMPO_EMAIL TEXT NOT NULL, $CAMPO_MORADA TEXT NOT NULL, $CAMPO_ID_MEDICO INTEGER NOT NULL, $CAMPO_ID_UNIDADE_HOSPITALAR INTEGER NOT NULL, $CAMPO_ID_TESTE INTEGER NOT NULL, FOREIGN KEY ($CAMPO_ID_MEDICO) REFERENCES ${TabelaMedicos.NOME_TABELA}, FOREIGN KEY ($CAMPO_ID_UNIDADE_HOSPITALAR) REFERENCES ${TabelaUnidadeHospitalar.NOME_TABELA}, FOREIGN KEY ($CAMPO_ID_TESTE) REFERENCES ${TabelaTeste.NOME_TABELA})")
    }

    fun insert(values: ContentValues): Long {
        return db.insert(NOME_TABELA,null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(NOME_TABELA,whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        val ultimaColuna = columns.size - 1

        var posColNomeCategoria = -1 // -1 indica que a coluna não foi pedida
        for (i in 0..ultimaColuna) {
            if (columns[i] == CAMPO_EXTERNO_NOME_MEDICOS) {
                posColNomeCategoria = i
                break
            }
        }

        if (posColNomeCategoria == -1) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
        }

        var colunas = ""
        for (i in 0..ultimaColuna) {
            if (i > 0) colunas += ","

            colunas += if (i == posColNomeCategoria) {
                "${TabelaMedicos.NOME_TABELA}.${TabelaMedicos.CAMPO_NOME} AS $CAMPO_EXTERNO_NOME_MEDICOS"
            } else {
                "${NOME_TABELA}.${columns[i]}"
            }
        }

        val tabelas = "$NOME_TABELA INNER JOIN ${TabelaMedicos.NOME_TABELA} ON ${TabelaMedicos.NOME_TABELA}.${BaseColumns._ID}=$CAMPO_ID_MEDICO"

        var sql = "SELECT $colunas FROM $tabelas"

        if (selection != null) sql += " WHERE $selection"

        if (groupBy != null) {
            sql += " GROUP BY $groupBy"
            if (having != null) " HAVING $having"
        }

        if (orderBy != null) sql += " ORDER BY $orderBy"

        return db.rawQuery(sql, selectionArgs)
    }



    companion object{
        const val NOME_TABELA = "Pessoas"
        const val CAMPO_NUM_UTENTE = "Numero_de_Utente"
        const val CAMPO_NOME = "Nome"
        const val CAMPO_SEXO = "Sexo"
        const val CAMPO_DATA_NASCIMENTO = "Data_de_Nascimento"
        const val CAMPO_NUM_TELEMOVEL = "Telemovel"
        const val CAMPO_EMAIL = "Email"
        const val CAMPO_MORADA = "Morada"
        const val CAMPO_ID_MEDICO = "Id_Medico"
        const val CAMPO_ID_UNIDADE_HOSPITALAR = "Id_Unidade_Hospitalar"
        const val CAMPO_ID_TESTE = "Id_Teste"
        const val CAMPO_EXTERNO_NOME_MEDICOS = "nome_Medicos"
        val TODOS_CAMPOS = arrayOf(BaseColumns._ID, CAMPO_NUM_UTENTE, CAMPO_NOME, CAMPO_SEXO, CAMPO_DATA_NASCIMENTO, CAMPO_NUM_TELEMOVEL, CAMPO_EMAIL, CAMPO_MORADA, CAMPO_ID_MEDICO, CAMPO_ID_UNIDADE_HOSPITALAR, CAMPO_ID_TESTE, CAMPO_EXTERNO_NOME_MEDICOS)
    }
}