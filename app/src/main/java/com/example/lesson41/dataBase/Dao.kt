package com.example.lesson41.dataBase

import androidx.room.*
import androidx.room.Dao
import com.example.lesson41.models.TaskModel


@Dao
interface Dao {

    @Query("SELECT * FROM taskmodel ORDER BY time DESC")
    fun getAll(): List<TaskModel>?

    @Insert
    fun insert(task: TaskModel)

    @Update
    fun update(task: TaskModel)

    @Delete
    fun delete(task: TaskModel)

    @Query("SELECT * FROM taskModel WHERE task=:title")
    fun getTask(title: String): TaskModel?
}