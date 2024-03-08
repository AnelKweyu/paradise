package co.ke.kweyu.paradise.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.adapters.HistoryAdapter
import co.ke.kweyu.paradise.adapters.MainViewPagerAdapter
import co.ke.kweyu.paradise.databinding.ActivityPlayGroundBinding
import co.ke.kweyu.paradise.databinding.FragmentPlaygroundBinding
import co.ke.kweyu.paradise.fragments.AccountFragment
import co.ke.kweyu.paradise.fragments.HomeFragment
import co.ke.kweyu.paradise.fragments.SearchFragment
import co.ke.kweyu.paradise.models.History
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.ArrayDeque
import java.util.Deque

class PlayGroundActivity : BaseActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var assetsBottomSheet:BottomSheetDialog
    private lateinit var mainViewpager: ViewPager2
    private var deque: Deque<Int> = ArrayDeque(4)
    private var flag = true
    private val homeFragment = HomeFragment()
    private val accountFragment = AccountFragment()
    private val searchFragment = SearchFragment()
    private lateinit var binding: ActivityPlayGroundBinding
    private lateinit var mainViewPagerAdapter: MainViewPagerAdapter
    private lateinit var fragmentList: ArrayList<Fragment>


    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var historyArrayList: ArrayList<History>

    private lateinit var retailPrice: Array<String>
    private lateinit var ownership: Array<String>
    private lateinit var date: Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayGroundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigation = binding.bottomNavigation
        mainViewpager = binding.mainActivityViewPager

        setupBottomNavigation(bottomNavigation)

        // Initialize your fragmentList with the desired fragments
        fragmentList = ArrayList()
        fragmentList.add(homeFragment)
        fragmentList.add(searchFragment)
        fragmentList.add(accountFragment)


        // Initialize the ViewPager2 and the adapter
        mainViewPagerAdapter = MainViewPagerAdapter(this, fragmentList)
        deque.push(R.id.bottomNavHome)
        // Set the adapter to the ViewPager2
        mainViewpager.adapter = mainViewPagerAdapter
        // link Viewpager to bottom nav
        setupViewPagerBottomNavigation()

        onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun setupBottomNavigation(bottomNavigation: BottomNavigationView) {
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
    private fun loadFragment(itemId:Int) {
        when (itemId) {
            R.id.bottomNavHome -> mainViewpager.currentItem = 0
            R.id.bottomNavSearch -> mainViewpager.currentItem = 1
            R.id.bottomNavProfile -> mainViewpager.currentItem = 2
            R.id.bottomNavTransactions->{
                showAssetsBottomSheet()
            }
        }
    }

    private lateinit var transactionsBottomSheetbinding: FragmentPlaygroundBinding
    private lateinit var bottomSheetLayout:ConstraintLayout
    private lateinit var closeBottomSheet:ImageView
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

    private val callback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            deque.pop()
            if (!deque.isEmpty()){
                deque.peek()?.let {
                    loadFragment(it)
                }
            }else{
                finishAffinity()
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

}




