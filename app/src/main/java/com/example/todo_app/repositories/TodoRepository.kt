package com.example.todo_app.repositories

import androidx.lifecycle.LiveData
import com.example.todo_app.db.Todo
import com.example.viewmodel.ui.db.AppDatabase

class TodoRepository(private val appDatabase: AppDatabase) {
    fun getAllData(): LiveData<List<Todo>> {
        return  appDatabase.dataDao().getAllData()
    }

    suspend fun insertData(todo: Todo){
        appDatabase.dataDao().dataInsert(todo)
    }
    suspend fun updateData(todo: Todo){
        appDatabase.dataDao().updateData(todo)
    }
    suspend fun delete(todo: Todo){
        appDatabase.dataDao().deleteData(todo)
    }
}