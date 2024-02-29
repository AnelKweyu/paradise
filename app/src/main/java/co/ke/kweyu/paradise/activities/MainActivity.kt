package co.ke.kweyu.paradise.activities


import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.adapters.BestPlanAdapter
import co.ke.kweyu.paradise.adapters.InvestmentGuideAdapter
import co.ke.kweyu.paradise.fragments.HomeFragment
import androidx.fragment.app.Fragment
import co.ke.kweyu.paradise.fragments.AccountFragment
import co.ke.kweyu.paradise.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity() {

    private lateinit var bottomNavigation : BottomNavigationView
    val homeFragment = HomeFragment()
    val accountFragment = AccountFragment()
    val searchFragment = SearchFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottomNavigation)

//        setupActionBar()
        
        replaceFragment(homeFragment)

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottomNavHome -> {
                    replaceFragment(homeFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.bottomNavSearch -> {
                    replaceFragment(searchFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.bottomNavTransactions -> {
                    replaceFragment(homeFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.bottomNavProfile -> {
                    replaceFragment(accountFragment)
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

    private fun setupActionBar() {
        val toolbarMainActivity: Toolbar = findViewById(R.id.toolbarMainActivity)

        setSupportActionBar(toolbarMainActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_menu)
            actionBar.setTitle("")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_notification, menu)
        return super.onCreateOptionsMenu(menu)
    }
}