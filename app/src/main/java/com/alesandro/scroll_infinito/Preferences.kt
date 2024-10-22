package com.alesandro.scroll_infinito

import android.content.Context
import android.content.SharedPreferences

/**
 * Clase con preferencias para almacenar datos de la aplicación
 */
class Preferences(context: Context) {
    companion object {
        const val PREFS_NAME = "myDatabase"
        const val TASKS = "tasks_value"
    }

    val prefs:SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    fun saveTasks(tasks:List<String>) {
        prefs.edit().putStringSet(TASKS, tasks.toSet())
    }

}