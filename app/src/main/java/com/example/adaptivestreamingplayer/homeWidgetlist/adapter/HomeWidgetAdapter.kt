package com.example.adaptivestreamingplayer.homeWidgetlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adaptivestreamingplayer.databinding.HomeWidgetItemBinding
import com.example.adaptivestreamingplayer.homeWidgetlist.OrderData

class HomeWidgetAdapter(
    private val items: List<OrderData>
) : RecyclerView.Adapter<HomeWidgetAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeWidgetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: HomeWidgetItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderData) {
            
        }
    }
}
