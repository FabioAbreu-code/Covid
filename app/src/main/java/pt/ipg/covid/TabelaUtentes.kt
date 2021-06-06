package pt.ipg.covid

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaUtentes(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria(){
        db.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_DATA_TESTE  DATE NOT NULL, $CAMPO_RESULTADO TEXT NOT NULL, $CAMPO_NUM_UTENTE TEXT NOT NULL, $CAMPO_NOME TEXT NOT NULL, $CAMPO_SEXO TEXT NOT NULL, $CAMPO_DATA_NASCIMENTO DATE NOT NULL, $CAMPO_NUM_TELEMOVEL TEXT NOT NULL, $CAMPO_EMAIL TEXT NOT NULL, $CAMPO_MORADA TEXT NOT NULL, $CAMPO_ID_MEDICO INTEGER NOT NULL, $CAMPO_ID_UNIDADE_HOSPITALAR INTEGER NOT NULL, FOREIGN KEY ($CAMPO_ID_MEDICO) REFERENCES ${TabelaMedicos.NOME_TABELA2}, FOREIGN KEY ($CAMPO_ID_UNIDADE_HOSPITALAR) REFERENCES ${TabelaUnidadeHospitalar.NOME_TABELA3})")
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
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object{
        const val NOME_TABELA = "Pessoas"
        const val CAMPO_DATA_TESTE = "Data_do_Teste"
        const val CAMPO_RESULTADO = "Resultado"
        const val CAMPO_NUM_UTENTE = "Numero_de_Utente"
        const val CAMPO_NOME = "Nome"
        const val CAMPO_SEXO = "Sexo"
        const val CAMPO_DATA_NASCIMENTO = "Data_de_Nascimento"
        const val CAMPO_NUM_TELEMOVEL = "Telemovel"
        const val CAMPO_EMAIL = "Email"
        const val CAMPO_MORADA = "Morada"
        const val CAMPO_ID_MEDICO = "Id_Medico"
        const val CAMPO_ID_UNIDADE_HOSPITALAR = "Id_Unidade_Hospitalar"

        val TODOS_CAMPOS = arrayOf(BaseColumns._ID, CAMPO_DATA_TESTE, CAMPO_RESULTADO, CAMPO_NUM_UTENTE, CAMPO_NOME, CAMPO_SEXO, CAMPO_DATA_NASCIMENTO, CAMPO_NUM_TELEMOVEL, CAMPO_EMAIL, CAMPO_MORADA, CAMPO_ID_MEDICO, CAMPO_ID_UNIDADE_HOSPITALAR)
    }
}