package com.iperc.treelistpicker.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import com.iperc.treelistpicker.R
import com.iperc.treelistpicker.base.ListItem

class BreadcrumbsView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {
    var onBackButtonClickListener: (() -> Unit)? = null
    private val pathTextView: TextView
    private val backButton: ImageButton

    init {
        LayoutInflater.from(context).inflate(R.layout.view_breadcrumbs, this, true)
        pathTextView = findViewById(R.id.path)
        backButton = findViewById(R.id.back)

        backButton.setOnClickListener { onBackButtonClickListener?.invoke() }
    }

    fun setItem(list: List<ListItem>) {
        pathTextView.apply {
            text = ""
            list.forEach {
                if (list.indexOf(it) > 0) append(" / ")
                append(it.name)
            }
        }

        backButton.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
    }
}