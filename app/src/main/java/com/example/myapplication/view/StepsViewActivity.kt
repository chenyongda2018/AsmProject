package com.example.myapplication.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityStepsViewBinding

class StepsViewActivity : AppCompatActivity() {

    private val mBinding by lazy {
        ActivityStepsViewBinding.inflate(layoutInflater)
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
        val nodes = arrayOf(5, 15, 25)
        var curProgress = 5
        mBinding.progress.initProgress(curProgress, 30, nodes, null)

        mBinding.plusBtn.setOnClickListener {
            curProgress+=5
            mBinding.progress.setProgress(curProgress)
        }
        mBinding.minusBtn.setOnClickListener {
            curProgress-=5
            mBinding.progress.setProgress(curProgress)
        }
    }
}