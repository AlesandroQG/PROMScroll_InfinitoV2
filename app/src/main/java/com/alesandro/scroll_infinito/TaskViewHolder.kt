package com.alesandro.scroll_infinito

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Clase ViewHolder para el RecyclerView de tareas
 */
class TaskViewHolder(view:View):RecyclerView.ViewHolder(view) {
    private val tvTask = view.findViewById<TextView>(R.id.tvTask)
    private val ivTaskDone = view.findViewById<TextView>(R.id.ivTaskDone)

    /**
     * Función que define el texto de la tarea de la lista
     */
    fun render(task:String, onItemDone:(Int) -> Unit) {
        tvTask.text = task
        ivTaskDone.setOnClickListener {onItemDone(adapterPosition())}
    }

}