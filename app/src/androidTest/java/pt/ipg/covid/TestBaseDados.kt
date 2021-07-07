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
    private fun getTabelaUnidadesHospitalares(db: SQLiteDatabase) = TabelaUnidadeHospitalar(db)
    private fun getTabelaTestes(db: SQLiteDatabase) = TabelaTeste(db)

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

    private fun insertUnidadesHospitalares(tabela: TabelaUnidadeHospitalar, unidadeHospitalar: UnidadesHospitalares): Long {
        val id = tabela.insert(unidadeHospitalar.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun insertTest(tabela: TabelaTeste, teste: Teste): Long {
        val id = tabela.insert(teste.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun GetUtentesBd(tabelaUtentes: TabelaUtentes, id: Long): Utentes {
        val cursor = tabelaUtentes.query(
            TabelaUtentes.TODOS_CAMPOS,
            "${TabelaUtentes.NOME_TABELA}.${BaseColumns._ID}=?",
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
            "${TabelaMedicos.NOME_TABELA}.${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Medicos.fromCursor(cursor)

    }

    private fun GetTestBd(tabelaTeste: TabelaTeste, id: Long): Teste {
        val cursor = tabelaTeste.query(
            TabelaTeste.TODOS_CAMPOS,
            "${TabelaTeste.NOME_TABELA}.${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Teste.fromCursor(cursor)

    }

    private fun GetUnidadesHospitalaresBd(tabelaUnidadeHospitalar: TabelaUnidadeHospitalar, id: Long): UnidadesHospitalares {
        val cursor = tabelaUnidadeHospitalar.query(
            TabelaUnidadeHospitalar.TODOS_CAMPOS,
            "${TabelaUnidadeHospitalar.NOME_TABELA}.${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return UnidadesHospitalares.fromCursor(cursor)

    }

    @Before
    fun apagaBaseDados(){
        getAppContext().deleteDatabase(BdTestesOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueLerUtentes() {

        val db = getBdTestesOpenHelper().writableDatabase

        val tabelaMedicos = TabelaMedicos(db)
        val medicos = Medicos(Nome = "Maria", Telemovel = "936873505", Email= "maria@gmail.com")
        medicos.id = insereMedico(tabelaMedicos, medicos)

        val tabelaUnidadeHospitalar = TabelaUnidadeHospitalar(db)
        val unidadesHospitalares = UnidadesHospitalares(Nome = "Hospital Dr. Nélio Mendonça", Morada = "Av. Luís de Camões 6180")
        unidadesHospitalares.id = insertUnidadesHospitalares(tabelaUnidadeHospitalar, unidadesHospitalares)

        val tabelaTeste = TabelaTeste(db)
        val testes = Teste(Data_do_Teste = 15062021, Resultado = "Negativo")
        testes.id = insertTest(tabelaTeste, testes)

        val tabelaUtentes = TabelaUtentes(db)
        val utentes = Utentes(
                Numero_de_Utente = "797941504",
                Nome = "Fábio Emanuel Fiqueli Abreu",
                Sexo = "Masculino",
                Data_de_Nascimento = 21071999,
                Telemovel = "936873504",
                Email = "fabiofiqueli@hotmail.com",
                Morada = "Madeira, Ribeira Brava Nº11",
                Id_Medico = medicos.id,
                Id_Unidade_Hospitalar = unidadesHospitalares.id,
                Id_Teste = testes.id,
                nomeMedicos = medicos.nome // necessário apenas nos testes
        )
        utentes.id = insereUtente(tabelaUtentes, utentes)

        assertEquals(utentes, GetUtentesBd(tabelaUtentes, utentes.id))

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

/*
    @Test
    fun consegueLerUnidadesHospitalares() {

        val db = getBdTestesOpenHelper().writableDatabase
        val tabelaUnidadeHospitalar = getTabelaUnidadesHospitalares(db)

        val unidadeHospitalar = UnidadesHospitalares(Nome = "Hospital Dr. Nélio Mendonça", Morada = "Av. Luís de Camões 6180")
        unidadeHospitalar.id = insertUnidadesHospitalares(tabelaUnidadeHospitalar, unidadeHospitalar)

        val unidadeHospitalarBd = GetUnidadesHospitalaresBd(tabelaUnidadeHospitalar, unidadeHospitalar.id)
        assertEquals(unidadeHospitalar, unidadeHospitalarBd)

        db.close()
    }

 */

    @Test
    fun consegueLerTestes() {

        val db = getBdTestesOpenHelper().writableDatabase
        val tabelaTeste = getTabelaTestes(db)

        val teste = Teste(Data_do_Teste = 15062021, Resultado = "Negativo")
        teste.id = insertTest(tabelaTeste, teste)

        val testeBd = GetTestBd(tabelaTeste, teste.id)
        assertEquals(teste, testeBd)

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

        val tabelaMedicos = TabelaMedicos(db)
        val medicos = Medicos(Nome = "Maria", Telemovel = "936873505", Email= "maria@gmail.com")
        medicos.id = insereMedico(tabelaMedicos, medicos)

        val tabelaUnidadeHospitalar = TabelaUnidadeHospitalar(db)
        val unidadesHospitalares = UnidadesHospitalares(Nome = "Hospital Dr. Nélio Mendonça", Morada = "Av. Luís de Camões 6180")
        unidadesHospitalares.id = insertUnidadesHospitalares(tabelaUnidadeHospitalar, unidadesHospitalares)

        val tabelaTeste = TabelaTeste(db)
        val testes = Teste(Data_do_Teste = 15062021, Resultado = "Negativo")
        testes.id = insertTest(tabelaTeste, testes)

        val utentes = Utentes(
                Numero_de_Utente = "797941504",
                Nome = "Fábio Emanuel Fiqueli Abreu",
                Sexo = "Masculino",
                Data_de_Nascimento = 21071999,
                Telemovel = "936873504",
                Email = "fabiofiqueli@hotmail.com",
                Morada = "Madeira, Ribeira Brava Nº11",
                Id_Medico = medicos.id,
                Id_Unidade_Hospitalar = unidadesHospitalares.id,
                Id_Teste = testes.id,
                nomeMedicos = medicos.nome // necessário apenas nos testes
        )

        insereUtente(getTableUtentes(db), Utentes( Numero_de_Utente = "797941504", Nome = "Fábio Emanuel Fiqueli Abreu", Sexo = "Masculino", Data_de_Nascimento = 21071999, Telemovel = "936873504", Email = "fabiofiqueli@hotmail.com", Morada = "Madeira, Ribeira Brava Nº11", Id_Medico = medicos.id, Id_Unidade_Hospitalar = unidadesHospitalares.id, Id_Teste = testes.id))

        db.close()
    }

    @Test
    fun consegueInserirMedicos(){
        val db = getBdTestesOpenHelper().writableDatabase

        insereMedico(getTabelaMedicos(db), Medicos(Nome = "Maria", Telemovel = "936873505", Email= "maria@gmail.com"))

        db.close()
    }

    @Test
    fun consegueInserirUnidadesHospitalares(){

        val db = getBdTestesOpenHelper().writableDatabase

        insertUnidadesHospitalares(getTabelaUnidadesHospitalares(db), UnidadesHospitalares(Nome = "Hospital Dr. Nélio Mendonça", Morada = "Av. Luís de Camões 6180"))

        db.close()

    }

    @Test
    fun consegueInserirTeste(){
        val db = getBdTestesOpenHelper().writableDatabase
        val tabelaTeste = TabelaTeste(db)

        val teste = Teste(Data_do_Teste = 15062021, Resultado = "Negativo")
        teste.id = insertTest(tabelaTeste, teste)

        assertEquals(teste, GetTestBd(tabelaTeste, teste.id))

        db.close()
    }

    @Test
    fun consegueAlterarVacinas(){

        val db = getBdTestesOpenHelper().writableDatabase

        val tabelaUtentes = getTableUtentes(db)

        val tabelaMedicos = TabelaMedicos(db)
        val medicos = Medicos(Nome = "Maria", Telemovel = "936873505", Email= "maria@gmail.com")
        medicos.id = insereMedico(tabelaMedicos, medicos)

        val tabelaUnidadeHospitalar = TabelaUnidadeHospitalar(db)
        val unidadesHospitalares = UnidadesHospitalares(Nome = "Hospital Dr. Nélio Mendonça", Morada = "Av. Luís de Camões 6180")
        unidadesHospitalares.id = insertUnidadesHospitalares(tabelaUnidadeHospitalar, unidadesHospitalares)

        val tabelaTeste = TabelaTeste(db)
        val testes = Teste(Data_do_Teste = 15062021, Resultado = "Negativo")
        testes.id = insertTest(tabelaTeste, testes)

        val utentes = Utentes(
                Numero_de_Utente = "797941504",
                Nome = "Fábio Emanuel Fiqueli Abreu",
                Sexo = "Masculino",
                Data_de_Nascimento = 21071999,
                Telemovel = "936873504",
                Email = "fabiofiqueli@hotmail.com",
                Morada = "Madeira, Ribeira Brava Nº11",
                Id_Medico = medicos.id,
                Id_Unidade_Hospitalar = unidadesHospitalares.id,
                Id_Teste = testes.id,
                nomeMedicos = medicos.nome // necessário apenas nos testes
        )

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

    fun consegueAlterarUnidadesHospitalares() {

        val db = getBdTestesOpenHelper().writableDatabase

        val tabelaUnidadeHospitalar = getTabelaUnidadesHospitalares(db)
        val unidadeHospitalar = UnidadesHospitalares(Nome = "Hospital Dr. Nélio Mendonça", Morada = "Av. Luís de Camões 6180")
        unidadeHospitalar.id = insertUnidadesHospitalares(tabelaUnidadeHospitalar , unidadeHospitalar)

        val registosAlterados = tabelaUnidadeHospitalar.update(
            unidadeHospitalar.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(unidadeHospitalar.id.toString())
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

    fun consegueAlterarTeste() {

        val db = getBdTestesOpenHelper().writableDatabase

        val tabelaTeste = getTabelaTestes(db)
        val teste = Teste(Data_do_Teste = 15062021, Resultado = "Negativo")
        teste.id = insertTest(tabelaTeste, teste)


        val registosAlterados = tabelaTeste.update(
            teste.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(teste.id.toString())
        )

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueApagarUtentes() {

        val db = getBdTestesOpenHelper().writableDatabase
        val tabelaUtentes = getTableUtentes(db)
        val tabelaMedicos = TabelaMedicos(db)
        val medicos = Medicos(Nome = "Maria", Telemovel = "936873505", Email= "maria@gmail.com")
        medicos.id = insereMedico(tabelaMedicos, medicos)

        val tabelaUnidadeHospitalar = TabelaUnidadeHospitalar(db)
        val unidadesHospitalares = UnidadesHospitalares(Nome = "Hospital Dr. Nélio Mendonça", Morada = "Av. Luís de Camões 6180")
        unidadesHospitalares.id = insertUnidadesHospitalares(tabelaUnidadeHospitalar, unidadesHospitalares)

        val tabelaTeste = TabelaTeste(db)
        val testes = Teste(Data_do_Teste = 15062021, Resultado = "Negativo")
        testes.id = insertTest(tabelaTeste, testes)

        val utentes = Utentes(
                Numero_de_Utente = "797941504",
                Nome = "Fábio Emanuel Fiqueli Abreu",
                Sexo = "Masculino",
                Data_de_Nascimento = 21071999,
                Telemovel = "936873504",
                Email = "fabiofiqueli@hotmail.com",
                Morada = "Madeira, Ribeira Brava Nº11",
                Id_Medico = medicos.id,
                Id_Unidade_Hospitalar = unidadesHospitalares.id,
                Id_Teste = testes.id,
                nomeMedicos = medicos.nome // necessário apenas nos testes
        )
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

    @Test

    fun consegueApagarUnidadesHospitalares() {

        val db = getBdTestesOpenHelper().writableDatabase
        val tabelaUnidadeHospitalar = getTabelaUnidadesHospitalares(db)
        val unidadeHospitalar = UnidadesHospitalares(Nome = "Hospital Dr. Nélio Mendonça", Morada = "Av. Luís de Camões 6180")
        unidadeHospitalar.id = insertUnidadesHospitalares(tabelaUnidadeHospitalar, unidadeHospitalar)

        val registosApagados = tabelaUnidadeHospitalar.delete("${BaseColumns._ID}=?",arrayOf(unidadeHospitalar.id.toString()))
        assertEquals(1, registosApagados)

        db.close()
    }

    @Test
    fun consegueApagarTestes() {

        val db = getBdTestesOpenHelper().writableDatabase
        val tabelaTeste = getTabelaTestes(db)
        val teste = Teste(Data_do_Teste = 15062021, Resultado = "Negativo")
        teste.id = insertTest(tabelaTeste, teste)

        val registosApagados = tabelaTeste.delete("${BaseColumns._ID}=?",arrayOf(teste.id.toString()))
        assertEquals(1, registosApagados)

        db.close()
    }
}