package co.ke.kweyu.paradise.activities

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.databinding.ActivityPlayGroundBinding
import co.ke.kweyu.paradise.fragments.AccountFragment
import co.ke.kweyu.paradise.fragments.HomeFragment
import co.ke.kweyu.paradise.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Deque
import java.util.ArrayDeque


class PlayGroundActivity : BaseActivity() {

    private var deque: Deque<Int> = ArrayDeque<Int>(4)
    private var flag = true

    private lateinit var binding: ActivityPlayGroundBinding
    private lateinit var bottomNavigation: BottomNavigationView

    private lateinit var currentFragment: Fragment

    private val homeFragment = HomeFragment()
    private val accountFragment = AccountFragment()
    private val searchFragment = SearchFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayGroundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigation = binding.bottomNavigation

        deque.push(R.id.bottomNavHome)
        loadFragment(homeFragment)

        setupBottomNavigation(bottomNavigation)

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
                loadFragment(getFragment(item.itemId))
                true
            }
        }
    }
    private fun getFragment(itemId:Int): Fragment {
        when(itemId){
            R.id.bottomNavHome -> {
                bottomNavigation.menu.getItem(0).isChecked = true
                currentFragment = homeFragment
                return currentFragment
            }
            R.id.bottomNavSearch -> {
                bottomNavigation.menu.getItem(1).isChecked = true
                currentFragment = searchFragment
                return currentFragment
            }
            R.id.bottomNavTransactions -> {
                bottomNavigation.menu.getItem(2).isChecked = true
                currentFragment = homeFragment
                return currentFragment
            }
            R.id.bottomNavProfile -> {
                bottomNavigation.menu.getItem(3).isChecked = true
                currentFragment = accountFragment
                return currentFragment
            }
        }
        bottomNavigation.menu.getItem(0).isChecked = true
        currentFragment = homeFragment
        return currentFragment
    }

    private fun loadFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.playGroundActivityFragmentContainerView.id, fragment)
        fragmentTransaction.commit()
    }

    private val callback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            deque.pop()
            if (!deque.isEmpty()){
                deque.peek()?.let {
                    getFragment(it)
                }?.let {
                    loadFragment(it)
                }
            }else{
                finishAffinity()
            }
        }
    }
}