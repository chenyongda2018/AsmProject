package com.example.myapplication.service.workmanager

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityWorkManagerBinding
import java.util.concurrent.TimeUnit

class WorkManagerActivity : AppCompatActivity() {

    val mBinding by lazy {
        ActivityWorkManagerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(mBinding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mBinding.workBegin.setOnClickListener {
            executeWork()
        }
    }

    fun executeWork() {

        val inputData = Data.Builder()
            .putString("key", "0")
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .build()

        val request =
            OneTimeWorkRequest.Builder(TestWork::class.java) // 注意这里使用::class.java
                .setConstraints(constraints)
                .setInitialDelay(3, TimeUnit.SECONDS)
                .addTag("test")
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    1000L,
                    TimeUnit.MILLISECONDS
                )
                .setInputData(inputData)
                .build()

        // 使用by lazy延迟初始化WorkManager实例
        val workManager = WorkManager.getInstance(this)

        // 使用Kotlin的简化语法观察WorkInfo
        workManager.getWorkInfoByIdLiveData(request.id).observe(this) { workInfo ->
            workInfo?.let {
                val value = it.outputData.getInt("key",-1)
                if (it.state == WorkInfo.State.SUCCEEDED) {
                    mBinding.workResult.text = "result: sucess ,value = $value"
                } else if(it.state == WorkInfo.State.FAILED) {
                    mBinding.workResult.text = "result: failed ,value = $value"
                } else {
                    mBinding.workResult.text = "result: ${it.state} ,value = $value"
                }
            }
        }

        // 提交任务
        workManager.enqueue(request)
    }
}