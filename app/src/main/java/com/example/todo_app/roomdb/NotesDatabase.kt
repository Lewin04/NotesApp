package com.example.remainder.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomdatabase.TaskDao



@Database(entities = [NotesApp::class],exportSchema = true,version = 2)

abstract class NotesDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
