package com.example.todo_app.factroy

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo_app.repositories.TodoRepository
import com.example.todo_app.viewModel.TodoViewModel

class TodoFactory (private val todoRepository: TodoRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodoViewModel(application = Application(), todoRepository) as T
    }
}
