package com.example.todo_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.todo_app.activity.Note_list
import com.example.todo_app.adapter.Shared_Preferences
import com.github.paolorotolo.appintro.AppIntro

class MainActivity : AppIntro() {

    override fun onCreate(savedInstanceState: Bundle?) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
