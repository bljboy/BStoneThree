package com.boy_stone.bstonethree

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login_.*
import kotlinx.android.synthetic.main.fragment_login_.view.*

class Login_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view!!.login_registerbutton.setOnClickListener(View.OnClickListener {
            val findNavController = Navigation.findNavController(it)
            findNavController.navigate(R.id.action_login_Fragment_to_register_Fragment)

        })
        login_loginbutton.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.setClass(requireContext(), MainActivity().javaClass)
            startActivity(intent)
        })
    }
}