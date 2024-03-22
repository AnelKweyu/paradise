package co.ke.kweyu.paradise.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.adapters.HistoryAdapter
import co.ke.kweyu.paradise.adapters.MainViewPagerAdapter
import co.ke.kweyu.paradise.databinding.ActivityMainBinding

import co.ke.kweyu.paradise.databinding.FragmentPlaygroundBinding
import co.ke.kweyu.paradise.firebase.FirestoreClass
import co.ke.kweyu.paradise.fragments.AccountFragment
import co.ke.kweyu.paradise.fragments.HomeFragment
import co.ke.kweyu.paradise.fragments.SearchFragment
import co.ke.kweyu.paradise.models.History
import co.ke.kweyu.paradise.models.User
import co.ke.kweyu.paradise.utils.Constants
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import java.util.ArrayDeque
import java.util.Deque

class MainActivity : BaseActivity(),  NavigationView.OnNavigationItemSelectedListener {
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var assetsBottomSheet: BottomSheetDialog
    private lateinit var mainViewpager: ViewPager2
    private lateinit var toolbar: Toolbar

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerLayoutNav: NavigationView
    private lateinit var navigationMenu: Menu


    private var deque: Deque<Int> = ArrayDeque(4)
    private var flag = true
    private val homeFragment = HomeFragment()
    private val accountFragment = AccountFragment()
    private val searchFragment = SearchFragment()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewPagerAdapter: MainViewPagerAdapter
    private lateinit var fragmentList: ArrayList<Fragment>


    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var historyArrayList: ArrayList<History>

    private lateinit var retailPrice: Array<String>
    private lateinit var ownership: Array<String>
    private lateinit var date: Array<String>

    private lateinit var transactionsBottomSheetbinding: FragmentPlaygroundBinding
    private lateinit var bottomSheetLayout: ConstraintLayout
    private lateinit var closeBottomSheet: ImageView

    // A global variable for User Name
    private lateinit var paradiseUserName: String
    // A global variable for SharedPreferences
    private lateinit var paradiseSharedPreferences: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = binding.toolbarMainActivity.toolbarLayout
        drawerLayout = binding.drawerLayout
        drawerLayoutNav = binding.navView
        bottomNavigation = binding.bottomNavigation
        mainViewpager = binding.mainActivityViewPager

        setupBottomNavigation()
        setupNavigationDrawer()

        // Initialize your fragmentList with the desired fragments
        fragmentList = ArrayList()
        fragmentList.add(homeFragment)
        fragmentList.add(searchFragment)
        fragmentList.add(accountFragment)

        // Assign the NavigationView.OnNavigationItemSelectedListener to navigation view.
        drawerLayoutNav.setNavigationItemSelectedListener(this)


        // Initialize the ViewPager2 and the adapter
        mainViewPagerAdapter = MainViewPagerAdapter(this, fragmentList)
        deque.push(R.id.bottomNavHome)
        // Set the adapter to the ViewPager2
        mainViewpager.adapter = mainViewPagerAdapter
        // link Viewpager to bottom nav
        setupViewPagerBottomNavigation()
        setupViewPagerToolBar()
        drawerLayout.bringToFront()

        FirestoreClass().loadUserData(this)

        onBackPressedDispatcher.addCallback(this, callback)
    }
    private fun setupBottomNavigation() {
        bottomNavigation.run {
            selectedItemId = R.id.bottomNavHome
            setOnItemSelectedListener { item ->
                val id = item.itemId
                if (deque.contains(id)) {
                    if (id == R.id.bottomNavHome) {
                        if (deque.size != 1) {
                            if (flag) {
                                deque.addFirst(R.id.bottomNavHome)
                                flag = false
                            }
                        }
                    }
                    deque.remove(id)
                }
                deque.push(id)
                loadFragment(item.itemId)
                true
            }
        }
    }

    private fun setupNavigationDrawer() {
        navigationMenu = drawerLayoutNav.menu


        // Set the initially selected item
        navigationMenu.findItem(R.id.drawer_home).isChecked = true

        drawerLayoutNav.setNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId

            if (deque.contains(id)) {
                if (id == R.id.drawer_home) {
                    if (deque.size != 1) {
                        if (flag) {
                            deque.addFirst(R.id.drawer_home)
                            flag = false
                        }
                    }
                }
                deque.remove(id)
            }

            deque.push(id)

            // Load fragment based on selected item
            loadFragment(id)

            // Highlight the selected item
            menuItem.isChecked = true

            true
        }
    }

    private fun loadFragment(itemId:Int) {
        when (itemId) {
            R.id.bottomNavHome -> mainViewpager.currentItem = 0
            R.id.bottomNavSearch -> mainViewpager.currentItem = 1
            R.id.bottomNavProfile -> mainViewpager.currentItem = 2
            R.id.bottomNavTransactions->{
                showAssetsBottomSheet()
            }
            R.id.drawer_home -> mainViewpager.currentItem = 0
            R.id.drawer_search -> mainViewpager.currentItem = 1
            R.id.drawer_profile -> mainViewpager.currentItem = 2
        }
    }

    private fun showAssetsBottomSheet() {
        // Using View Binding to inflate the layout
        transactionsBottomSheetbinding = FragmentPlaygroundBinding.inflate(layoutInflater)
        bottomSheetLayout = transactionsBottomSheetbinding.root
        assetsBottomSheet = BottomSheetDialog(this, R.style.BottomSheetTheme)

        closeBottomSheet = transactionsBottomSheetbinding.closeBottomSheet
        closeBottomSheet.setOnClickListener{
            assetsBottomSheet.dismiss()
        }

        assetsBottomSheet.setContentView(bottomSheetLayout)
        historyDataInitialize()
        // Now you can directly access views from the binding object
        historyRecyclerView = transactionsBottomSheetbinding.rvHistory
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.setHasFixedSize(true)

        // Assuming you have a function to update the adapter data
        updateHistoryAdapter()

        assetsBottomSheet.show()
    }

    // Sample function to update history adapter data
    private fun updateHistoryAdapter() {
        historyAdapter = HistoryAdapter(historyArrayList)
        historyRecyclerView.adapter = historyAdapter
    }

    private fun setupViewPagerBottomNavigation() {
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
    }
    private fun setupViewPagerToolBar() {
        mainViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        setSupportActionBar(toolbar)
                        supportActionBar?.setDisplayHomeAsUpEnabled(true)
                        supportActionBar?.title = ""

                        toggle = ActionBarDrawerToggle(this@MainActivity,drawerLayout,R.string.open,R.string.close)
                        drawerLayout.addDrawerListener(toggle)
                        toggle.syncState()

                        toolbar.setNavigationOnClickListener {
                            toggleDrawer()
                        }
                        navigationMenu.findItem(R.id.drawer_home).isChecked = true
                    }
                    1 -> {
                        setSupportActionBar(toolbar)
                        supportActionBar?.setDisplayHomeAsUpEnabled(true)
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
                        supportActionBar?.title = "Search (coming soon)"

                        toolbar.setNavigationOnClickListener {
                            onBackPressedDispatcher.onBackPressed()
                        }
                        navigationMenu.findItem(R.id.drawer_search).isChecked = true


                    }
                    2 -> {
                            setSupportActionBar(toolbar)
                            supportActionBar?.setDisplayHomeAsUpEnabled(true)
                            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
                            supportActionBar?.title = ""

                            toolbar.setNavigationOnClickListener {
                                onBackPressedDispatcher.onBackPressed()
                            }
                        navigationMenu.findItem(R.id.drawer_profile).isChecked = true

                    }

                }
                super.onPageSelected(position)
            }
        })
    }

    private fun toggleDrawer() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        when (menuItem.itemId) {

            R.id.drawer_home -> {
                mainViewpager.currentItem = 0
            }
            R.id.drawer_search -> {
                mainViewpager.currentItem = 1
            }
            R.id.drawer_profile -> {
                mainViewpager.currentItem = 2
            }
            R.id.drawer_logout -> {
                // Here sign outs the user from firebase in this device.
                FirebaseAuth.getInstance().signOut()
                // Send the user to the intro screen of the application.
                val intent = Intent(this, IntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            R.id.drawer_notification -> {
                startActivity(Intent(this, NotificationActivity::class.java))
            }
            R.id.drawer_bank_account -> {
                startActivity(Intent(this, BankAccountInfoActivity::class.java))

            }
            R.id.drawer_share -> {
                Toast.makeText(
                    this,
                    "Share feature coming soon.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            R.id.drawer_rate -> {
                Toast.makeText(
                    this,
                    "Rate Us Now!",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    private val callback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                // A double back press function is added in Base Activity.
                deque.pop()
                if (!deque.isEmpty()){
                    deque.peek()?.let {
                        loadFragment(it)
                    }
                }else{
                    doubleBackToExit()
                }
            }

        }
    }

    private fun historyDataInitialize() {
        historyArrayList = arrayListOf()
        retailPrice = arrayOf(
            getString(R.string.rp_200_000),
            getString(R.string.rp_150_000),
            getString(R.string.rp_1_000_240),
            getString(R.string.rp_1_000_240),
            getString(R.string.rp_200_000),
            getString(R.string.rp_150_000),
            getString(R.string.rp_1_000_240),
            getString(R.string.rp_1_000_240),
            getString(R.string.rp_200_000),
            getString(R.string.rp_150_000),
            getString(R.string.rp_1_000_240),
            getString(R.string.rp_1_000_240),
            getString(R.string.rp_200_000),
            getString(R.string.rp_150_000),
            getString(R.string.rp_1_000_240),
            getString(R.string.rp_1_000_240),
        )
        ownership = arrayOf(
            getString(R.string.buy_kocela_stock),
            getString(R.string.sell_kplc_stock),
            getString(R.string.buy_tende_stock),
            getString(R.string.sell_bat_stock),
            getString(R.string.buy_kocela_stock),
            getString(R.string.sell_kplc_stock),
            getString(R.string.buy_tende_stock),
            getString(R.string.sell_bat_stock),
            getString(R.string.buy_kocela_stock),
            getString(R.string.sell_kplc_stock),
            getString(R.string.buy_tende_stock),
            getString(R.string.sell_bat_stock),
            getString(R.string.buy_kocela_stock),
            getString(R.string.sell_kplc_stock),
            getString(R.string.buy_tende_stock),
            getString(R.string.sell_bat_stock)
        )

        date = arrayOf(
            getString(R.string.wed_6_mar_2024),
            getString(R.string.wed_6_mar_2024),
            getString(R.string.wed_6_mar_2024),
            getString(R.string.wed_6_mar_2024),
            getString(R.string.wed_6_mar_2024),
            getString(R.string.wed_6_mar_2024),
            getString(R.string.wed_6_mar_2024),
            getString(R.string.wed_6_mar_2024),
            getString(R.string.wed_6_mar_2024),
            getString(R.string.wed_6_mar_2024),
            getString(R.string.wed_6_mar_2024),
            getString(R.string.wed_6_mar_2024),
            getString(R.string.wed_6_mar_2024),
            getString(R.string.wed_6_mar_2024),
            getString(R.string.wed_6_mar_2024),
            getString(R.string.wed_6_mar_2024)
        )

        for (i in retailPrice.indices) {
            val historyItem = History(
                retailPrice[i],
                ownership[i],
                date[i]
            )
            historyArrayList.add(historyItem)
        }
    }

    fun updateNavigationUserDetails(user: User) {
        val headerView = binding.navView.getHeaderView(0)
        val navUserImage = headerView.findViewById<ImageView>(R.id.iv_user_image)
        Glide.with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(navUserImage)
        val navUsername = headerView.findViewById<TextView>(R.id.tv_username)
        navUsername.text = user.name

        // Pass the user's name to the HomeFragment
        val homeFragment = fragmentList[0] as HomeFragment
        homeFragment.updateLoggedInUserName(user)
    }

    /**
     * A function to notify the token is updated successfully in the database.
     */
    fun tokenUpdateSuccess() {

        hideProgressDialog()

        // Here we have added a another value in shared preference that the token is updated in the database successfully.
        // So we don't need to update it every time.
        val editor: SharedPreferences.Editor = paradiseSharedPreferences.edit()
        editor.putBoolean(Constants.FCM_TOKEN_UPDATED, true)
        editor.apply()

        // Get the current logged in user details.
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().loadUserData(this)
    }

    /**
     * A companion object to declare the constants.
     */
    companion object {
        //A unique code for starting the activity for result
        const val MY_PROFILE_REQUEST_CODE: Int = 11

        const val CREATE_BOARD_REQUEST_CODE: Int = 12
    }

}




