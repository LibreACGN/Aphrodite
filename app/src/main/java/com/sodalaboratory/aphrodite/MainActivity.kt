package com.sodalaboratory.aphrodite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.sodalaboratory.aphrodite.ui.PersonListFragment


class MainActivity : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager2

    companion object {
        private const val PAGE_NUMBERS = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set adapter for ViewPager
        mViewPager = findViewById<ViewPager2>(R.id.viewpager)
        val viewPagerAdapter = ScreenSlidePagerAdapter(this)
        mViewPager.adapter = viewPagerAdapter
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = PAGE_NUMBERS

        override fun createFragment(position: Int): Fragment =
            when (position) {
                // PersonListFragment
                0 -> PersonListFragment()
                else -> Fragment()
            }
    }
}