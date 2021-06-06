package pt.ipg.covid

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
    class TestBaseDados {
    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getBdTestesOpenHelper() = BdTestesOpenHelper(getAppContext())
    private fun getTableUtentes(db: SQLiteDatabase) = TabelaUtentes(db)

    private fun insereUtente(tabela: TabelaUtentes, utentes: Utentes): Long {
        val id = tabela.insert(utentes.toContentValues())
        assertNotEquals(-1, id)

        return id
    }


    @Before
    fun apagaBaseDados(){
        getAppContext().deleteDatabase(BdTestesOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados(){
        val db = getBdTestesOpenHelper().readableDatabase
        assert(db.isOpen)
        db.close()
    }

    @Test
    fun consegueInserirUtentes() {
        val db = getBdTestesOpenHelper().writableDatabase

        insereUtente(getTableUtentes(db), Utentes(Data_do_Teste = "06/06/2021", Resultado = "Negativo", Numero_de_Utente = "797941504", Nome = "Fábio Emanuel Fiqueli Abreu", Sexo = "Masculino", Data_de_Nascimento = "21/07/1999", Telemovel = "936873504", Email = "fabiofiqueli@hotmail.com", Morada = "Madeira, Ribeira Brava Nº11", Id_Medico = "3", Id_Unidade_Hospitalar = "Hospital Dr. Nélio Mendonça"))

        db.close()
    }

    @Test
    fun consegueAlterarVacinas(){

        val db = getBdTestesOpenHelper().writableDatabase

        val tabelaUtentes = getTableUtentes(db)
        val utentes = Utentes(Data_do_Teste = "06/06/2021", Resultado = "Negativo", Numero_de_Utente = "797941504", Nome = "Fábio Emanuel Fiqueli Abreu", Sexo = "Masculino", Data_de_Nascimento = "21/07/1999", Telemovel = "936873504", Email = "fabiofiqueli@hotmail.com", Morada = "Madeira, Ribeira Brava Nº11", Id_Medico = "3", Id_Unidade_Hospitalar = "Hospital Dr. Nélio Mendonça")
        utentes.id = insereUtente(tabelaUtentes, utentes)

        val registosAlterados = tabelaUtentes.update(
            utentes.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(utentes.id.toString())
        )

        assertEquals(1, registosAlterados)

        db.close()
    }

}