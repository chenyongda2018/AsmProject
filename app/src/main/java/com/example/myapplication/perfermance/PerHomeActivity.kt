package com.example.myapplication.perfermance

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityPerHomeBinding
import com.kwai.koom.javaoom.monitor.OOMMonitor

class PerHomeActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPerHomeBinding.inflate(layoutInflater)
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
        binding.leakBtn.setOnClickListener {
            Handler().postDelayed({
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
            }, 3000)
            finish()
        }
        binding.startLoop.setOnClickListener {
            OOMMonitor.startLoop(false,false,2000L)
        }
    }
}