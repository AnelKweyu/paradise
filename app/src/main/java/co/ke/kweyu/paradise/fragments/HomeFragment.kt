package co.ke.kweyu.paradise.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.adapters.BestPlanAdapter
import co.ke.kweyu.paradise.adapters.InvestmentGuideAdapter
import co.ke.kweyu.paradise.databinding.FragmentHomeBinding
import co.ke.kweyu.paradise.enums.PlanEnum
import co.ke.kweyu.paradise.models.Guide
import co.ke.kweyu.paradise.models.Plan
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import co.ke.kweyu.paradise.activities.NotificationActivity
import co.ke.kweyu.paradise.firebase.FirestoreClass
import co.ke.kweyu.paradise.models.User

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var toolbar: Toolbar

    private lateinit var bestPlanAdapter: BestPlanAdapter
    private lateinit var bestPlanRecyclerView: RecyclerView
    private lateinit var plansArrayList: ArrayList<Plan>

    private lateinit var investmentGuideAdapter: InvestmentGuideAdapter
    private lateinit var investmentGuideRecyclerView: RecyclerView
    private lateinit var investmentGuidesArrayList: ArrayList<Guide>

    private lateinit var planTypes: Array<PlanEnum>
    private lateinit var planProfits: Array<Int>

    private lateinit var investmentGuideTitles: Array<String>
    private lateinit var investmentGuidesDescriptions: Array<String>
    private lateinit var investmentGuideImages: Array<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Call function to initialize data
        planCardDataInitialize()
        investmentGuideDataInitialize()

        bestPlanRecyclerView = binding.rvBestPlan
        bestPlanRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        bestPlanRecyclerView.setHasFixedSize(true)
        bestPlanAdapter = BestPlanAdapter(plansArrayList)
        bestPlanRecyclerView.adapter = bestPlanAdapter

        investmentGuideRecyclerView = binding.rvInvestmentGuide
        investmentGuideRecyclerView.layoutManager = LinearLayoutManager(context)
        investmentGuideRecyclerView.setHasFixedSize(true)
        investmentGuideAdapter = InvestmentGuideAdapter(investmentGuidesArrayList)
        investmentGuideRecyclerView.adapter = investmentGuideAdapter
        FirestoreClass().loadUserData(requireActivity())

        return binding.root
    }




    private fun planCardDataInitialize() {
        plansArrayList = arrayListOf()

        planTypes = arrayOf(
            PlanEnum.Platinum,
            PlanEnum.Gold,
            PlanEnum.Silver,
            PlanEnum.Bronze
        )

        planProfits = arrayOf(
            90, 60, 30, 15
        )

        for (i in planTypes.indices) {
            val plan = Plan(planTypes[i], planProfits[i])
            plansArrayList.add(plan)
        }
    }

    private fun investmentGuideDataInitialize() {
        investmentGuidesArrayList = arrayListOf()

        investmentGuideTitles = arrayOf(
            getString(R.string.basic_type_of_investments),
            getString(R.string.how_much_can_you_start_wit),
            getString(R.string.basic_type_of_investments),
            getString(R.string.how_much_can_you_start_wit),
            getString(R.string.basic_type_of_investments),
            getString(R.string.how_much_can_you_start_wit),
            getString(R.string.basic_type_of_investments),
            getString(R.string.how_much_can_you_start_wit),
            getString(R.string.basic_type_of_investments),
            getString(R.string.how_much_can_you_start_wit),
            getString(R.string.basic_type_of_investments),
            getString(R.string.how_much_can_you_start_wit),
            getString(R.string.basic_type_of_investments),
            getString(R.string.how_much_can_you_start_wit),
            getString(R.string.basic_type_of_investments),
            getString(R.string.how_much_can_you_start_wit)
        )

        investmentGuidesDescriptions = arrayOf(
            getString(R.string.this_is_how_you_set_your_foot_for_2020_stock_market_recession_what_s_next),
            getString(R.string.what_do_you_like_to_see_Its_a_very_different_market_from_2018_The_way),
            getString(R.string.this_is_how_you_set_your_foot_for_2020_stock_market_recession_what_s_next),
            getString(R.string.what_do_you_like_to_see_Its_a_very_different_market_from_2018_The_way),
            getString(R.string.this_is_how_you_set_your_foot_for_2020_stock_market_recession_what_s_next),
            getString(R.string.what_do_you_like_to_see_Its_a_very_different_market_from_2018_The_way),
            getString(R.string.this_is_how_you_set_your_foot_for_2020_stock_market_recession_what_s_next),
            getString(R.string.what_do_you_like_to_see_Its_a_very_different_market_from_2018_The_way),
            getString(R.string.this_is_how_you_set_your_foot_for_2020_stock_market_recession_what_s_next),
            getString(R.string.what_do_you_like_to_see_Its_a_very_different_market_from_2018_The_way),
            getString(R.string.this_is_how_you_set_your_foot_for_2020_stock_market_recession_what_s_next),
            getString(R.string.what_do_you_like_to_see_Its_a_very_different_market_from_2018_The_way),
            getString(R.string.this_is_how_you_set_your_foot_for_2020_stock_market_recession_what_s_next),
            getString(R.string.what_do_you_like_to_see_Its_a_very_different_market_from_2018_The_way),
            getString(R.string.this_is_how_you_set_your_foot_for_2020_stock_market_recession_what_s_next),
            getString(R.string.what_do_you_like_to_see_Its_a_very_different_market_from_2018_The_way)
        )

        investmentGuideImages = arrayOf(
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

        for (i in investmentGuideTitles.indices) {
            val guide = Guide(
                investmentGuideTitles[i],
                investmentGuidesDescriptions[i],
                investmentGuideImages[i]
            )
            investmentGuidesArrayList.add(guide)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_notification, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_notification -> {
                startActivity(Intent(requireActivity(), NotificationActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun updateLoggedInUserName(user: User) {
        val welcomeTv = binding.welcomeTextview
        val userFullName = user.name
        val arr = userFullName.split(' ')
        val (firstName) = arr
        welcomeTv.text = "Welcome $firstName"
    }
}
