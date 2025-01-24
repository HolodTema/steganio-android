package com.terabyte.steganio

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.terabyte.steganio.activity.ImageActivity
import com.terabyte.steganio.activity.SettingsActivity
import com.terabyte.steganio.databinding.ActivityTextBinding
import com.terabyte.steganio.viewmodel.MainViewModel

class TextActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()
        binding = ActivityTextBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.rootActivityText) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.bottomNavigationView.selectedItemId = R.id.menuItemText
        binding.bottomNavigationView.setOnApplyWindowInsetsListener(null)
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.menuItemText -> {
                    true
                }
                R.id.menuItemImage -> {
                    startActivity(Intent(this, ImageActivity::class.java))
                    overridePendingTransition(
                        R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_left
                    )
                    finish()
                    true
                }
                R.id.menuItemSettings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
                    finish()
                    true
                }
                else -> false
            }
        }
    }


}