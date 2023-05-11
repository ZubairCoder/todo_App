package com.example.todo_app.interfaces

import com.example.todo_app.db.Todo


interface onItemClick {
    fun onClick( todo: Todo)
    fun onDeleteUserClickListener(todo: Todo)
}