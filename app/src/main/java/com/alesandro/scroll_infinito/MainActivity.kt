package com.alesandro.scroll_infinito

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Clase principal de la actividad
 *
 * @author Alesandro Quirós Gobbato
 */
class MainActivity : AppCompatActivity() {
    lateinit var etTask: EditText
    lateinit var btnAddTask: Button
    lateinit var rvTasks: RecyclerView

    lateinit var adapter: TaskAdapter

    private lateinit var db : TaskDatabaseHelper

    lateinit var mp: MediaPlayer

    lateinit var tasks: List<Task>

    /**
     * Función que se ejecuta al crear la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = TaskDatabaseHelper(this)
        initUi()
    }

    /**
     * Función que inicia la interfaz gráfica de la actividad
     */
    private fun initUi() {
        initView()
        initListeners()
        initRecyclerView()
    }

    /**
     * Función que instancia la ventana de inicio
     */
    private fun initView() {
        mp = MediaPlayer.create(this, R.raw.ding) // Carga el sonido
        etTask = findViewById(R.id.etTask)
        btnAddTask = findViewById(R.id.btnAddTask)
        rvTasks = findViewById(R.id.rvTasks)
    }

    /**
     * Función que instancia los eventos de la interfaz gráfica de la actividad
     */
    private fun initListeners() {
        btnAddTask.setOnClickListener {
            addTask()
        }
    }

    /**
     * Función que agrega una tarea a la lista
     */
    private fun addTask() {
        val taskToAdd:String = etTask.text.toString()
        if (!taskToAdd.isEmpty()) { // Solo añadir si existe texto
            val task = Task(0,taskToAdd)
            mp.start() // Reproduce el sonido
            adapter.notifyDataSetChanged() // Notifica al adaptador que se ha agregado un elemento
            Toast.makeText(this, "Tarea añadida correctamente", Toast.LENGTH_LONG) // Mensaje de confirmación
                .show()
            etTask.setText("") // Limpia el campo de texto
            db.insertTask(task)
            adapter.refreshList(db.getAllTasks())
        } else {
            Toast.makeText(this, "No se puede añadir una tarea vacía", Toast.LENGTH_LONG) // Mensaje de error
                .show()
        }
    }

    /**
     * Función que instancia el RecyclerView
     */
    private fun initRecyclerView() {
        tasks = db.getAllTasks()
        rvTasks.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(db.getAllTasks(), this) // it -> posición
        rvTasks.adapter = adapter
        // Configuración de Swipe to Delete para eliminar tareas al deslizar
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = tasks[position]
                adapter.deleteTask(task) // Llama al método para eliminar la tarea en la posición desliz
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvTasks) // Asigna el helper al RecyclerView
    }

}