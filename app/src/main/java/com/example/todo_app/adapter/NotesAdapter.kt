package com.example.remainder.roomdb

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_app.R
import com.example.todo_app.activity.UpdateActivity
import kotlinx.android.synthetic.main.remimder_list.view.*
import java.util.*
import kotlin.collections.ArrayList


class NotesAdapter(private val mCtx: Context, private var notelist: ArrayList<NotesApp>,var date:String,var callListerner:CallListerner) :


    RecyclerView.Adapter<NotesAdapter.TasksViewHolder>() {
    var item:Int?=null
    var list=ArrayList<String>()
    var list1=ArrayList<String>()
    val t:NotesApp?=null
    var callListerners:CallListerner?=null

    companion object {
        var same:String?=null


    }
    init {
        this.list1=list
        this.callListerners=callListerner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view = LayoutInflater.from(mCtx).inflate(R.layout.remimder_list, parent, false)
        return TasksViewHolder(view)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val t = notelist[position]


        holder.itemView.notes_title.text = t.title
        list.add(t.title!!)
//        Log.e("list", t.date)

        var event=t.desc
        holder.itemView.notes_description.text = t.desc


        Log.e("position",position.toString())


      /*  if(date.equals(t.date)){
            callListerners!!.getData(t.desc.toString())


        }*/

        holder.itemView.cardview.setOnClickListener {

            var intent=Intent(mCtx, UpdateActivity::class.java)
            intent.putExtra("values",  t.id)
            intent.putExtra("Title",t.title)
            intent.putExtra("des",t.desc)
            intent.putExtra("notesimg",t.image)
            intent.putExtra("AddNotes",0)
            Log.e("success3", t.id.toString())
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(intent)
        }

        holder.itemView.cardview.setOnLongClickListener {
            holder.itemView.cardview.setBackgroundDrawable(mCtx.getResources().getDrawable(R.drawable.card_selected_bg))
            callListerners!!.delete(t.id,notelist,position)
            callListerners!!.pinnotes(t.id, t.title.toString(), t!!.desc.toString(),position)

            true
        }

        /*val rnd = Random()
        val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        holder.itemView.con.setBackgroundColor(color)*/

    }
    override fun getItemCount(): Int {
        return notelist.size
    }

    interface CallListerner{

        fun getData(des:String)
        fun delete(item:Int,notelist: ArrayList<NotesApp>, position: Int)
        fun pinnotes(item:Int,Title : String,Description : String, position: Int)

    }

    inner  class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        override fun onClick(v: View?) {

        }

    }



}