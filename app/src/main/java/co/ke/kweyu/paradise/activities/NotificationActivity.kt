package co.ke.kweyu.paradise.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.adapters.NotificationAdapter
import co.ke.kweyu.paradise.databinding.ActivityNotificationBinding
import co.ke.kweyu.paradise.models.Notification

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private lateinit var toolbar: Toolbar

    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var notificationRecyclerView: RecyclerView
    private lateinit var notificationsArrayList: ArrayList<Notification>

    private lateinit var notificationDescriptions: Array<String>
    private lateinit var notificationImages: Array<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        notificationsDataInitialize()

        notificationRecyclerView = binding.rvNotification
        notificationRecyclerView.layoutManager = LinearLayoutManager(this)
        notificationRecyclerView.setHasFixedSize(true)
        notificationAdapter = NotificationAdapter(notificationsArrayList)
        notificationRecyclerView.adapter = notificationAdapter
    }

    private fun setupActionBar() {
        toolbar = binding.toolbarNotificationActivity.toolbarLayout
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = ""
        }

        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun notificationsDataInitialize() {
        notificationsArrayList = arrayListOf()
        notificationDescriptions = arrayOf(
            getString(R.string.apple_stock_just_increased_check_it_out_now),
            getString(R.string.apple_stock_just_increased_check_it_out_now),
            getString(R.string.apple_stock_just_increased_check_it_out_now),
            getString(R.string.apple_stock_just_increased_check_it_out_now),
            getString(R.string.apple_stock_just_increased_check_it_out_now),
            getString(R.string.apple_stock_just_increased_check_it_out_now),
            getString(R.string.apple_stock_just_increased_check_it_out_now),
            getString(R.string.apple_stock_just_increased_check_it_out_now),
            getString(R.string.apple_stock_just_increased_check_it_out_now),
            getString(R.string.apple_stock_just_increased_check_it_out_now),
            getString(R.string.apple_stock_just_increased_check_it_out_now),
            getString(R.string.apple_stock_just_increased_check_it_out_now),
            getString(R.string.apple_stock_just_increased_check_it_out_now),
            getString(R.string.apple_stock_just_increased_check_it_out_now),
            getString(R.string.apple_stock_just_increased_check_it_out_now),
        )

        notificationImages = arrayOf(
            R.drawable.tende_logo,
            R.drawable.kocela,
            R.drawable.tende_logo,
            R.drawable.kocela,
            R.drawable.tende_logo,
            R.drawable.kocela,
            R.drawable.tende_logo,
            R.drawable.kocela,
            R.drawable.tende_logo,
            R.drawable.kocela,
            R.drawable.tende_logo,
            R.drawable.kocela,
            R.drawable.tende_logo,
            R.drawable.kocela,
            R.drawable.tende_logo,
            R.drawable.kocela
        )

        for (i in notificationDescriptions.indices) {
            val notification = Notification(
                notificationDescriptions[i],
                notificationImages[i]
            )
            notificationsArrayList.add(notification)
        }
    }
}
