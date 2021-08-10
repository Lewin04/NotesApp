package com.example.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.remainder.roomdb.NotesApp
import com.example.remainder.roomdb.PinnedNotes


@Dao
interface TaskDao {

    @Query("SELECT * FROM NotesApp")
    fun getAll(): List<NotesApp>

    @Insert
    fun insert(task: NotesApp)

    @Insert
    fun PinnedNotes(task: NotesApp)



    @Query("DELETE FROM NotesApp WHERE id = :userId")
    fun deleteByUserId(userId: Int)

    @Query("DELETE FROM NotesApp")
    fun deleteAll()

    @Query("SELECT * FROM NotesApp WHERE hint LIKE :date")
    fun findUserWithName(date: String):List<NotesApp>

    @Query("UPDATE NotesApp SET  title = :name ,descs =:pass WHERE id =:id")
    fun update(name:String,pass:String ,id: Int)

    @Query("SELECT hint FROM NotesApp")
    fun getAllHint(): List<String?>?

}