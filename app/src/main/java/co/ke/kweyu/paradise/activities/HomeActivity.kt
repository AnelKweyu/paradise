package co.ke.kweyu.paradise.activities


import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.adapters.BestPlanAdapter
import co.ke.kweyu.paradise.adapters.InvestmentGuideAdapter

class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupActionBar()

//        showProgressDialog(resources.getString(R.string.please_wait))

        val rvBestPlan: RecyclerView = findViewById(R.id.rv_best_plan)


        rvBestPlan.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
        rvBestPlan.setHasFixedSize(true)
        val plans = getPlans()
        val adapterBestPlan = BestPlanAdapter(this@HomeActivity, plans)
        rvBestPlan.adapter = adapterBestPlan



        val rvInvestmentGuide: RecyclerView = findViewById(R.id.rv_investment_guide)


        rvInvestmentGuide.layoutManager = LinearLayoutManager(this@HomeActivity)
        rvInvestmentGuide.setHasFixedSize(true)
        val guides = getGuides()


        val adapterGuide = InvestmentGuideAdapter(this@HomeActivity, guides)
        rvInvestmentGuide.adapter = adapterGuide
    }

    private fun setupActionBar() {
        val toolbarMembersInActivity: Toolbar = findViewById(R.id.toolbar_members_activity)

        setSupportActionBar(toolbarMembersInActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_navigation_menu)
            actionBar.setTitle("")
        }

//        toolbarMembersInActivity.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_notification, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun getPlans() : ArrayList<String>{
        val plans = ArrayList<String>()
        for(i in 1..7){
            plans.add("Gold $i")
        }
        return plans
    }

    private fun getGuides() : ArrayList<String>{
        val guide = ArrayList<String>()
        for(i in 1..15){
            guide.add("Basic type of investments $i")
        }
        return guide
    }

}