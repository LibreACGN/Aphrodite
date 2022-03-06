package com.sodalaboratory.aphrodite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sodalaboratory.aphrodite.ui.PersonListFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.person_list_fragment_container)
        if (currentFragment == null) {
            val fragment = PersonListFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.person_list_fragment_container, fragment)
                .commit()
        }
    }
}