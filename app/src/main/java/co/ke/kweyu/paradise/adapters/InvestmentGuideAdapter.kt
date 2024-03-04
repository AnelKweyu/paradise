package co.ke.kweyu.paradise.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.models.Guide

open class InvestmentGuideAdapter(
    private var investmentGuideList: ArrayList<Guide>
) : RecyclerView.Adapter<InvestmentGuideAdapter.InvestmentGuideViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvestmentGuideViewHolder {
        val view =           LayoutInflater.from(parent.context).inflate(
            R.layout.item_investment_guide,
            parent,
            false
        )
        return InvestmentGuideViewHolder(view)
    }

    override fun onBindViewHolder(holder: InvestmentGuideViewHolder, position: Int) {
        val investmentGuideItem = investmentGuideList[position]
        holder.tvTitle.text = investmentGuideItem.title
        holder.tvDescription.text = investmentGuideItem.description
        holder.ivImage.setImageResource(investmentGuideItem.image)
    }

    override fun getItemCount(): Int {
        return investmentGuideList.size
    }
    class InvestmentGuideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.textViewGuideTitle)
        val tvDescription : TextView = view.findViewById(R.id.textViewGuideDetails)
        val ivImage : ImageView =view.findViewById(R.id.ivGuideImage)
    }
}