package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.asm.AsmActivity
import com.example.myapplication.behavior_scrollview.view.BahaviorLayoutActivity
import com.example.myapplication.bottomsheet.BottomSheetActivity
import com.example.myapplication.crash.CrashActivity
import com.example.myapplication.extension.buildLayoutManager
import com.example.myapplication.perfermance.PerHomeActivity
import com.example.myapplication.service.IntentServiceActivity
import com.example.myapplication.service.workmanager.WorkManagerActivity
import com.example.myapplication.view.StepsViewActivity

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    val menuRv: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.menu_rv)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = listOf<MenuItemData>(
            MenuItemData("BottomSheet", Intent(this, BottomSheetActivity::class.java)),
            MenuItemData("IntentService", Intent(this, IntentServiceActivity::class.java)),
            MenuItemData("WorkManager", Intent(this, WorkManagerActivity::class.java)),
            MenuItemData("BottomSheet & RV", Intent(this, BahaviorLayoutActivity::class.java)),
            MenuItemData("Step view", Intent(this, StepsViewActivity::class.java)),
            MenuItemData("ASM", Intent(this, AsmActivity::class.java)),
            MenuItemData("Crash", Intent(this, CrashActivity::class.java)),
            MenuItemData("Perfermance", Intent(this, PerHomeActivity::class.java)),
        )

        val menuAdapter = FeatureMenuAdapter(list)
        menuRv.buildLayoutManager(this)
            .adapter = menuAdapter
    }
}