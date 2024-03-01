package co.ke.kweyu.paradise.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import co.ke.kweyu.paradise.R

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        setupActionBar()

    }

    private fun setupActionBar() {
        val toolbarNotificationActivity: Toolbar = findViewById(R.id.toolbarNotificationActivity)

        setSupportActionBar(toolbarNotificationActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.setTitle("")
        }

        toolbarNotificationActivity.setNavigationOnClickListener { onBackPressed() }
    }
}