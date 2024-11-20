package com.example.myapplication.crash

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityCrashBinding
import kotlin.concurrent.thread

class CrashActivity : AppCompatActivity() {

    companion object {
        val TAG = CrashActivity::class.java.simpleName
    }

    private val binding by lazy {
        ActivityCrashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            Log.d(TAG,"thread=${t.name},--${e}")
//            uncaughtExceptionHandler.uncaughtException(t, e)
        }



        binding.clickException.setOnClickListener {
            throw RuntimeException("click exception...")
        }

        binding.threadException.setOnClickListener {
            thread {
                throw RuntimeException("thread exception...")
            }
        }

        binding.handlerException.setOnClickListener {
            Handler().post {
                throw RuntimeException("Handler exception...")
            }
        }
    }
}