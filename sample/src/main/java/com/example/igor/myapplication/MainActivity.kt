package com.example.igor.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.iperc.treelistpicker.TreeListDialogBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }

        findViewById<Button>(R.id.fs_dialog_button).setOnClickListener {
            TreeListDialogBuilder(this, FSDataProvider()).apply {
                title = "Select a folder"
                itemIconResource = R.drawable.item_icon
                directoryIconResource = R.drawable.item_icon
                positiveButtonTitle = "Select"
                positiveButtonClick = {
                    Toast.makeText(this@MainActivity, it?.file?.path, Toast.LENGTH_LONG).show()
                }
            }.build().show()
        }
    }
}
