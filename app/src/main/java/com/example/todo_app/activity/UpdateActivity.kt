package com.example.todo_app.activity

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity

import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat

import com.example.remainder.roomdb.NotesApp
import com.example.roomdatabase.DatabaseClient
import com.example.todo_app.MainActivity
import com.example.todo_app.R
import kotlinx.android.synthetic.main.activity_update1.*

import kotlinx.android.synthetic.main.addnotes.*


import kotlinx.android.synthetic.main.remimder_list.*
import java.io.ByteArrayOutputStream
import java.util.*

class UpdateActivity : AppCompatActivity() {
    var notes: NotesApp?=null
    var type:Int?=null
    var notestitle:String?=null
    var notesDescription:String?=null
    var notesimage:String?=null
    var savedhint:String?=null
    var notelist= ArrayList<String>()
    var AddNotes : Int ?= null
    var update : ImageView ?= null
    var Menuicon : ImageView ?= null
    var settingIcon : ImageView ?= null
    var imagedisplay : ImageView ?= null
    var SaveImage : LinearLayout ?= null
    var Share : LinearLayout ?= null
    var NotesTitle : TextView ?= null
    var NotesDescription : TextView ?= null
    var headertitle : TextView ?= null
    val Permission_request_code = 200 as Int
    var byt:ByteArray?=null
    var imageEncoded : String ?= null


    var adapter: ArrayAdapter<*>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update1)

        update = findViewById(R.id.notes_save_icon)
        NotesTitle = findViewById(R.id.edit_Notes_title)
        NotesDescription = findViewById(R.id.edit_Description_title)
        Menuicon = findViewById(R.id.notes_Menu_icon)
        SaveImage = findViewById(R.id.add_image_layout)
        imagedisplay = findViewById(R.id.edit_notes_img)
        Share = findViewById(R.id.share_layout)
        headertitle = findViewById(R.id.header_notes_title)
        settingIcon = findViewById(R.id.notes_setting_icon)

        type=intent.getIntExtra("values",0)
        AddNotes = intent.getIntExtra("AddNotes",0)
        notestitle = intent.getStringExtra("Title")
        notesDescription = intent.getStringExtra("des")
        notesimage = intent.getStringExtra("notesimg")



        if(notestitle != null){
            NotesTitle!!.setText(notestitle)
            headertitle!!.setText(notestitle)
        }else{
            NotesTitle!!.setText("")
            headertitle!!.setText(NotesTitle?.text.toString())
        }

        if(notesDescription != null){
            NotesDescription!!.setText(notesDescription)
        }else{
            NotesDescription!!.setText("")
        }

        settingIcon!!.setOnClickListener {
            val settingintent = Intent(this@UpdateActivity,settings_activity::class.java)
            startActivity(settingintent)
        }

        Menuicon!!.setOnClickListener {
            menu_hide_layout.visibility = View.VISIBLE
            edit_Notes_title.setClickable(false);
            edit_Notes_title.setFocusable(false);
            edit_Notes_title.setFocusableInTouchMode(false);
            edit_Description_title.setClickable(false);
            edit_Description_title.setFocusable(false);
            edit_Description_title.setFocusableInTouchMode(false);
                    }

        Share!!.setOnClickListener {
            sharemail()
        }
        add_image_layout.setOnClickListener {
            menu_hide_layout.visibility = View.GONE
            edit_Notes_title.setClickable(true);
            edit_Notes_title.setFocusable(true);
            edit_Notes_title.setFocusableInTouchMode(true);
            edit_Description_title.setClickable(true);
            edit_Description_title.setFocusable(true);
            edit_Description_title.setFocusableInTouchMode(true);
            openCamera()

        }

        update!!.setOnClickListener {
            updatetask()
        }
    }

    private fun updatetask() {
        if(AddNotes == 1){
            var notestitle: String = NotesTitle?.text.toString()
            var notesDesctiption: String = NotesDescription?.text.toString()

            if (notestitle.isEmpty()) {
                NotesTitle?.setError("Title Required")
                NotesTitle?.requestFocus()
                return
            }
            if (notesDesctiption.isEmpty()) {
                NotesDescription?.setError("Description Required")
                NotesDescription?.requestFocus()
                return
            }


            class save : AsyncTask<Void, Void, Void>() {
                override fun doInBackground(vararg params: Void?): Void? {
                    var task= NotesApp()
                    task.title=notestitle
                    task.desc= notesDesctiption
                    task.hint=""
                    task.image = "";
                    task.PinedNotes = "0";



                    DatabaseClient.getInstance(this@UpdateActivity).database.taskDao().insert(task)
                    Log.d("Success" , "successListener")
                    var successint = Intent(this@UpdateActivity,Note_list::class.java)
                    startActivity(successint)

                    return null

                }

                override fun onPostExecute(result: Void?) {
                    super.onPostExecute(result)
//                startActivity(Intent(applicationContext, Main2Activity::class.java))
//                Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show()
                }
            }

            val st = save()
            st.execute()

        }else {
            var Title: String = NotesTitle?.text.toString()
            var description: String = NotesDescription?.text.toString()

            if (Title.isEmpty()) {
                NotesTitle?.setError("Title Required")
                NotesTitle?.requestFocus()
                return
            }
            if (description.isEmpty()) {
                NotesDescription?.setError("Description Required")
                NotesDescription?.requestFocus()
                return
            }

            class update : AsyncTask<Void, Void, Void>() {
                override fun doInBackground(vararg params: Void?): Void? {

                    //                notes!!.date=task1
                    //                notes!!.desc=desc1


                    //                DatabaseClient.getInstance(this@UpdateActivity).database.taskDao().update(task1,desc1,type!!.toInt())


                    DatabaseClient.getInstance(this@UpdateActivity).database.taskDao()
                        .update(Title, description, type!!)

                    Log.e("success1", "Success")
                    return null

                }

                override fun onPostExecute(result: Void?) {
                    super.onPostExecute(result)
                    startActivity(Intent(applicationContext, Note_list::class.java))
                    Toast.makeText(applicationContext, "Updated", Toast.LENGTH_LONG).show()
                }
            }

            val st = update()
            st.execute()
        }
    }

    private fun openCamera() {

//        var file =  File(applicationContext.getExternalFilesDir(System.currentTimeMillis()+".jpg").toString())
//        var fileUri = Uri.fromFile(file);
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA),
            Permission_request_code
        )

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            Permission_request_code -> if (grantResults.size > 0) {

                Log.e("clickedtimes", requestCode.toString())


                val cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
                // add more permission whether its needed.


                if (cameraAccepted) {

                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (intent.resolveActivity(packageManager) != null)
                        startActivityForResult(intent, Permission_request_code)

                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Permission_request_code -> {

                    val extras = data?.getExtras()
                    val imageBitmap = extras?.get("data") as Bitmap
                    imagedisplay?.setImageBitmap(imageBitmap)
                    decodeImage(imageBitmap)

                }
            }
        }
    }

    fun decodeImage(bitmap: Bitmap){
        var bitmapimage= ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bitmapimage)
        byt=bitmapimage.toByteArray()
        imageEncoded = Base64.encodeToString(byt, Base64.DEFAULT)
        Log.e("imagestring",imageEncoded)
//        preference!!.putImage(imageEncoded)
    }

    private fun sharemail(){
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "abc@mail.com", null))
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "address")
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body")
        startActivity(Intent.createChooser(emailIntent, "Send Email..."))
    }
}
