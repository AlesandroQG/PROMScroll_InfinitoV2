package com.alesandro.scroll_infinito

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Clase ViewHolder para el RecyclerView de tareas
 */
class TaskViewHolder(view:View):RecyclerView.ViewHolder(view) {
    val tvTask = view.findViewById<TextView>(R.id.tvTask)
    val ivTaskDone = view.findViewById<ImageView>(R.id.ivTaskDone)
}