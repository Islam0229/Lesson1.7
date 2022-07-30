package com.example.lesson41.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lesson41.models.TaskModel

@Database(entities = [TaskModel::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun dao(): Dao
}