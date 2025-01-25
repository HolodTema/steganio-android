package com.terabyte.steganio.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.terabyte.steganio.R
import com.terabyte.steganio.TextActivity
import com.terabyte.steganio.databinding.ActivitySplashScreenBinding
import com.terabyte.steganio.util.ShPreferencesHelper
import com.terabyte.steganio.viewmodel.SplashScreenViewModel

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var viewModel: SplashScreenViewModel
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[SplashScreenViewModel::class.java]

        viewModel.liveDataAlarm.observe(this) {
            if (it) {
                val hasPIN = ShPreferencesHelper.isKeyInShPreferences(this, ShPreferencesHelper.KEY_PIN)
                if (hasPIN) {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                else {
                    startActivity(Intent(this, TextActivity::class.java))
                }
            }
        }

        val listQuotes = listOf(
            "The best defence of secrets is the absence of these secrets.",
            "We offer the best encryption in the world\n(maybe, you believe it)",
            "Never gonna give your secrets up!",
            "All the words in this text block are joke. We hope you enjoy it!",
            "Words are deceptive: Steganio is not like Slitherio, Paperio, or Agario.",
            "All right then, keep your secrets...",
            "The truth always reveals itself."
        )
        binding.textQuote.text = listQuotes.random()

    }
}