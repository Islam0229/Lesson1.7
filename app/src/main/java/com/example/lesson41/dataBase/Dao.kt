package com.example.lesson41.dataBase

import androidx.room.*
import androidx.room.Dao
import com.example.lesson41.models.TaskModel


@Dao
interface Dao {

    @Query("SELECT * FROM taskmodel")
    fun getAll(): List<TaskModel>?

    @Insert
    fun insert(task: TaskModel)

    @Update
    fun update(task: TaskModel)

    @Delete
    fun delete(task: TaskModel)
}