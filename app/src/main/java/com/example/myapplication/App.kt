package com.example.myapplication

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.kwai.koom.base.CommonConfig
import com.kwai.koom.base.MonitorLog
import com.kwai.koom.base.MonitorManager
import com.kwai.koom.javaoom.monitor.OOMHprofUploader
import com.kwai.koom.javaoom.monitor.OOMMonitorConfig
import com.kwai.koom.javaoom.monitor.OOMReportUploader
import java.io.File

/**
 *
 * @author: cyd
 * @date: 2024/11/25
 * @since:
 */
class App : Application() {
    companion object {
        const val TAG = "AppApplication"
    }

    override fun onCreate() {
        super.onCreate()
        installKOOM()
        Handler().post {
            Log.d(
                TAG,
                "current looper is main looper ? ${Looper.myLooper() == Looper.getMainLooper()}"
            )

//            while(true) {
//                try {
//                    Looper.loop()
//                }catch (e: Throwable) {
//                    Log.e(TAG, e.localizedMessage)
//                }
//            }
        }

    }

    private fun installKOOM() {
        val commonConfig = CommonConfig.Builder()
            .setApplication(this)
            .setDebugMode(true)
            .setVersionNameInvoker { "1.0" }
            .setLoadSoInvoker {
            }
            .build()
        MonitorManager.initCommonConfig(commonConfig)

        val config = OOMMonitorConfig.Builder()
            .setThreadThreshold(50) //线程增量阈值
            .setFdThreshold(300) // fd增量阈值
            .setHeapThreshold(0.9f) // 堆内存使用比例
            .setVssSizeThreshold(1_000_000) // VSS内存阈值，单位kb
            .setMaxOverThresholdCount(1) // 超过最大次数阈值
            .setAnalysisMaxTimesPerVersion(3) // 每个版本最多分析次数
            .setAnalysisPeriodPerVersion(15 * 24 * 60 * 60 * 1000) // 每个版本的前15天才分析，超过这个时间段不再dump
            .setLoopInterval(1_000) // 检测的间隔时间
            .setEnableHprofDumpAnalysis(true)
            .setHprofUploader(object : OOMHprofUploader {
                override fun upload(file: File, type: OOMHprofUploader.HprofType) {
                    MonitorLog.e("OOMMonitor", "todo, upload hprof ${file.name} if necessary")
                }
            })
            .setReportUploader(object : OOMReportUploader {
                override fun upload(file: File, content: String) {
                    MonitorLog.i("OOMMonitor", content)
                    MonitorLog.e("OOMMonitor", "todo, upload report ${file.name} if necessary")
                }
            })
            .build()
        MonitorManager.addMonitorConfig(config)
    }
}