package com.boy_stone.bstonethree

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.green
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.boy_stone.bstonethree.fragment.FragmentAdapter
import com.boy_stone.bstonethree.fragment.Fragment_fun
import com.boy_stone.bstonethree.fragment.Fragment_messages
import com.boy_stone.bstonethree.fragment.Fragment_sweet
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home__bottom__fragment.*
import kotlinx.android.synthetic.main.fragment_home__bottom__fragment.view.*
import org.jetbrains.anko.support.v4.toast


class Home_Bottom_Activity : Fragment() {

    private var list: ArrayList<Fragment> = ArrayList()
    private var fragmentAdapter: FragmentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home__bottom__fragment, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet", "WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        list.add(Fragment_messages())
        list.add(Fragment_sweet())
        list.add(Fragment_fun())
        fragmentAdapter = FragmentAdapter(fragmentManager, list)
        viewpager.setAdapter(fragmentAdapter)
        viewpager.setCurrentItem(0)

        viewpager.setOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {
            }

            override fun onPageScrollStateChanged(arg0: Int) {
            }
        })


        home_bar.setNavigationOnClickListener(View.OnClickListener {
            view!!.dd.openDrawer(Gravity.START)
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


}