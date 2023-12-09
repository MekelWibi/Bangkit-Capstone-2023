package com.dicoding.calofruit.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dicoding.calofruit.R
import com.dicoding.calofruit.ui.welcome.WelcomeActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreenActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_DELAY_MS)
    }

    companion object {
        private const val SPLASH_SCREEN_DELAY_MS = 1000L
    }
}