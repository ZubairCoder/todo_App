package com.example.todo_app.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_app.AlarmManager
import com.example.todo_app.R
import com.example.todo_app.adopter.DataAdopter
import com.example.todo_app.databinding.ActivityMainBinding
import com.example.todo_app.db.Todo
import com.example.todo_app.factroy.TodoFactory
import com.example.todo_app.interfaces.onItemClick
import com.example.todo_app.repositories.TodoRepository
import com.example.todo_app.viewModel.TodoViewModel
import com.example.viewmodel.ui.db.AppDatabase
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(),onItemClick {
    private var binding : ActivityMainBinding? = null
    private var recyclerView : RecyclerView? = null
    private var todoViewModel : TodoViewModel? = null
    private lateinit var timePicker: TimePicker
    private var alarmTime : String? = null
    private lateinit var calendar : Calendar
    private var alarmManager : AlarmManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


         calendar = Calendar.getInstance()
        alarmManager = AlarmManager(this)
        alarmManager?.createNotificationChannel()

        recyclerView = binding?.recyclerView
        recyclerView?.layoutManager = LinearLayoutManager(this)
        val db = AppDatabase.getDatabase(applicationContext)
        val todoRepository = TodoRepository(db)

        // viewModel= ViewModelProvider(this).get(MainviewModel::class.java)
        todoViewModel= ViewModelProvider(this,TodoFactory(todoRepository)).get(TodoViewModel::class.java)
        todoViewModel?.users?.observe(this, Observer {
            recyclerView?.adapter = DataAdopter(todoViewModel!!,this,it,this)
        })

        binding?.btnAdd?.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val view = LayoutInflater.from(this).inflate(R.layout.insert_dailogue,null)
            builder.setView(view)
            val alert = builder.create()
            alert.show()
            val title = view.findViewById<EditText>(R.id.TxtTitle)
            val img = view.findViewById<EditText>(R.id.TxtDescription)
            val btnInsert = view.findViewById<Button>(R.id.btnAdd)
            val txtTime = view.findViewById<TextView>(R.id.txtTime)
            btnInsert.setOnClickListener {
                todoViewModel?.insertData(title.text.toString(),img.text.toString(), alarmTime!!)
                alert.dismiss()
            }
            val btnSetAlarm = view.findViewById<ImageView>(R.id.btnAlarm)
            btnSetAlarm.setOnClickListener {
                val builder = AlertDialog.Builder(this)
                val view = LayoutInflater.from(this).inflate(R.layout.alarm_layout,null)
                builder.setView(view)
                val alert = builder.create()
                alert.show()
                val btnAddAlarm = view.findViewById<Button>(R.id.btnAddAlarm)
                timePicker = view.findViewById<TimePicker>(R.id.alarmTimePicker)
                btnAddAlarm.setOnClickListener {
                   timePicker.let {
                        calendar.set(
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH),
                            it.hour,
                            it.minute,
                            0
                        )
                       val timeInMiliSeconds = calendar.timeInMillis
                       alarmTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.time)
                       txtTime.setText(alarmTime)
                       alarmManager?.setAlarm(timeInMiliSeconds)
                       //remainingTime(timeInMiliSeconds)
                       alert.dismiss()
                    }
                }

            }
        }
        setContentView(binding?.root)


        alarmManager?.createNotificationChannel()
    }





    @SuppressLint("MissingInflatedId")
    override fun onClick(todo: Todo) {
        val builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.update_dailogue,null)
        builder.setView(view)
        val alert = builder.create()
        alert.show()
        val updatetitle = view.findViewById<EditText>(R.id.UpdateTitle)
        val updateDescription = view.findViewById<EditText>(R.id.UpdateDescription)
        val updateTime = view.findViewById<TextView>(R.id.updateTime)
        val btnUpdate = view.findViewById<Button>(R.id.btnUpdate)
        updatetitle.setText(todo.Title)
        updateDescription.setText(todo.Detail)
        updateTime.setText(todo.Time)
        btnUpdate.setOnClickListener {
            val data = Todo(todo.Id,updatetitle.text.toString(),updateDescription.text.toString(),alarmTime)
            todoViewModel?.updateData(data)
            alert.dismiss()
        }
        val btnSetAlarm = view.findViewById<ImageView>(R.id.btnAlarm)
        btnSetAlarm.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val view = LayoutInflater.from(this).inflate(R.layout.alarm_layout,null)
            builder.setView(view)
            val alert = builder.create()
            alert.show()
            val btnAddAlarm = view.findViewById<Button>(R.id.btnAddAlarm)
            timePicker = view.findViewById<TimePicker>(R.id.alarmTimePicker)
            btnAddAlarm.setOnClickListener {
                timePicker.let {
                    calendar.set(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        it.hour,
                        it.minute,
                        0
                    )
                    val timeInMiliSeconds = calendar.timeInMillis
                    alarmTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.time)
                    updateTime.setText(alarmTime)
                    alarmManager?.setAlarm(timeInMiliSeconds)
                    //remainingTime(timeInMiliSeconds)
                    alert.dismiss()
                }
            }

        }
    }

    override fun onDeleteUserClickListener(todo: Todo) {
        todoViewModel?.deleteData(todo)
    }
}