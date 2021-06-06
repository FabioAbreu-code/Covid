package pt.ipg.covid

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BdTestesOpenHelper(context: Context?): SQLiteOpenHelper(context, NOME_BASE_DADOS, null, VERSAO_BASE_DADOS) {

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            TabelaUtentes(db).cria()
            TabelaMedicos(db).cria()
            TabelaUnidadeHospitalar(db).cria()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        const val NOME_BASE_DADOS = "Covid.db"
        const val VERSAO_BASE_DADOS = 1
    }
}