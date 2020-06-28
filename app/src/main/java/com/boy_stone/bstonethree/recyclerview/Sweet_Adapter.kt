package com.example.sqlitecase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.boy_stone.bstonethree.R
import com.boy_stone.bstonethree.bottom_nav_fragment.Fragment_sweet
import com.boy_stone.bstonethree.recyclerview.News
import com.boy_stone.bstonethree.recyclerview.item
import com.boy_stone.bstonethree.recyclerview.mHolder
import kotlinx.android.synthetic.main.recyclerview_sweet_item_.view.*

class Sweet_Adapter<T:Any>(var list:ArrayList<T>,var itemlayout:Int,var bindholder:BindholderInterface)
    : RecyclerView.Adapter<mHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):mHolder {

        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_sweet_item_, parent, false)
        val holder = mHolder(itemView)
        return holder

    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: mHolder, position: Int) {
        bindholder.bindholder(holder,position)
    }


    interface BindholderInterface{
        fun bindholder(holder:mHolder,position: Int)
    }

}
