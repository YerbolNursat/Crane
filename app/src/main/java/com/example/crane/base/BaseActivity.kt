package com.example.crane.base

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.crane.R

abstract class BaseActivity() : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(baseContext, R.color.black)
    }

    fun setStatusBar(color: Int) {
        window.statusBarColor = ContextCompat.getColor(baseContext, color)
    }

}