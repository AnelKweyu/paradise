package co.ke.kweyu.paradise.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.enums.PlanEnum
import co.ke.kweyu.paradise.models.Plan

open class BestPlanAdapter(
    private val plans: ArrayList<Plan>
    ) : RecyclerView.Adapter<BestPlanAdapter.BestPlanViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestPlanViewHolder {
        return BestPlanViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_best_plan_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BestPlanViewHolder, position: Int) {
        val plan = plans[position]
        val profit = plan.profit.toString()

        holder.tvProfit.text= buildString {
        append(profit)
        append("% return")
        }
        when (plan.type) {
            PlanEnum.Platinum -> {
                holder.tvPlan.text = "Platinum"
                holder.ivSymbol.setImageResource(R.drawable.bitcoin)
                holder.cardItem.setBackgroundResource(R.drawable.platinum_card_bg)
            }
            PlanEnum.Gold -> {
                holder.tvPlan.text = "Gold"
                holder.ivSymbol.setImageResource(R.drawable.dolar)
                holder.cardItem.setBackgroundResource(R.drawable.gold_card_bg)
            }
            PlanEnum.Silver -> {
                holder.tvPlan.text = "Silver"
                holder.ivSymbol.setImageResource(R.drawable.euro)
                holder.cardItem.setBackgroundResource(R.drawable.silver_card_bg)
            }
            PlanEnum.Bronze -> {
                holder.tvPlan.text = "Bronze"
                holder.ivSymbol.setImageResource(R.drawable.bronzebitcoin)
                holder.cardItem.setBackgroundResource(R.drawable.bronze_card_bg)
            }
            PlanEnum.Unplanned -> {
                holder.tvPlan.text = "Unplanned"
                holder.ivSymbol.setImageResource(R.drawable.dolar)
                holder.cardItem.setBackgroundResource(R.drawable.silver_card_bg)
            }
        }
    }

    override fun getItemCount(): Int {
        return plans.size
    }
    class BestPlanViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPlan: TextView = view.findViewById(R.id.planName)
        val tvProfit: TextView = view.findViewById(R.id.planProfit)
        val ivSymbol: ImageView = view.findViewById(R.id.planSymbol)
        val cardItem: ConstraintLayout = view.findViewById(R.id.constraintLayoutCard)
    }
}



