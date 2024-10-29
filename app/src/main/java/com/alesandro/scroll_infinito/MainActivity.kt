package com.alesandro.scroll_infinito

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alesandro.scroll_infinito.TaskApplication.Companion.prefs
import com.alesandro.scroll_infinito.databinding.ActivityMainBinding


/**
 * Clase principal de la actividad
 *
 * @author Alesandro Quirós Gobbato
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var adapter: TaskAdapter

    lateinit var mp: MediaPlayer

    var tasks = mutableListOf<String>()

    /**
     * Función que se ejecuta al crear la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
    }

    /**
     * Función que instancia los eventos de la interfaz gráfica de la actividad
     */
    private fun initListeners() {
        binding.btnAddTask.setOnClickListener {
            addTask()
        }
    }

    /**
     * Función que agrega una tarea a la lista
     */
    private fun addTask() {
        val taskToAdd:String = binding.etTask.text.toString()
        if (!taskToAdd.isEmpty()) { // Solo añadir si existe texto
            tasks.add(taskToAdd)
            mp.start() // Reproduce el sonido
            adapter.notifyDataSetChanged() // Notifica al adaptador que se ha agregado un elemento
            binding.etTask.setText("") // Limpia el campo de texto
            prefs.saveTasks(tasks)
        }
    }

    /**
     * Función que instancia el RecyclerView
     */
    private fun initRecyclerView() {
        tasks = prefs.getTasks()
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(tasks) {deleteTask(it)} // it -> posición
        binding.rvTasks.adapter = adapter
    }

    /**
     * Función que elimina una tarea de la lista
     */
    private fun deleteTask(position:Int) {
        tasks.removeAt(position)
        adapter.notifyDataSetChanged() // Notifica al adaptador que se ha agregado un elemento
        prefs.saveTasks(tasks)
    }

}