package com.example.puli

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BenfAdapter(
    private val context: Context,
    private val items: List<BenfDetails>
) : RecyclerView.Adapter<BenfAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txtName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_benf, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.txtName.text = item.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, BenfDetailActivity::class.java).apply {
                putExtra("item", item)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = items.size
}
