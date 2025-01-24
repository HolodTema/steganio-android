package com.terabyte.steganio.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.terabyte.steganio.R
import com.terabyte.steganio.TextActivity
import com.terabyte.steganio.databinding.ActivityImageBinding

class ImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.rootActivityImage) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.bottomNavigationView.selectedItemId = R.id.menuItemImage
        binding.bottomNavigationView.setOnApplyWindowInsetsListener(null)
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.menuItemText -> {
                    startActivity(Intent(this, TextActivity::class.java))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
                    }
                    else {
                        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
                    }
                    finish()
                    true
                }
                R.id.menuItemImage -> true
                R.id.menuItemSettings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
                    }
                    else {
                        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
                    }
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}