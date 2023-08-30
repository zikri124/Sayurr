package com.example.sayurr

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragment: Fragment
    private lateinit var fragmentManager: FragmentManager
    private var userData : Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setVar()
        setNav()
    }

    private fun setVar() {
        fragmentManager = supportFragmentManager
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
    }

    private fun setNav() {
        userData = intent.getBundleExtra("data")
        if (userData == null) {
            toLoginPage()
        }

        fragment = HomeFragment()
        val bundle1 = Bundle()
        bundle1.putString("uid", userData?.getString("uid").toString())
        bundle1.putString("name", userData?.getString("name").toString())
        fragment.arguments = bundle1
        setCurrentFragment(fragment)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val bundle = Bundle()
            bundle.putString("uid", userData?.getString("uid"))
            when(item.itemId) {
                R.id.page_1 -> {
                    fragment = HomeFragment()
                    bundle.putString("name", userData?.getString("name"))
                }
                R.id.page_2 -> {
                    fragment = CartFragment()
                }
                R.id.page_3 -> {
                    fragment = OrderFragment()
                }
                R.id.page_4 -> {
                    fragment = MenuFragment()
                    bundle.putString("name", userData?.getString("name"))
                    bundle.putString("email", userData?.getString("email"))
                    bundle.putString("phone", userData?.getString("phone"))
                }
            }
            fragment.arguments = bundle
            setCurrentFragment(fragment)
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        fragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }

    private fun toLoginPage() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}