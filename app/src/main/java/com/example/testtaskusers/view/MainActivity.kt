package com.example.testtaskusers.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testtaskusers.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportFragmentManager.backStackEntryCount == 0) {
            val usersListFragment = UsersListFragment.newInstance()
            supportFragmentManager.beginTransaction().replace(R.id.frame, usersListFragment)
                .commit()
        }
    }
}