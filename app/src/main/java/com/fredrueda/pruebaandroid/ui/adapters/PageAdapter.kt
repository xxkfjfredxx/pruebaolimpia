package com.fredrueda.pruebaandroid.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.fredrueda.pruebaandroid.ui.user.FinishFragment
import com.fredrueda.pruebaandroid.ui.user.FormFragment
import com.fredrueda.pruebaandroid.ui.user.MapsFragment
import com.fredrueda.pruebaandroid.ui.user.TakePhotosFragment

class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return FormFragment()
            }
            1 -> {
                return TakePhotosFragment()
            }
            2 -> {
                return MapsFragment()
            }
            3 -> {
                return FinishFragment()
            }
            else -> {
                return FormFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Data"
            }
            1 -> {
                return "fotos"
            }
            2 -> {
                return "map"
            }
            3 -> {
                return "finish"
            }
        }
        return super.getPageTitle(position)
    }
}