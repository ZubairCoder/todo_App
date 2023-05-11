package com.example.viewmodel.ui.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todo_app.db.Todo

@Dao
interface DataDao {
    @Insert
    suspend fun  dataInsert(todo: Todo)

    @Query("SELECT * FROM todo_table")
    fun getAllData() : LiveData<List<Todo>>

    @Query("SELECT * FROM todo_table where id like :id limit 1")
    suspend fun getOneData(id : Int) : Todo

    @Update
    fun updateData(todo: Todo)

    @Delete
    fun deleteData(todo: Todo)
}