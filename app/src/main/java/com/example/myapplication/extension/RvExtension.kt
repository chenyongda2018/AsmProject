package com.example.myapplication.extension

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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