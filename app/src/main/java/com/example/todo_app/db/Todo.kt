package com.example.todo_app.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class Todo(@PrimaryKey(autoGenerate = true)var Id : Int?,
                     var Title : String,
                     var Detail : String?,
                     val Time : String?
)