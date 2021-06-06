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
    private fun getTabelaMedicos(db: SQLiteDatabase) = TabelaMedicos(db)

    private fun insereUtente(tabela: TabelaUtentes, utentes: Utentes): Long {
        val id = tabela.insert(utentes.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun insereMedico(tabela: TabelaMedicos, medicos: Medicos): Long {
        val id = tabela.insert(medicos.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun GetUtenteBd(tabelaMedicos: TabelaUtentes, id: Long): Utentes {
        val cursor = tabelaMedicos.query(
            TabelaUtentes.TODOS_CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Utentes.fromCursor(cursor)

    }

    private fun GetMedicosBd(tabelaMedicos: TabelaMedicos, id: Long): Medicos {
        val cursor = tabelaMedicos.query(
            TabelaMedicos.TODOS_CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Medicos.fromCursor(cursor)

    }

    @Before
    fun apagaBaseDados(){
        getAppContext().deleteDatabase(BdTestesOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueLerUtentes() {

        val db = getBdTestesOpenHelper().writableDatabase
        val tabelaUtentes = getTableUtentes(db)

        val utentes = Utentes(Data_do_Teste = "06/06/2021", Resultado = "Negativo", Numero_de_Utente = "797941504", Nome = "Fábio Emanuel Fiqueli Abreu", Sexo = "Masculino", Data_de_Nascimento = "21/07/1999", Telemovel = "936873504", Email = "fabiofiqueli@hotmail.com", Morada = "Madeira, Ribeira Brava Nº11", Id_Medico = "3", Id_Unidade_Hospitalar = "Hospital Dr. Nélio Mendonça")
        utentes.id = insereUtente(tabelaUtentes, utentes)

        val utenteBd = GetUtenteBd(tabelaUtentes, utentes.id)
        assertEquals(utentes, utenteBd)

        db.close()
    }

    @Test
    fun consegueLerMedicos() {

        val db = getBdTestesOpenHelper().writableDatabase
        val tabelaMedicos = getTabelaMedicos(db)

        val medicos = Medicos(Nome = "Maria", Telemovel = "936873505", Email= "maria@gmail.com")
        medicos.id = insereMedico(tabelaMedicos, medicos)

        val medicosBd = GetMedicosBd(tabelaMedicos, medicos.id)
        assertEquals(medicos, medicosBd)

        db.close()
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
    fun consegueInserirMedicos(){
        val db = getBdTestesOpenHelper().writableDatabase

        insereMedico(getTabelaMedicos(db), Medicos(Nome = "Maria", Telemovel = "936873505", Email= "maria@gmail.com"))

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

    @Test

    fun consegueAlterarMedicos() {

        val db = getBdTestesOpenHelper().writableDatabase

        val tabelaMedicos = getTabelaMedicos(db)
        val medicos = Medicos(Nome = "Maria", Telemovel = "936873505", Email= "maria@gmail.com")
        medicos.id = insereMedico(tabelaMedicos, medicos)


        val registosAlterados = tabelaMedicos.update(
            medicos.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(medicos.id.toString())
        )

        assertEquals(1, registosAlterados)

        db.close()
    }


    @Test
    fun consegueApagarUtentes() {

        val db = getBdTestesOpenHelper().writableDatabase
        val tabelaUtentes = getTableUtentes(db)
        val utentes = Utentes(Data_do_Teste = "06/06/2021", Resultado = "Negativo", Numero_de_Utente = "797941504", Nome = "Fábio Emanuel Fiqueli Abreu", Sexo = "Masculino", Data_de_Nascimento = "21/07/1999", Telemovel = "936873504", Email = "fabiofiqueli@hotmail.com", Morada = "Madeira, Ribeira Brava Nº11", Id_Medico = "3", Id_Unidade_Hospitalar = "Hospital Dr. Nélio Mendonça")
        utentes.id = insereUtente(tabelaUtentes, utentes)

        val registosApagados = tabelaUtentes.delete("${BaseColumns._ID}=?",arrayOf(utentes.id.toString()))
        assertEquals(1, registosApagados)

        db.close()
    }

    @Test

    fun consegueApagarMedicos() {

        val db = getBdTestesOpenHelper().writableDatabase
        val tabelaMedicos = getTabelaMedicos(db)
        val medicos = Medicos(Nome = "Maria", Telemovel = "936873505", Email= "maria@gmail.com")
        medicos.id = insereMedico(tabelaMedicos, medicos)

        val registosApagados = tabelaMedicos.delete("${BaseColumns._ID}=?",arrayOf(medicos.id.toString()))
        assertEquals(1, registosApagados)

        db.close()
    }

}