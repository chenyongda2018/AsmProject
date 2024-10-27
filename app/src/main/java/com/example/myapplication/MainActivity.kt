package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.util.DeviceUtil

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    val getDeviceIdBtn by lazy {
        findViewById<Button>(R.id.getDeviceIdBtn)
    }

    val getBrandBtn by lazy {
        findViewById<Button>(R.id.getBrandBtn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        getDeviceIdBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val deviceId = DeviceUtil.getDeviceId(this@MainActivity)
                Log.d(TAG, "deviceId=$deviceId")
            }
        })

        getBrandBtn.setOnClickListener {
            val brand = DeviceUtil.getBrand()
            Log.d(TAG, "brand=$brand")
            val i = Intent(this,BottomSheetActivity::class.java)
            startActivity(i)
        }


    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume---")
        test(1, 1)
    }

    fun test(a: Int, b: Int) {
        val c = a + b
        val d = c + 0
        println(d)
    }
}