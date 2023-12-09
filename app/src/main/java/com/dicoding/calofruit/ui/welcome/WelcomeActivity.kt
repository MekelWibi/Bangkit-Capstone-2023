package com.dicoding.calofruit.ui.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.viewModels
import com.dicoding.calofruit.utils.ViewModelFactory
import com.dicoding.calofruit.databinding.ActivityWelcomeBinding
import com.dicoding.calofruit.ui.login.LoginActivity
import com.dicoding.calofruit.ui.main.MainActivity
import com.dicoding.calofruit.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private val mainViewModel by viewModels<WelcomeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        mainViewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        binding.btnLocalization.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivWelcome, View.TRANSLATION_X, -50f, 50f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.title, View.ALPHA, 1f).setDuration(100)
        val title2 = ObjectAnimator.ofFloat(binding.title2, View.ALPHA, 1f).setDuration(100)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(100)
        val btnRegister =
            ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                title2,
                btnLogin,
                btnRegister
            )
            start()
        }
    }
}