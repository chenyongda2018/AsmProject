package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.bottomsheet.BottomSheetActivity
import com.example.myapplication.extension.buildLayoutManager
import com.example.myapplication.util.DeviceUtil

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    val menuRv: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.menu_rv)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = listOf<MenuItemData>(
            MenuItemData("BottomSheet", Intent(this, BottomSheetActivity::class.java))
        )

        val menuAdapter = FeatureMenuAdapter(list)
        menuRv.buildLayoutManager(this)
            .adapter = menuAdapter
    }
}