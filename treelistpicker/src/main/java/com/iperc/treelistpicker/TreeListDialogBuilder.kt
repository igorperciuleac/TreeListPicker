package com.iperc.treelistpicker

import android.app.AlertDialog
import android.content.Context
import android.widget.LinearLayout
import com.iperc.treelistpicker.base.ListItem
import com.iperc.treelistpicker.component.TreeListView
import com.iperc.treelistpicker.provider.DataProvider


class TreeListDialogBuilder<I : ListItem>(private val context: Context, private val dataProvider: DataProvider<I>) {
    var title: String? = null
    var positiveButtonClick: ((I?) -> Unit)? = null
    var negativeButtonClick: (() -> Unit)? = null

    var positiveButtonTitle = context.resources.getString(android.R.string.ok)
    var negativeButtonTitle = context.resources.getString(android.R.string.cancel)

    var itemIconResource: Int = R.drawable.item_icon
    var directoryIconResource: Int = R.drawable.directory_icon

    fun build(): AlertDialog {
        val treeListView = TreeListView<I>(context)
        treeListView.dataProvider = dataProvider
        treeListView.itemIconResource = itemIconResource
        treeListView.directoryIconResource = directoryIconResource

        return AlertDialog.Builder(context).apply {
            setView(LinearLayout(context).apply { addView(treeListView) })
            setTitle(title)
            setPositiveButton(
                    positiveButtonTitle,
                    { _, _ -> positiveButtonClick?.invoke(treeListView.getParentItem()) }
            )
            setNegativeButton(
                    negativeButtonTitle,
                    { _, _ -> negativeButtonClick?.invoke() }
            )
        }.create()
    }
}