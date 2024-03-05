package co.ke.kweyu.paradise.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainViewPagerAdapter(fragmentActivity: FragmentActivity, private var arr: ArrayList<Fragment>) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun createFragment(position: Int): Fragment {
        return arr[position]
    }
}
