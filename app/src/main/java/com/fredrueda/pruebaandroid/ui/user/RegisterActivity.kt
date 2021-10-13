package com.fredrueda.pruebaandroid.ui.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.fredrueda.pruebaandroid.R
import com.fredrueda.pruebaandroid.ui.adapters.PageAdapter
import com.fredrueda.pruebaandroid.utils.SharedPref
import com.google.android.material.tabs.TabLayout
import kotlin.random.Random


class RegisterActivity : AppCompatActivity() {

    private lateinit var mViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val randomValues = Random.nextInt(0, 1000)
        SharedPref.saveData(this, "id", "" + randomValues)

        mViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        mViewModel.init()

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = PageAdapter(supportFragmentManager)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
        viewPager.offscreenPageLimit = 4

        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                mViewModel.sendData(position)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // Code goes here
            }

            override fun onPageScrollStateChanged(state: Int) {
                // Code goes here
            }
        })
    }
}