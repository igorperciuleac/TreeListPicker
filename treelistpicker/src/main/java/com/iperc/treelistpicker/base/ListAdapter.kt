package com.iperc.treelistpicker.base

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.iperc.treelistpicker.R

class ListAdapter<in I : ListItem> internal constructor(
        private val itemList: List<I>,
        private val itemClickListener: ((I) -> Unit)? = null,
        private var itemIconResource: Int,
        private var directoryIconResource: Int
) : RecyclerView.Adapter<ListAdapter<I>.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_tree_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(itemList[position])

    }

    override fun getItemCount(): Int = itemList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var nameTextView: TextView = view.findViewById(R.id.name)
        private var iconImageView: ImageView = view.findViewById(R.id.icon)

        fun bindItem(item: I) {
            nameTextView.text = item.name
            iconImageView.setImageResource(
                    if (item.isDirectory) directoryIconResource else itemIconResource
            )
            itemView.setOnClickListener { itemClickListener?.invoke(item) }
        }
    }
}