package com.example.remainder.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class NotesApp{


    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "title")
    var title: String? = null

    @ColumnInfo(name = "descs")
    var desc: String? = null

    @ColumnInfo(name="hint")
    var hint: String? = null

    @ColumnInfo(name="Image")
    var image: String? = null

    @ColumnInfo(name="Pinned")
    var PinedNotes: String? = null

}