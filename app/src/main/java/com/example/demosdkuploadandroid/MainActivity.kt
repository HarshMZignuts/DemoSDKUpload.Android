package com.example.demosdkuploadandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.mylibrarydemo.DemoClass
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val demo = DemoClass("hkm")
        val name = demo.getName()
        lifecycleScope.launch{
            val runtimeVersion = demo.getRuntimeVersion()
            Log.e("Name and Runtime Version","Name and Runtime Version ${name}/${runtimeVersion}")
        }
    }
}