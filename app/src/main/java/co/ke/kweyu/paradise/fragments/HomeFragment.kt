package co.ke.kweyu.paradise.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.adapters.BestPlanAdapter
import co.ke.kweyu.paradise.adapters.InvestmentGuideAdapter
import co.ke.kweyu.paradise.enums.PlanEnum
import co.ke.kweyu.paradise.models.Guide
import co.ke.kweyu.paradise.models.Plan


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"





/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val homeFragmentView: View = inflater.inflate(R.layout.fragment_home, container, false)

        planCardDataInitialize()


        bestPlanRecyclerView = homeFragmentView.findViewById(R.id.rv_best_plan)
        bestPlanRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        bestPlanRecyclerView.setHasFixedSize(true)
        bestPlanAdapter = BestPlanAdapter(plansArrayList)
        bestPlanRecyclerView.adapter = bestPlanAdapter

        investmentGuideDataInitialize()

        investmentGuideRecyclerView = homeFragmentView.findViewById(R.id.rv_investment_guide)
        investmentGuideRecyclerView.layoutManager = LinearLayoutManager(context)
        investmentGuideRecyclerView.setHasFixedSize(true)
        investmentGuideAdapter = InvestmentGuideAdapter(investmentGuidesArrayList)
        investmentGuideRecyclerView.adapter = investmentGuideAdapter

        return homeFragmentView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                HomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    private fun planCardDataInitialize(){
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

        for (i in planTypes.indices){
            val plan = Plan(planTypes[i],planProfits[i])
            plansArrayList.add(plan)
        }

    }

    private fun investmentGuideDataInitialize(){
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

        for (i in investmentGuideTitles.indices){
            val guide = Guide(investmentGuideTitles[i],investmentGuidesDescriptions[i],investmentGuideImages[i])
            investmentGuidesArrayList.add(guide)
        }

    }
}