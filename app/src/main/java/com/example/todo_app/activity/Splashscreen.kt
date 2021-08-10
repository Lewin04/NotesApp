package com.example.todo_app.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.example.todo_app.MainActivity
import com.example.todo_app.R
import kotlinx.android.synthetic.main.activity_splashscreen.*

class Splashscreen : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_splashscreen)

        splashimage.animate()
            .scaleX(0.5f)
            .scaleY(0.5f)
            .rotation(360f)
            .duration = 2500

        Handler().postDelayed(
            {
                val i = Intent(this, Note_list::class.java)
                startActivity(i)
                finish()
            }, 3000)

    }
}
