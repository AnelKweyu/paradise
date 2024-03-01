package co.ke.kweyu.paradise.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import co.ke.kweyu.paradise.R

class BankAccountInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_account_info)
        setupActionBar()
    }

    private fun setupActionBar() {
        val toolbarBankAccountInfoActivity: Toolbar = findViewById(R.id.toolbarBankAccountInfoActivity)

        setSupportActionBar(toolbarBankAccountInfoActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.setTitle("Bank account info")
        }

        toolbarBankAccountInfoActivity.setNavigationOnClickListener { onBackPressed() }
    }
}