package co.ke.kweyu.paradise.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.models.Notification



open class NotificationAdapter(
    private var notificationsList: ArrayList<Notification>
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):NotificationViewHolder {
        val view =             LayoutInflater.from(parent.context).inflate(
            R.layout.item_notification,
            parent,
            false
        )
        return NotificationViewHolder(view)
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
    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notificationItem = notificationsList[position]

        holder.textNotificationDetails.text = notificationItem.description
        holder.notificationItemImage.setImageResource(notificationItem.image)

    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return notificationsList.size
    }



    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class NotificationViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textNotificationDetails : TextView = view.findViewById(R.id.textNotificationDetails)
        val notificationItemImage : ImageView =view.findViewById(R.id.notificationItemImage)
    }
}
