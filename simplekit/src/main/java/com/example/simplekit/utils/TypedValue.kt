@file:JvmName("NumEx")

package com.example.simplekit.utils

/**
 *
 * @author: cyd
 * @date: 2024/11/5
 * @since:
 */
import android.content.res.Resources
import android.util.TypedValue

/**
 * @author https://github.com/funnywolfdadada
 * @since 2020/5/30
 */
val Number.dp: Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()

val Number.sp: Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()
