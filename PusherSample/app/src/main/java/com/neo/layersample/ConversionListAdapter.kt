package com.neo.layersample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pusher.chatkit.rooms.Room

class ConversionListAdapter (private val onConversationSelected: OnConversationSelected)
    : RecyclerView.Adapter<ConversionListAdapter.ViewHolder>()  {

    private var conversationList: List<Room>  = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(conversationList[position])

    override fun getItemCount(): Int = conversationList.size

    fun setList(list:List<Room>){
        this.conversationList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val text = itemView.findViewById<TextView>(android.R.id.text1)!!

        fun bind(item: Room) = with(itemView) {
            text.text = item.name
            itemView.setOnClickListener {
                onConversationSelected.conversationSelected(item)
            }

        }

    }

    interface OnConversationSelected {
        fun conversationSelected(conversation: Room)
    }

}