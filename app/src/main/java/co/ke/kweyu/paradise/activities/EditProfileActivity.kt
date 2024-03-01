package co.ke.kweyu.paradise.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import co.ke.kweyu.paradise.R

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        setupActionBar()
    }

    private fun setupActionBar() {
        val toolbarEditProfile: Toolbar = findViewById(R.id.toolbarEditProfile)

        setSupportActionBar(toolbarEditProfile)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.setTitle("Contact info")
        }

        toolbarEditProfile.setNavigationOnClickListener { onBackPressed() }
    }
}