package pt.ipg.covid

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class ContentProviderCovid : ContentProvider() {
    private var bdTestesOpenHelper : BdTestesOpenHelper? = null

    /**
     * Implement this to initialize your content provider on startup.
     * This method is called for all registered content providers on the
     * application main thread at application launch time.  It must not perform
     * lengthy operations, or application startup will be delayed.
     *
     *
     * You should defer nontrivial initialization (such as opening,
     * upgrading, and scanning databases) until the content provider is used
     * (via [.query], [.insert], etc).  Deferred initialization
     * keeps application startup fast, avoids unnecessary work if the provider
     * turns out not to be needed, and stops database errors (such as a full
     * disk) from halting application launch.
     *
     *
     * If you use SQLite, [android.database.sqlite.SQLiteOpenHelper]
     * is a helpful utility class that makes it easy to manage databases,
     * and will automatically defer opening until first use.  If you do use
     * SQLiteOpenHelper, make sure to avoid calling
     * [android.database.sqlite.SQLiteOpenHelper.getReadableDatabase] or
     * [android.database.sqlite.SQLiteOpenHelper.getWritableDatabase]
     * from this method.  (Instead, override
     * [android.database.sqlite.SQLiteOpenHelper.onOpen] to initialize the
     * database when it is first opened.)
     *
     * @return true if the provider was successfully loaded, false otherwise
     */
    override fun onCreate(): Boolean {
        bdTestesOpenHelper = BdTestesOpenHelper(context)

        return true
    }

    /**
     * Implement this to handle query requests from clients.
     *
     *
     * Apps targeting [android.os.Build.VERSION_CODES.O] or higher should override
     * [.query] and provide a stub
     * implementation of this method.
     *
     *
     * This method can be called from multiple threads, as described in
     * [Processes
 * and Threads]({@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     *
     * Example client call:
     *
     *
     * <pre>// Request a specific record.
     * Cursor managedCursor = managedQuery(
     * ContentUris.withAppendedId(Contacts.People.CONTENT_URI, 2),
     * projection,    // Which columns to return.
     * null,          // WHERE clause.
     * null,          // WHERE clause value substitution
     * People.NAME + " ASC");   // Sort order.</pre>
     * Example implementation:
     *
     *
     * <pre>// SQLiteQueryBuilder is a helper class that creates the
     * // proper SQL syntax for us.
     * SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
     *
     * // Set the table we're querying.
     * qBuilder.setTables(DATABASE_TABLE_NAME);
     *
     * // If the query ends in a specific record number, we're
     * // being asked for a specific record, so set the
     * // WHERE clause in our query.
     * if((URI_MATCHER.match(uri)) == SPECIFIC_MESSAGE){
     * qBuilder.appendWhere("_id=" + uri.getPathLeafId());
     * }
     *
     * // Make the query.
     * Cursor c = qBuilder.query(mDb,
     * projection,
     * selection,
     * selectionArgs,
     * groupBy,
     * having,
     * sortOrder);
     * c.setNotificationUri(getContext().getContentResolver(), uri);
     * return c;</pre>
     *
     * @param uri The URI to query. This will be the full URI sent by the client;
     * if the client is requesting a specific record, the URI will end in a record number
     * that the implementation should parse and add to a WHERE or HAVING clause, specifying
     * that _id value.
     * @param projection The list of columns to put into the cursor. If
     * `null` all columns are included.
     * @param selection A selection criteria to apply when filtering rows.
     * If `null` then all rows are included.
     * @param selectionArgs You may include ?s in selection, which will be replaced by
     * the values from selectionArgs, in order that they appear in the selection.
     * The values will be bound as Strings.
     * @param sortOrder How the rows in the cursor should be sorted.
     * If `null` then the provider is free to define the sort order.
     * @return a Cursor or `null`.
     */
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val bd = bdTestesOpenHelper!!.readableDatabase

        return when (getUriMatcher().match(uri)){

            URI_UTENTES -> TabelaUtentes(bd).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )
            URI_UTENTES_ESPECIFICO -> TabelaUtentes(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )
            URI_MEDICOS -> TabelaMedicos(bd).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )
            URI_MEDICOS_ESPECIFICA -> TabelaMedicos(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )
            URI_UNIDADES_HOSPITALARES -> TabelaUnidadeHospitalar(bd).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )
            URI_UNIDADES_HOSPITALARES_ESPECIFICO -> TabelaUnidadeHospitalar(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )
            URI_TESTES -> TabelaTeste(bd).query(
                projection as Array<String>,
                selection,
                selectionArgs as Array<String>?,
                null,
                null,
                sortOrder
            )
            URI_TESTES_ESPECIFICA -> TabelaTeste(bd).query(
                projection as Array<String>,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!),
                null,
                null,
                null
            )
            else -> null
        }
    }

    /**
     * Implement this to handle requests for the MIME type of the data at the
     * given URI.  The returned MIME type should start with
     * `vnd.android.cursor.item` for a single record,
     * or `vnd.android.cursor.dir/` for multiple items.
     * This method can be called from multiple threads, as described in
     * [Processes
 * and Threads]({@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     *
     * Note that there are no permissions needed for an application to
     * access this information; if your content provider requires read and/or
     * write permissions, or is not exported, all applications can still call
     * this method regardless of their access permissions.  This allows them
     * to retrieve the MIME type for a URI when dispatching intents.
     *
     * @param uri the URI to query.
     * @return a MIME type string, or `null` if there is no type.
     */
    override fun getType(uri: Uri): String? {
        return when (getUriMatcher().match(uri)){
            URI_UTENTES -> "$MULTIPLOS_ITEMS/$UTENTES"
            URI_UTENTES_ESPECIFICO -> "$UNICO_ITEM/$UTENTES"
            URI_MEDICOS -> "$MULTIPLOS_ITEMS/$MEDICOS"
            URI_MEDICOS_ESPECIFICA -> "$UNICO_ITEM/$MEDICOS"
            URI_UNIDADES_HOSPITALARES -> "$MULTIPLOS_ITEMS/$UNIDADES_HOSPITALARES"
            URI_UNIDADES_HOSPITALARES_ESPECIFICO -> "$UNICO_ITEM/$UNIDADES_HOSPITALARES"
            URI_TESTES -> "$MULTIPLOS_ITEMS/$TESTES"
            URI_TESTES_ESPECIFICA -> "$UNICO_ITEM/$TESTES"
            else -> null
        }
    }


    /**
     * Implement this to handle requests to insert a new row. As a courtesy,
     * call
     * [ notifyChange()][ContentResolver.notifyChange] after inserting. This method can be called from multiple
     * threads, as described in [Processes
 * and Threads](
      {@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     * @param uri The content:// URI of the insertion request.
     * @param values A set of column_name/value pairs to add to the database.
     * @return The URI for the newly inserted item.
     */
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val bd = bdTestesOpenHelper!!.writableDatabase

        val id =  when (getUriMatcher().match(uri)){

            URI_UTENTES -> TabelaUtentes(bd).insert(values!!)

            URI_MEDICOS -> TabelaMedicos(bd).insert(values!!)

            URI_UNIDADES_HOSPITALARES -> TabelaUnidadeHospitalar(bd).insert(values!!)

            URI_TESTES -> TabelaTeste(bd).insert(values!!)

            else -> -1L
        }

        if(id == -1L) return null

        return Uri.withAppendedPath(uri, id.toString())
    }

    /**
     * Implement this to handle requests to delete one or more rows. The
     * implementation should apply the selection clause when performing
     * deletion, allowing the operation to affect multiple rows in a directory.
     * As a courtesy, call
     * [ notifyChange()][ContentResolver.notifyChange] after deleting. This method can be called from multiple
     * threads, as described in [Processes
 * and Threads](
      {@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     *
     * The implementation is responsible for parsing out a row ID at the end of
     * the URI, if a specific row is being deleted. That is, the client would
     * pass in `content://contacts/people/22` and the implementation
     * is responsible for parsing the record number (22) when creating a SQL
     * statement.
     *
     * @param uri The full URI to query, including a row ID (if a specific
     * record is requested).
     * @param selection An optional restriction to apply to rows when deleting.
     * @return The number of rows affected.
     * @throws SQLException
     */
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val bd = bdTestesOpenHelper!!.writableDatabase

        return when (getUriMatcher().match(uri)){

            URI_UTENTES_ESPECIFICO -> TabelaUtentes(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_MEDICOS_ESPECIFICA -> TabelaMedicos(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_UNIDADES_HOSPITALARES_ESPECIFICO -> TabelaUnidadeHospitalar(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_TESTES_ESPECIFICA -> TabelaTeste(bd).delete(
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )
            else -> 0
        }
    }

    /**
     * Implement this to handle requests to update one or more rows. The
     * implementation should update all rows matching the selection to set the
     * columns according to the provided values map. As a courtesy, call
     * [ notifyChange()][ContentResolver.notifyChange] after updating. This method can be called from multiple
     * threads, as described in [Processes
 * and Threads](
      {@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     * @param uri The URI to query. This can potentially have a record ID if
     * this is an update request for a specific record.
     * @param values A set of column_name/value pairs to update in the database.
     * @param selection An optional filter to match rows to update.
     * @return the number of rows affected.
     */
    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val bd = bdTestesOpenHelper!!.writableDatabase

        return when (getUriMatcher().match(uri)){

            URI_UTENTES_ESPECIFICO -> TabelaUtentes(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_MEDICOS_ESPECIFICA -> TabelaMedicos(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_UNIDADES_HOSPITALARES_ESPECIFICO -> TabelaUnidadeHospitalar(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )

            URI_TESTES_ESPECIFICA -> TabelaTeste(bd).update(
                values!!,
                "${BaseColumns._ID}=?",
                arrayOf(uri.lastPathSegment!!)
            )
            else -> 0
        }
    }

    companion object{

        const val AUTHORITY = "pt.ipg.covid"
        const val UTENTES = "utentes"
        const val MEDICOS = "medicos"
        const val UNIDADES_HOSPITALARES = "unidades_Hospitalares"
        const val TESTES = "testes"

        private const val URI_UTENTES = 100
        private const val URI_UTENTES_ESPECIFICO = 101
        private const val URI_MEDICOS = 200
        private const val URI_MEDICOS_ESPECIFICA = 201
        private const val URI_UNIDADES_HOSPITALARES = 300
        private const val URI_UNIDADES_HOSPITALARES_ESPECIFICO = 301
        private const val URI_TESTES = 400
        private const val URI_TESTES_ESPECIFICA = 401

        private const val MULTIPLOS_ITEMS = "vnd.adroid.cursor.dir"
        private const val UNICO_ITEM = "vnd.android.cursor.item"

        private val ENDERECO_BASE = Uri.parse("content://$AUTHORITY")
        public val ENDERECO_UTENTES = Uri.withAppendedPath(ENDERECO_BASE, UTENTES)
        public val ENDERECO_UNIDADES_HOSPITALARES = Uri.withAppendedPath(ENDERECO_BASE, UNIDADES_HOSPITALARES)



        private fun getUriMatcher() : UriMatcher {
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)


            uriMatcher.addURI(AUTHORITY, UTENTES, URI_UTENTES)
            uriMatcher.addURI(AUTHORITY,"$UTENTES/#", URI_UTENTES_ESPECIFICO)
            uriMatcher.addURI(AUTHORITY, MEDICOS, URI_MEDICOS)
            uriMatcher.addURI(AUTHORITY, "$MEDICOS/#", URI_MEDICOS_ESPECIFICA)
            uriMatcher.addURI(AUTHORITY, UNIDADES_HOSPITALARES, URI_UNIDADES_HOSPITALARES)
            uriMatcher.addURI(AUTHORITY,"$UNIDADES_HOSPITALARES/#", URI_UNIDADES_HOSPITALARES_ESPECIFICO)
            uriMatcher.addURI(AUTHORITY, TESTES, URI_TESTES)
            uriMatcher.addURI(AUTHORITY, "$TESTES/#", URI_TESTES_ESPECIFICA)

            return uriMatcher
        }
    }
}