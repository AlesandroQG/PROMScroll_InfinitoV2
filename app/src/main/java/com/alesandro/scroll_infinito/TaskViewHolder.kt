package com.alesandro.scroll_infinito

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Clase ViewHolder para el RecyclerView de tareas
 */
class TaskViewHolder(view:View):RecyclerView.ViewHolder(view) {
    private val tvTask = view.findViewById<TextView>(R.id.tvTask)

    /**
     * Función que define el texto de la tarea de la lista
     */
    fun render(task:String) {
        tvTask.text = task
    }

}