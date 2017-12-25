package com.iperc.treelistpicker.component

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.iperc.treelistpicker.R
import com.iperc.treelistpicker.base.ListAdapter
import com.iperc.treelistpicker.base.ListItem
import com.iperc.treelistpicker.provider.DataProvider
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import java.util.*


class TreeListView<I : ListItem>(context: Context?) : FrameLayout(context) {
    var itemIconResource: Int = R.drawable.item_icon
    var directoryIconResource: Int = R.drawable.directory_icon

    private val itemStack: Deque<I> = ArrayDeque()
    private val itemList: MutableList<I> = mutableListOf()
    private val breadcrumbs: BreadcrumbsView
    private val recycleView: RecyclerView
    lateinit var dataProvider: DataProvider<I>

    init {
        LayoutInflater.from(context).inflate(R.layout.view_tree_list, this, true)

        breadcrumbs = findViewById(R.id.breadcrumbs)
        recycleView = findViewById(R.id.itemList)

        breadcrumbs.onBackButtonClickListener = ({
            itemStack.pollLast()
            if (itemStack.isEmpty()) {
                loadRoot()
            } else {
                loadByParent(itemStack.peekLast())
            }
            breadcrumbs.setItem(itemStack.toList())
        })

        recycleView.layoutManager = LinearLayoutManager(context)
        recycleView.adapter = ListAdapter(
                itemList,
                { i -> onItemClick(i) },
                itemIconResource,
                directoryIconResource
        )
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        loadRoot()
        breadcrumbs.setItem(itemStack.toList())
    }

    private fun onItemClick(item: I) {
        if (item.isDirectory) {
            loadByParent(item)
            itemStack.add(item)
            breadcrumbs.setItem(itemStack.toList())
        }
    }

    private fun loadRoot() = async(UI) {
        itemList.clear()
        itemList.addAll(async { dataProvider.getRoot() }.await())
        recycleView.adapter.notifyDataSetChanged()
    }

    private fun loadByParent(item: I) = async(UI) {
        itemList.clear()
        itemList.addAll(async { dataProvider.getByParent(item) }.await())
        recycleView.adapter.notifyDataSetChanged()
    }


    fun getParentItem(): I? = itemStack.peekLast()
}