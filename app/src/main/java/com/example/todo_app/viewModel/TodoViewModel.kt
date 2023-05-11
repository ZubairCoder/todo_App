package com.example.todo_app.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.todo_app.db.Todo
import com.example.todo_app.repositories.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TodoViewModel (application: Application, private val todoRepository: TodoRepository) : AndroidViewModel(application) {
    val users: LiveData<List<Todo>> = todoRepository.getAllData()

    fun insertData(title :String, description :String,alarmTime : String) {
        GlobalScope.launch {
            val data = Todo(null,title  ,description,alarmTime)
            todoRepository.insertData(data)
        }
    }
    fun updateData(todo: Todo){
        GlobalScope.launch(Dispatchers.IO) {
            val data = Todo(todo.Id,todo.Title,todo.Detail,todo.Time)
            todoRepository.updateData(data)
        }

    }
    fun deleteData(todo: Todo){
        GlobalScope.launch {
            todoRepository.delete(todo)
        }
    }


}