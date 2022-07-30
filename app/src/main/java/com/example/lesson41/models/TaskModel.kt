package com.example.lesson41.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity
data class TaskModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val task: String,
    var time: Long
) : Serializable