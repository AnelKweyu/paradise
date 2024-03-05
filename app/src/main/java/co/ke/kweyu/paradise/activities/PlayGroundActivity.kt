package co.ke.kweyu.paradise.activities

import android.os.Bundle

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.adapters.MainViewPagerAdapter
import co.ke.kweyu.paradise.databinding.ActivityPlayGroundBinding
import co.ke.kweyu.paradise.fragments.AccountFragment
import co.ke.kweyu.paradise.fragments.HomeFragment
import co.ke.kweyu.paradise.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class PlayGroundActivity : BaseActivity() {



    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var mainViewpager: ViewPager2



//    private lateinit var currentFragment: Fragment

    private val homeFragment = HomeFragment()
    private val accountFragment = AccountFragment()
    private val searchFragment = SearchFragment()

    private lateinit var binding: ActivityPlayGroundBinding
    private lateinit var mainViewPagerAdapter: MainViewPagerAdapter
    private lateinit var fragmentList: ArrayList<Fragment>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayGroundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigation = binding.bottomNavigation
        mainViewpager = binding.mainActivityViewPager

//        setupBottomNavigation(bottomNavigation)

//        onBackPressedDispatcher.addCallback(this, callback)

        // Initialize your fragmentList with the desired fragments
        fragmentList = ArrayList()
        fragmentList.add(homeFragment)
        fragmentList.add(searchFragment)
        fragmentList.add(accountFragment)


        // Add more fragments as needed

        // Initialize the ViewPager2 and the adapter
        mainViewPagerAdapter = MainViewPagerAdapter(this, fragmentList)
        // Set the adapter to the ViewPager2
        mainViewpager.adapter = mainViewPagerAdapter
        mainViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> bottomNavigation.selectedItemId = R.id.bottomNavHome
                    1 -> bottomNavigation.selectedItemId = R.id.bottomNavSearch
                    2 -> bottomNavigation.selectedItemId = R.id.bottomNavProfile
                }
                super.onPageSelected(position)
            }
        })

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottomNavHome -> mainViewpager.currentItem = 0
                R.id.bottomNavSearch -> mainViewpager.currentItem = 1
                R.id.bottomNavProfile -> mainViewpager.currentItem = 2
            }
            true
        }
    }


}




