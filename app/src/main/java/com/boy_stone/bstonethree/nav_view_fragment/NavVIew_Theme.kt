package com.boy_stone.bstonethree.nav_view_fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.boy_stone.bstonethree.R
import kotlinx.android.synthetic.main.activity_nav_v_iew__theme.*


class NavVIew_Theme : AppCompatActivity() {

    private var themeType = 0

    @SuppressLint("ApplySharedPref")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        themeType = getSharedPreferences("theme", MODE_PRIVATE).getInt("themeType", 0);
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
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_v_iew__theme)



        //返回
        theme_view_title.setOnClickListener(View.OnClickListener {
            finish()
        })

        th_red_1.setOnClickListener(View.OnClickListener {
            if (themeType == 0 || themeType == 2 || themeType == 3 || themeType == 4 || themeType == 5 || themeType == 6 || themeType == 7) {
                themeType = 1
            }
            getSharedPreferences("theme", MODE_PRIVATE).edit().putInt("themeType", themeType)
                .commit()
            recreate();
            overridePendingTransition(0, 0);
        })
        th_blue_2.setOnClickListener(View.OnClickListener {
            if (themeType == 0 || themeType == 1 || themeType == 3 || themeType == 4 || themeType == 5 || themeType == 6 || themeType == 7) {
                themeType = 2
            }
            getSharedPreferences("theme", MODE_PRIVATE).edit().putInt("themeType", themeType)
                .commit()
            recreate();
        })
        th_yellow_3.setOnClickListener(View.OnClickListener {
            if (themeType == 0 || themeType == 2 || themeType == 1 || themeType == 4 || themeType == 5 || themeType == 6 || themeType == 7) {
                themeType = 3
            }
            getSharedPreferences("theme", MODE_PRIVATE).edit().putInt("themeType", themeType)
                .commit()
            recreate();
        })
        th_green_4.setOnClickListener(View.OnClickListener {
            if (themeType == 0 || themeType == 2 || themeType == 1 || themeType == 3 || themeType == 5 || themeType == 6 || themeType == 7) {
                themeType = 4
            }
            getSharedPreferences("theme", MODE_PRIVATE).edit().putInt("themeType", themeType)
                .commit()
            recreate();
        })
        th_grey_5.setOnClickListener(View.OnClickListener {
            if (themeType == 0 || themeType == 2 || themeType == 1 || themeType == 4 || themeType == 3 || themeType == 6 || themeType == 7) {
                themeType = 5
            }
            getSharedPreferences("theme", MODE_PRIVATE).edit().putInt("themeType", themeType)
                .commit()
            recreate();
        })
        th_purple_6.setOnClickListener(View.OnClickListener {
            if (themeType == 0 || themeType == 2 || themeType == 1 || themeType == 4 || themeType == 5 || themeType == 3 || themeType == 7) {
                themeType = 6
            }
            getSharedPreferences("theme", MODE_PRIVATE).edit().putInt("themeType", themeType)
                .commit()
            recreate();
        })


    }

}