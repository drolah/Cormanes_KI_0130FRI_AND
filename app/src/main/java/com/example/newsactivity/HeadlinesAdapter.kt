package com.example.newsactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HeadlinesAdapter(
    private val newsList: List<News>,
    private val onClick: (News) -> Unit
) : RecyclerView.Adapter<HeadlinesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(newsList[position], onClick)
    }

    override fun getItemCount(): Int = newsList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(news: News, onClick: (News) -> Unit) {
            textView.text = news.headline
            itemView.setOnClickListener { onClick(news) }
        }
    }
}