package co.ke.kweyu.paradise.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.ke.kweyu.paradise.databinding.ActivityEditProfileBinding
import androidx.appcompat.widget.Toolbar
import co.ke.kweyu.paradise.R

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
    }

    private fun setupActionBar() {
        toolbar = binding.toolbarEditProfile.toolbarLayout
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = "Contact info"
        }

        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }
}
