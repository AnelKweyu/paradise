package co.ke.kweyu.paradise.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.ke.kweyu.paradise.R

open class InvestmentGuideAdapter(
    private val context: Context,
    private var investmentGuideList: ArrayList<String>
) : RecyclerView.Adapter<InvestmentGuideAdapter.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.investment_guide_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val investmentGuideItem = investmentGuideList[position]
        holder.tvTitle.text = investmentGuideItem
    }

    override fun getItemCount(): Int {
        return investmentGuideList.size
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.textViewGuideTitle)
    }
}