package com.boy_stone.bstonethree.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.boy_stone.bstonethree.R
import kotlinx.android.synthetic.main.recyclerview_messages.view.*

class Adapter(private val mydata: ArrayList<item>) :
    RecyclerView.Adapter<Adapter.MyHolder>() {
    private var mOnItemClickListener: AdapterView.OnItemClickListener? = null


    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.MyHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_messages, parent, false)
        val holder = MyHolder(itemView)
        return holder

    }

    override fun getItemCount(): Int {

        return mydata.size

    }

    override fun onBindViewHolder(holder: Adapter.MyHolder, position: Int) {
//        holder.itemView.car_big_title.text = mydata[position].big_title
//        holder.itemView.car_time.text = mydata[position].big_title
    }
}