package co.ke.kweyu.paradise.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.ke.kweyu.paradise.R

open class BestPlanAdapter(
    private val context: Context,
    private var plans: ArrayList<String>
    ) : RecyclerView.Adapter<BestPlanAdapter.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.best_plan_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plan = plans[position]

        holder.tvPlan.text = plan

        if (position % 2 == 0) {
            holder.cardItem.setBackground(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.gold_card_bg
                )
            )

        }else{
            holder.cardItem.setBackground(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.gold_card_bg
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return plans.size
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPlan: TextView = view.findViewById(R.id.planName)
        val cardItem: ConstraintLayout = view.findViewById(R.id.constraintLayoutCard)
    }
}