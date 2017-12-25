package com.example.igor.myapplication

import android.os.Environment
import com.iperc.treelistpicker.provider.DataProvider

class FSDataProvider : DataProvider<FileItem> {
    override suspend fun getRoot(): List<FileItem> =
            Environment.getExternalStorageDirectory().listFiles().map { FileItem(it) }

    override suspend fun getByParent(listItem: FileItem): List<FileItem> =
            listItem.file.listFiles().map { FileItem(it) }
}
