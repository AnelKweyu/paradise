package co.ke.kweyu.paradise.activities


import android.os.Bundle
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.fragments.HomeFragment
import androidx.fragment.app.Fragment
import co.ke.kweyu.paradise.fragments.AccountFragment
import co.ke.kweyu.paradise.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity() {

    private lateinit var bottomNavigation : BottomNavigationView
    private lateinit var currentFragment: Fragment

    private val homeFragment = HomeFragment()
    private val accountFragment = AccountFragment()
    private val searchFragment = SearchFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottomNavigation)

        replaceFragment(homeFragment)
        currentFragment = homeFragment



        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottomNavHome -> {
                    replaceFragment(homeFragment)
                    currentFragment = homeFragment
                    return@setOnItemSelectedListener true
                }
                R.id.bottomNavSearch -> {
                    replaceFragment(searchFragment)
                    currentFragment = searchFragment
                    return@setOnItemSelectedListener true
                }
                R.id.bottomNavTransactions -> {
                    replaceFragment(homeFragment)
                    currentFragment = homeFragment
                    return@setOnItemSelectedListener true
                }
                R.id.bottomNavProfile -> {
                    replaceFragment(accountFragment)
                    currentFragment = accountFragment
                    return@setOnItemSelectedListener true
                }

            }
            false
        }
    }

    private fun replaceFragment(homeFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainActivityFragmentContainerView,homeFragment)
        fragmentTransaction.commit()
    }


}


