package com.example.myapplication.view

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityStepsViewBinding
import com.example.simplekit.utils.dp
import com.example.simplekit.widget.RoundRectDrawable

class StepsViewActivity : AppCompatActivity() {

    private val mBinding by lazy {
        ActivityStepsViewBinding.inflate(layoutInflater)
    }

    private val mSignedBg by lazy {
        GradientDrawable().apply {
            this.shape = GradientDrawable.RECTANGLE
            this.cornerRadius = 12.dp.toFloat()
            this.setStroke(1.dp,Color.parseColor("#9CD7FF"))
            this.setColor(Color.parseColor("#294B7A"))
        }
    }

    private val mUnSignedBg by lazy {
        GradientDrawable().apply {
            this.shape = GradientDrawable.RECTANGLE
            this.cornerRadius = 12.dp.toFloat()
            this.setStroke(1.dp,Color.parseColor("#9CD7FF"))
            this.setColor(Color.parseColor("#294B7A"))
        }
    }

    private var mSignedState = false

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

        mBinding.roundBtn.background = GradientDrawable().apply {
            this.shape = GradientDrawable.RECTANGLE
            this.cornerRadius = 12.dp.toFloat()
            this.setStroke(2.dp,Color.parseColor("#9CD7FF"))
            this.setColor(Color.parseColor("#294B7A"))
        }

        onSignStateChange()
        mBinding.roundBtn.setOnClickListener {
            mSignedState = !mSignedState
            onSignStateChange()
        }
    }

    private fun onSignStateChange() {
        if(mSignedState) {
            mBinding.roundBtn.alpha = 1f
        } else {
            mBinding.roundBtn.alpha = 0.5f
        }
    }
}