package co.ke.kweyu.paradise.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.models.History



open class HistoryAdapter(
    private var historyList: ArrayList<History>
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):HistoryViewHolder {
        val view =             LayoutInflater.from(parent.context).inflate(
            R.layout.item_history,
            parent,
            false
        )
        return HistoryViewHolder(view)
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyItem = historyList[position]

        holder.historyRetailPriceText.text = historyItem.retailPrice
        holder.historyOwnershipText.text = historyItem.ownership
        holder.historyDateText.text = historyItem.date

        when {
            position % 2 == 0 -> {
                holder.historyRetailPriceText.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.historyGreen
                    )
                )
            }
            else -> {
                holder.historyRetailPriceText.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.black
                    )
                )
            }
        }


    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return historyList.size
    }



    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val historyRetailPriceText : TextView = view.findViewById(R.id.historyRetailPriceText)
        val historyOwnershipText : TextView = view.findViewById(R.id.historyOwnershipText)
        val historyDateText : TextView = view.findViewById(R.id.historyDateText)
    }
}
