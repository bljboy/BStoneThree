package com.boy_stone.bstonethree.nav_view_fragment

import android.annotation.SuppressLint
import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.boy_stone.bstonethree.Login_Activity
import com.boy_stone.bstonethree.MainActivity
import com.boy_stone.bstonethree.R
import kotlinx.android.synthetic.main.activity_nav_view__person.*


class NavView_Person() : AppCompatActivity() {
    var sharedPreferences: SharedPreferences? = null
    @SuppressLint("ApplySharedPref")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_view__person)
        person_exit.setOnClickListener(
            View.OnClickListener {
                sharedPreferences = this.getSharedPreferences(
                    "login_user",
                    AppCompatActivity.MODE_PRIVATE
                )
                val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                editor .clear()
                editor.remove("user")
                editor.commit()
                val aa =
                    getSharedPreferences("login_user", Context.MODE_PRIVATE).getString("user", "")
                Log.d("wwwwwwwwwwww",aa)
                finish()
            }
        )
        //顶部按钮返回上一级
        person_topbar.setOnClickListener(View.OnClickListener {
            finish()
//          overridePendingTransition(R.anim.slide_to_left, R.anim.slide_to_right)
            overridePendingTransition(0, 0)
        })
    }

    override fun finish() {
        super.finish()

    }
}