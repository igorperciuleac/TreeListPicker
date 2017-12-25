package com.iperc.treelistpicker.provider

import com.iperc.treelistpicker.base.ListItem

interface DataProvider<I : ListItem> {
    suspend fun getRoot(): List<I>
    suspend fun getByParent(listItem: I): List<I>
}