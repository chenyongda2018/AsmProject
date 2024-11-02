package com.example.myapplication.service.workmanager

import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 *
 * @author: cyd
 * @date: 2024/11/2
 * @since:
 */
class TestWork(context: Context, workParams: WorkerParameters) : Worker(context, workParams) {

    companion object {
        const val TAG = "TestWork"
    }


    override fun doWork(): Result {
        Log.i(TAG, "TestWork start!,isMainThread=" + (Looper.getMainLooper() == Looper.myLooper()))

        val inputData = inputData.getString("key")?.toInt() ?: 0

        Thread.sleep(3000L)

        Log.i(TAG, "TestWork inputData = ${inputData}")

        return handleRsp(inputData)
    }


    private fun handleRsp(param: Int): Result {

        var outputData = Data.Builder().putInt("key", param).build()

        return when (param) {
            0 -> {
                Result.success(outputData)
            }

            1 -> {
                Result.retry()
            }

            else -> {
                Result.failure(outputData)
            }

        }
    }

}