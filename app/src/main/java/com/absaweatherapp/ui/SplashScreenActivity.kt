package com.absaweatherapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.absaweatherapp.MainActivity
import com.absaweatherapp.R


class SplashScreenActivity : Activity() {
    private var handler:Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_slash)

        handler = Handler()
        handler?.postDelayed({
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}