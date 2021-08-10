package com.example.todo_app.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remainder.roomdb.NotesAdapter
import com.example.remainder.roomdb.NotesApp
import com.example.roomdatabase.DatabaseClient
import com.example.todo_app.R
import com.example.todo_app.adapter.Shared_Preferences
import kotlinx.android.synthetic.main.activity_note_list.*
import kotlinx.android.synthetic.main.remimder_list.*
import java.io.ByteArrayInputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Note_list : AppCompatActivity() , NotesAdapter.CallListerner {

    var linearLayoutManager: GridLayoutManager? = null

    var currentDate: String? = null

    var adapter: NotesAdapter? = null

    var listnew: ArrayList<NotesApp>? = null

    var preference: Shared_Preferences? = null

    var pinnedlayout : LinearLayout ?= null
    var recyclerview: RecyclerView? = null
    var PinnedRecycleView : RecyclerView? = null

    var addnotes: ImageView? = null
    var delnotes: ImageView? = null
    var pinnotes: ImageView? = null

//    var Pinn : String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        preference = Shared_Preferences(this)

        recyclerview = findViewById(R.id.Noteslist_recycleView)
        pinnedlayout = findViewById(R.id.pinned_notes_layout)
        PinnedRecycleView = findViewById(R.id.Pinned_recycleView)
        addnotes = findViewById(R.id.notes_add_icon)
        delnotes = findViewById(R.id.notes_delete_icon)
        pinnotes = findViewById(R.id.notes_Pin_icon)

        addnotes!!.setOnClickListener {
            var intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("AddNotes", 1)
            startActivity(intent)
        }


        /*search.setOnQueryTextListener(this@Note_list)

        profile.setOnClickListener{
*/
//            openCamera()
//        }


        val sdf = SimpleDateFormat("dd/M/yyyy")
        currentDate = sdf.format(Date())
        Log.e(" C DATE is  ", currentDate)

        getNotes(currentDate!!)

    }

    override fun onResume() {
        super.onResume()

        var getImg = preference!!.getImage()
        var b = Base64.decode(getImg, Base64.DEFAULT)
        var inputstream = ByteArrayInputStream(b)
        var image = BitmapFactory.decodeStream(inputstream)
//        profile.setImageBitmap(image)
//        getNotes(currentDate!!)
        Log.e("resume", "resume")
    }

    private fun getNotes(date: String) {
        class GetTasks : AsyncTask<Void, Void, List<NotesApp>>() {

            override fun doInBackground(vararg voids: Void): List<NotesApp> {
                return DatabaseClient
                    .getInstance(applicationContext)
                    .database
                    .taskDao()
                    .getAll()

            }

            override fun onPostExecute(notes: List<NotesApp>) {
                super.onPostExecute(notes)
                listnew = java.util.ArrayList<NotesApp>(notes)


/*
                for(pinnlist in listnew!!){
                    Pinn  = pinnlist.PinedNotes

                    Log.d("PinnList","" + pinnlist.PinedNotes)

                }*/

  /*              if(Pinn == "1"){
                    pinnedlayout!!.visibility =View.VISIBLE
                    linearLayoutManager = GridLayoutManager(applicationContext, 2)
                    PinnedRecycleView!!.layoutManager = linearLayoutManager
                    adapter =
                        NotesAdapter(applicationContext, listnew!!, date, this@Note_list)
                    PinnedRecycleView!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()
                }else{
  */                  linearLayoutManager = GridLayoutManager(applicationContext, 2)
                    recyclerview!!.layoutManager = linearLayoutManager
                    adapter = NotesAdapter(applicationContext, listnew!!, date, this@Note_list)
                    recyclerview!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()
//                }

                adapter!!.notifyItemInserted(listnew!!.size)

//                    call()

            }
        }

        val gt = GetTasks()
        gt.execute()
    }

    override fun getData(des: String) {
        TODO("Not yet implemented")
    }

    override fun delete(item: Int, notelist: ArrayList<NotesApp>, position: Int) {
        class delete : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {

                DatabaseClient.getInstance(this@Note_list).database.taskDao().deleteByUserId(item)
                return null

            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                delnotes!!.visibility = View.VISIBLE
                delnotes!!.setOnClickListener {
                    notelist.removeAt(position)
                    adapter!!.notifyItemRemoved(position)
                    adapter!!.notifyItemRangeChanged(position, notelist.size)
                    adapter!!.notifyItemChanged(position)
                }
            }
        }

        val st = delete()
        st.execute()
    }

    override fun pinnotes(item: Int, Title: String, Description: String, position: Int) {
        pinnotes!!.visibility = View.VISIBLE

        class save : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                var task = NotesApp()
                task.title = Title
                task.desc = Description
                task.hint = ""
                task.image = "";
                task.PinedNotes = "1";

                DatabaseClient.getInstance(this@Note_list).database.taskDao().PinnedNotes(task)
                Log.d("Success", "successListener")


                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                pinnotes!!.setOnClickListener {
                    Log.d("Success", Title + "" + Description)
                    Toast.makeText(applicationContext,"Your Notes Pinned SuccessFUly" ,Toast.LENGTH_SHORT).show()
                }
            }
        }

        val st = save()
        st.execute()

    }


}
