@file:JvmName("RvEx")
package com.example.myapplication.extension

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.bottomsheet.SimpleStringListAdapter

/**
 *
 * @author: cyd
 * @date: 2024/10/27
 * @since:
 */
fun RecyclerView.buildLayoutManager(context: Context): RecyclerView {
    this.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
    return this
}

fun RecyclerView.buildMockAdapter(): RecyclerView {
    this.adapter = SimpleStringListAdapter(getMockData())
    return this
}

fun getMockData(): List<String> {
    val mockList = ArrayList<String>()
    for (i in 0..19) {
        mockList.add("测试$i")
    }
    return mockList
}