package com.boy_stone.bstonethree.bottom_nav_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.boy_stone.bstonethree.R
import com.boy_stone.bstonethree.recyclerview.Adapter
import com.boy_stone.bstonethree.recyclerview.item
import kotlinx.android.synthetic.main.fragment_messages.*

class Fragment_messages : Fragment() {

    val listData = ArrayList<item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        for (i in 0..5){
            item().big_title = "我爱你"
            listData.add(item())
        }



        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.adapter = Adapter(listData)
        recyclerview.setHasFixedSize(true)
    }
}