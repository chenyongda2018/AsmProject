package com.example.myapplication.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * @author: cyd
 * @date: 2024/10/28
 * @since:
 */
public class MyIntentService extends IntentService {

    private int count = 0;

    private boolean isRunning = false;

    private LocalBroadcastManager localBroadcastManager;

    public MyIntentService() {
        this("itent_serasd");
    }

    public MyIntentService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        sendServiceStatus("服务启动");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendServiceStatus("服务销毁");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            sendThreadStatus("线程启动", count);
            Thread.sleep(1000);

            sendServiceStatus("服务运行中...");

            isRunning = true;
            count = 0;

            while (isRunning) {
                count++;
                if (count >= 100) {
                    isRunning = false;
                }
                Thread.sleep(50);
                sendThreadStatus("线程运行中", count);
            }
            sendThreadStatus("线程结束", count);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    // 发送服务状态信息
    private void sendServiceStatus(String status) {
        Intent intent = new Intent(IntentServiceFragment.ACTION_SERVICE);
        intent.putExtra("status", status);
        localBroadcastManager.sendBroadcast(intent);
    }

    // 发送线程状态信息
    private void sendThreadStatus(String status, int progress) {
        Intent intent = new Intent(IntentServiceFragment.ACTION_THREAD);
        intent.putExtra("status", status);
        intent.putExtra("progress", progress);
        localBroadcastManager.sendBroadcast(intent);
    }
}
