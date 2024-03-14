package co.ke.kweyu.paradise.activities

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import co.ke.kweyu.paradise.databinding.ActivityBankAccountInfoBinding
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.adapters.BankAccountAdapter
import co.ke.kweyu.paradise.adapters.NotificationAdapter
import co.ke.kweyu.paradise.models.BankAccount
import co.ke.kweyu.paradise.models.Notification

class BankAccountInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBankAccountInfoBinding
    private lateinit var toolbar: Toolbar

    private lateinit var bankAccountAdapter: BankAccountAdapter
    private lateinit var bankAccountRecyclerView: RecyclerView
    private lateinit var bankAccountsArrayList: ArrayList<BankAccount>

    private lateinit var bankDetails: Array<String>
    private lateinit var userNames: Array<String>
    private lateinit var bankImages: Array<Int>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBankAccountInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        bankAccountsDataInitialize()

        bankAccountRecyclerView = binding.rvBankAccountInfo
        bankAccountRecyclerView.layoutManager = LinearLayoutManager(this)
        bankAccountRecyclerView.setHasFixedSize(true)
        bankAccountAdapter = BankAccountAdapter(bankAccountsArrayList)
        bankAccountRecyclerView.adapter = bankAccountAdapter
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
    private fun bankAccountsDataInitialize() {
        bankAccountsArrayList = arrayListOf()
        bankDetails = arrayOf(
            getString(R.string.bank_of_africa_0102907xxx),
            getString(R.string.bank_of_africa_0102907xxx),
            getString(R.string.bank_of_africa_0102907xxx),
            getString(R.string.bank_of_africa_0102907xxx),
            getString(R.string.bank_of_africa_0102907xxx),
            getString(R.string.bank_of_africa_0102907xxx),
            getString(R.string.bank_of_africa_0102907xxx),
            getString(R.string.bank_of_africa_0102907xxx),
            getString(R.string.bank_of_africa_0102907xxx),
            getString(R.string.bank_of_africa_0102907xxx),
            getString(R.string.bank_of_africa_0102907xxx),
            getString(R.string.bank_of_africa_0102907xxx),
            getString(R.string.bank_of_africa_0102907xxx),
            getString(R.string.bank_of_africa_0102907xxx),
            getString(R.string.bank_of_africa_0102907xxx),
            getString(R.string.bank_of_africa_0102907xxx),
            )
        userNames = arrayOf(
            getString(R.string.anelka_kweyu),
            getString(R.string.anelka_kweyu),
            getString(R.string.anelka_kweyu),
            getString(R.string.anelka_kweyu),
            getString(R.string.anelka_kweyu),
            getString(R.string.anelka_kweyu),
            getString(R.string.anelka_kweyu),
            getString(R.string.anelka_kweyu),
            getString(R.string.anelka_kweyu),
            getString(R.string.anelka_kweyu),
            getString(R.string.anelka_kweyu),
            getString(R.string.anelka_kweyu),
            getString(R.string.anelka_kweyu),
            getString(R.string.anelka_kweyu),
            getString(R.string.anelka_kweyu),
            getString(R.string.anelka_kweyu),
        )

        bankImages = arrayOf(
            R.drawable.tende_logo,
            R.drawable.kocela,
            R.drawable.kcb,
            R.drawable.nbk,
            R.drawable.tende_logo,
            R.drawable.kocela,
            R.drawable.kcb,
            R.drawable.nbk,
            R.drawable.tende_logo,
            R.drawable.kocela,
            R.drawable.kcb,
            R.drawable.nbk,
            R.drawable.tende_logo,
            R.drawable.kocela,
            R.drawable.kcb,
            R.drawable.nbk,
        )

        for (i in bankDetails.indices) {
            val bankDetail = BankAccount(
                bankDetails[i],
                userNames[i],
                bankImages[i]
            )
            bankAccountsArrayList.add(bankDetail)
        }
    }

}
