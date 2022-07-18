package com.example.lesson41

import java.io.Serializable

data class TaskModel(
    val task: String,
    var time: Long
) : Serializable