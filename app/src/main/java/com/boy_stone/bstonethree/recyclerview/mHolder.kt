package com.boy_stone.bstonethree.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class mHolder(view:View):RecyclerView.ViewHolder(view) {

    fun title(id:Int,title:String):mHolder{
        val sweet_recyclerview_title = this.itemView.findViewById<TextView>(id)
        sweet_recyclerview_title.text = title
        return this
    }
    fun time(id: Int,time:String){
        val sweet_recyclerview_time = this.itemView.findViewById<TextView>(id)
        sweet_recyclerview_time.text = time

    }
    fun src(id: Int,src: String){
        val sweet_recyclerview_src = this.itemView.findViewById<TextView>(id)
        sweet_recyclerview_src.text = src
    }

    fun setvisible(id: Int){
        this.itemView.findViewById<View>(id).visibility = View.VISIBLE
    }
    fun setGone(id: Int){
        this.itemView.findViewById<View>(id).visibility = View.GONE
    }
}