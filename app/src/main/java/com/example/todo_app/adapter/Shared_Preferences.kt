package com.example.todo_app.adapter

import android.content.Context
import android.content.SharedPreferences

class Shared_Preferences (private val context: Context) {

    private val INTRO = "intro"
    private val IMAGE = "image"

    private val app_prefs: SharedPreferences


    init {
        app_prefs = context.getSharedPreferences(
            "shared",
            Context.MODE_PRIVATE
        )
    }

    fun putIntro(loginorout: String) {
        val edit = app_prefs.edit()
        edit.putString(INTRO, loginorout)
        edit.commit()
    }

    fun getIntro(): String? {
        return app_prefs.getString(INTRO, "")
    }
    fun putImage(image: String) {
        val edit = app_prefs.edit()
        edit.putString(IMAGE, image)
        edit.commit()
    }
    fun getImage(): String? {
        return app_prefs.getString(IMAGE, "")
    }


}