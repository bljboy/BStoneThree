package com.boy_stone.bstonethree

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.boy_stone.bstonethree.bottom_nav_fragment.FragmentAdapter
import com.boy_stone.bstonethree.bottom_nav_fragment.Fragment_fun
import com.boy_stone.bstonethree.bottom_nav_fragment.Fragment_messages
import com.boy_stone.bstonethree.bottom_nav_fragment.Fragment_sweet
import com.boy_stone.bstonethree.nav_view_fragment.NavVIew_Theme
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.switch_item.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private var list: ArrayList<Fragment> = ArrayList()
    private var fragmentAdapter: FragmentAdapter? = null
    private var themeType = 0

    @SuppressLint("WrongConstant", "ApplySharedPref")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        themeType =
            getSharedPreferences("theme", MODE_PRIVATE).getInt("themeType", 0)
        if (themeType == 0) {
            setTheme(R.style.AppTheme);
        } else if (themeType == 1) {
            setTheme(R.style.AppTheme_Red_1);
        } else if (themeType == 2) {
            setTheme(R.style.AppTheme_blue_2);
        } else if (themeType == 3) {
            setTheme(R.style.AppTheme_yellow_3);
        } else if (themeType == 4) {
            setTheme(R.style.AppTheme_green_4);
        } else if (themeType == 5) {
            setTheme(R.style.AppTheme_grey_5);
        } else if (themeType == 6) {
            setTheme(R.style.AppTheme_purple_6);
        } else if (themeType == 7) {
            setTheme(R.style.AppTheme_dark_7);
        }
        setContentView(R.layout.activity_main)


        navigationview.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_view_theme -> {
                    val intent = Intent()
                    intent.setClass(applicationContext, NavVIew_Theme().javaClass)
                    startActivity(intent)
                    true
                }
                R.id.nav_view_switch_bar -> {
                    toast("sssssssssssssssssssss")
                    if (themeType == 0 || themeType == 2 || themeType == 1 || themeType == 4 || themeType == 5 || themeType == 3 || themeType == 6) {
                        themeType = 7

                    } else {
                        themeType = 0
                    }
                    getSharedPreferences("theme", MODE_PRIVATE).edit()
                        .putInt("themeType", themeType)
                        .commit()
                    recreate();
                    true
                }

                else -> {
                    true
                }
            }
        }
        list.add(Fragment_messages())
        list.add(Fragment_sweet())
        list.add(Fragment_fun())
        fragmentAdapter = FragmentAdapter(getSupportFragmentManager(), list)
        viewpager.setAdapter(fragmentAdapter)
        viewpager.setCurrentItem(0)
        FAB.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "ss", Toast.LENGTH_SHORT).show()
        })

        viewpager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {
            }

            override fun onPageScrollStateChanged(arg0: Int) {
            }
        })


        home_bar.setNavigationOnClickListener(View.OnClickListener {
            dd.openDrawer(Gravity.START)
        })

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.page1 -> {
                    viewpager.setCurrentItem(0)
                    // Respond to navigation item 1 click
                    true
                }
                R.id.page2 -> {
                    viewpager.setCurrentItem(1)
                    // Respond to navigation item 2 click
                    true
                }
                else -> {
                    viewpager.setCurrentItem(2)
                    true
                }
            }
        }
    }



    override fun onRestart() {
        super.onRestart()
        recreate()
    }
}