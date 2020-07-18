package com.evgeniiverh.timer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler


class SplashScreenActivity : Activity() {
    // Время в милесекундах, в течение которого будет отображаться Splash Screen
    private val SPLASH_DISPLAY_LENGTH = 1000
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        Handler().postDelayed({ // По истечении времени, запускаем главный активити, а Splash Screen закрываем
            val mainIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            this@SplashScreenActivity.startActivity(mainIntent)
            finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}