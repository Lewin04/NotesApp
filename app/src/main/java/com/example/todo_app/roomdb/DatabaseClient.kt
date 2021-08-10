package com.example.roomdatabase

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.remainder.roomdb.NotesDatabase

class DatabaseClient

private constructor(private val mCtx: Context) {


    val database: NotesDatabase

    val MIGRATION_1_1: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE notes ADD COLUMN hint" )
        }
    }
    init {

        database = Room.databaseBuilder(mCtx, NotesDatabase::class.java, "success").addMigrations(
            MIGRATION_1_1).build()
    }

    companion object {
        private var mInstance: DatabaseClient? = null

        @Synchronized
        fun getInstance(mCtx: Context): DatabaseClient {
            if (mInstance == null) {
                mInstance = DatabaseClient(mCtx)
            }
            return mInstance as DatabaseClient
        }
    }
}