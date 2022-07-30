package com.example.lesson41

import android.app.Application
import androidx.room.Room
import com.example.lesson41.dataBase.DataBase

class App : Application() {

    companion object {
        lateinit var dataBase: DataBase
    }

    override fun onCreate() {
        super.onCreate()
        dataBase =
            Room.databaseBuilder(this, DataBase::class.java, "db").allowMainThreadQueries().build()
    }
}