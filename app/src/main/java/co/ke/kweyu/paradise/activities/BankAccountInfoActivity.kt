package co.ke.kweyu.paradise.activities

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import co.ke.kweyu.paradise.databinding.ActivityBankAccountInfoBinding
import androidx.appcompat.widget.Toolbar
import co.ke.kweyu.paradise.R

class BankAccountInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBankAccountInfoBinding
    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankAccountInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
    }

    private fun setupActionBar() {
        toolbar = binding.toolbarBankAccountInfoActivity.toolbarLayout
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = "Bank account info"
        }

        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }
}
