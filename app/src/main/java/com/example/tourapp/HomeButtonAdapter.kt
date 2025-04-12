package com.example.tourapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomeButtonAdapter(
    private val context: Context,
    private val buttons: List<HomeButtonItem>
) : RecyclerView.Adapter<HomeButtonAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
        Log.d("HomeButtonAdapter", "ClickListener set")
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.homeButton)
        val title: TextView = itemView.findViewById(R.id.buttonText)

        init {
            itemView.setOnClickListener {
                Log.d("HomeButtonAdapter", "Button clicked at position $adapterPosition")
                // Add visual feedback
                icon.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100)
                    .withEndAction {
                        icon.animate().scaleX(1f).scaleY(1f).setDuration(100)
                            .withEndAction {
                                itemClickListener?.onItemClick(adapterPosition)
                            }
                    }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.homepage_button, parent, false)
        Log.d("HomeButtonAdapter", "View created for view type $viewType")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val button = buttons[position]
        Log.d("HomeButtonAdapter", "Binding position $position: ${button.title}")

        holder.icon.setImageResource(button.Iconres)
        holder.title.text = button.title
    }

    override fun getItemCount(): Int {
        Log.d("HomeButtonAdapter", "Total items: ${buttons.size}")
        return buttons.size
    }
}