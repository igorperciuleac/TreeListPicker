package com.example.igor.myapplication

import com.iperc.treelistpicker.base.ListItem

import java.io.File

open class FileItem(val file: File) : ListItem {
    override val isDirectory: Boolean
        get() = file.isDirectory

    override val name: String
        get() = file.name
}