package com.alesandro.scroll_infinito

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Clase que conecta el TaskViewHolder con MainActivity
 */
class TaskAdapter(private val tasks:List<String>):RecyclerView.Adapter<TaskViewHolder>() {
    /**
     * Función que crea un ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(layoutInflater.inflate(R.layout.item_task, parent, false))
    }

    /**
     * Dice al RecyclerView cuantos items tiene que mostrar
     */
    override fun getItemCount() = tasks.size

    /**
     * Carga el item de la posición pasada
     */
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.render(tasks[position])
    }

}