package com.alesandro.scroll_infinito

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Clase con funciones para interactuar con la base de datos de SQLite
 */
class TaskDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "tasks.db";
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "tasks"
        // Columnas de la tabla
        private const val COLUMN_ID = "id"
        private const val COLUMN_TASK = "task"
    }

    /**
     * Función que crea la tabla
     */
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY, " +
                    "$COLUMN_TASK TEXT)"
        db?.execSQL(createTableQuery)
    }

    /**
     * Función que modífica la base de datos
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery =
            "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    /**
     * Pasado un objeto task, la añade a la base de datos
     * @param task la task a añadir
     */
    fun insertTask(task: Task) {
        //la abro en modo escritura
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TASK, task.task)
            //en este caso ID es autonumérico
        }
        //insertamos datos
        db.insert(TABLE_NAME, null, values)
        //y ahora a cerrar
        db.close()
    }

    /**
     * Lee la base de datos y rellena una List de tipo Task
     */
    fun getAllTasks(): List<Task> {
        //creo una lista mutable para poder cambiar cosas
        val listaTasks = mutableListOf<Task>()
        //la abro en modo lectura
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        //lanza un cursor
        val cursor = db.rawQuery(query, null)
        //itera mientras que exista otro
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val task_value = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK))
            //creamos un objeto temporal de tipo Task
            val task = Task(id, task_value)
            //añadimos la task
            listaTasks.add(task)
        }
        //cerrar las conexiones
        cursor.close()
        db.close()
        return listaTasks
    }

    /**
     * Recibe un integer que es su posición en la lista, con esto recuperara los datos de la task
     * @return Una task en función del integer que es su posicion
     * @param idTask el número de la posición de la task
     */
    fun getIdTask(idTask: Int): Task {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID=$idTask"
        //lanza un cursor
        val cursor = db.rawQuery(query, null)
        //ve al primer registro que cumpla esa condicion (esperemos que el único)
        cursor.moveToFirst()
        //leo los datos de la consulta
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val task_value = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK))
        //cierro conexiones y devuelvo la task
        cursor.close()
        db.close()
        return Task(id, task_value)
    }

    /**
     * Elimina un tarea por su id
     */
    fun deleteTask(idTask: Int) {
        val db = writableDatabase
        val whereClauses = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(idTask.toString())
        //eliminar el objeto
        db.delete(TABLE_NAME, whereClauses, whereArgs)
        db.close()
    }

}