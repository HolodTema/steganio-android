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
import com.terabyte.steganio.util.ShPreferencesHelper
import com.terabyte.steganio.viewmodel.SplashScreenViewModel

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var viewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
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
    }
}