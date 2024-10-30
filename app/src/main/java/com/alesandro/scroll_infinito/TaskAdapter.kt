package com.alesandro.scroll_infinito

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

/**
 * Clase que conecta el TaskViewHolder con MainActivity
 */
class TaskAdapter(private var tasks:List<Task>, private val context: Context):RecyclerView.Adapter<TaskViewHolder>() {
    private val db: TaskDatabaseHelper = TaskDatabaseHelper(context)

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
        val task = tasks[position];
        holder.tvTask.text = task.task
        holder.ivTaskDone.setOnClickListener {
            deleteTask(task)
        }
    }

    /**
     * Recarga la lista
     */
    fun refreshList(newTask : List<Task>) {
        tasks = newTask
        notifyDataSetChanged()
    }

    /**
     * Función que elimina una tarea de la lista
     */
    fun deleteTask(task:Task) {
        db.deleteTask(task.id)
        //refrescamos con las nuevas notas al reeleer la base de datos
        refreshList(db.getAllTasks())
        db.close()
    }

}