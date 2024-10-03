package com.example.bottomnavigation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val SELECTED_MENU_ITEM = "selected_menu_item"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Check if there's a saved state (e.g., after rotation) to restore the selected fragment
        if (savedInstanceState == null) {
            // Initial load: Display the ListFragment (the second menu item)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ListFragment())
                .commit()
            bottomNavigationView.selectedItemId = R.id.nav_list
        } else {
            // Restore selected item from saved instance state
            val selectedMenuItem = savedInstanceState.getInt(SELECTED_MENU_ITEM, R.id.nav_list)
            bottomNavigationView.selectedItemId = selectedMenuItem
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null

            when (item.itemId) {
                R.id.nav_calculator -> {
                    selectedFragment = CalculatorFragment()
                }

                R.id.nav_list -> {
                    selectedFragment = ListFragment() // Assuming you have this fragment
                }

                R.id.nav_profile -> {
                    selectedFragment = ProfileFragment() // Assuming you have this fragment
                }
            }


            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, selectedFragment)
                    .commit()
            }

            true
        }
    }
}