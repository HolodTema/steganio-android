package com.terabyte.steganio.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.terabyte.steganio.R
import com.terabyte.steganio.TextActivity
import com.terabyte.steganio.databinding.ActivitySettingsBinding
import com.terabyte.steganio.util.INTENT_KEY_LOGIN_ACTIVITY_MODE
import com.terabyte.steganio.util.LOGIN_ACTIVITY_MODE_CREATE
import com.terabyte.steganio.util.ShPreferencesHelper
import com.terabyte.steganio.viewmodel.SettingsViewModel

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.rootActivitySettings) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        configureBottomNavigationView()

        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
        binding.switchHasPIN.isChecked = viewModel.liveDataHasPIN.value ?: false
        binding.switchHasPIN.setOnCheckedChangeListener { compoundButton, b ->
            viewModel.liveDataHasPIN.value = b
            if (b) {
                showDialogCreatePIN()
            }
            else {
                ShPreferencesHelper.deleteStringFromShPreferences(this, ShPreferencesHelper.KEY_PIN)
            }
        }

        binding.buttonChangePIN.setOnClickListener {
            startLoginActivityToChangePIN()
        }

        viewModel.liveDataHasPIN.observe(this) {
            binding.switchHasPIN.isChecked = it
            if (it) {
                binding.buttonChangePIN.visibility = View.VISIBLE
            }
            else {
                binding.buttonChangePIN.visibility = View.INVISIBLE
            }
        }

    }

    private fun configureBottomNavigationView() {
        binding.bottomNavigationView.selectedItemId = R.id.menuItemSettings
        binding.bottomNavigationView.setOnApplyWindowInsetsListener(null)
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.menuItemText -> {
                    startActivity(Intent(this, TextActivity::class.java))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.anim_slide_out_left, R.anim.anim_slide_in_right)
                    }
                    else {
                        overridePendingTransition(R.anim.anim_slide_out_left, R.anim.anim_slide_in_right)
                    }
                    finish()
                    true
                }
                R.id.menuItemImage -> {
                    startActivity(Intent(this, ImageActivity::class.java))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.anim_slide_out_left, R.anim.anim_slide_in_right)
                    }
                    else {
                        overridePendingTransition(R.anim.anim_slide_out_left, R.anim.anim_slide_in_right)
                    }
                    finish()
                    true
                }
                R.id.menuItemSettings -> true
                else -> false
            }
        }
    }

    private fun startLoginActivityToChangePIN() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra(INTENT_KEY_LOGIN_ACTIVITY_MODE, LOGIN_ACTIVITY_MODE_CREATE)
        startActivity(intent)
    }

    private fun showDialogCreatePIN() {
        val builder = AlertDialog.Builder(this)
            .setMessage("Do you want to create PIN for this app?")
            .setPositiveButton(
                "yes",
                DialogInterface.OnClickListener({ dialog, which ->
                    startLoginActivityToChangePIN()
                })
            )
            .setNegativeButton(
                "cancel",
                DialogInterface.OnClickListener({ dialog, which ->
                    binding.switchHasPIN.isChecked = false
                    dialog.cancel()
                })
            )
            .setCancelable(true)
            .create()
            .show()
    }
}