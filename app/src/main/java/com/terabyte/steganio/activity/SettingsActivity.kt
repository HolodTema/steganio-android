package com.terabyte.steganio.activity

import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
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
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        configureBottomNavigationView()
        configureSwitchLoginTextCaptions()
        configureSwitchDarkTheme()
        configureAuthBlock()
    }

    override fun getTheme(): Resources.Theme {
        val theme = super.getTheme()
        val isDarkTheme = ShPreferencesHelper.getBooleanFromShPreferences(this@SettingsActivity, ShPreferencesHelper.KEY_HAS_DARK_THEME)
        if (isDarkTheme) {
            theme.applyStyle(R.style.Theme_SteganioLight, true)
        }
        else {
            theme.applyStyle(R.style.Theme_SteganioLight, true)
        }
        return theme
    }

    private fun configureAuthBlock() {
        binding.switchHasPIN.isChecked = viewModel.liveDataHasPIN.value ?: false
        if (binding.switchHasPIN.isChecked) {
            binding.switchBiometricAuthentication.isChecked = ShPreferencesHelper.getBooleanFromShPreferences(this@SettingsActivity, ShPreferencesHelper.KEY_HAS_BIOMETRIC_AUTH)
        }
        binding.switchHasPIN.setOnCheckedChangeListener { compoundButton, b ->
            viewModel.liveDataHasPIN.value = b
            if (b) {
                showDialogCreatePIN()
            }
            else {
                ShPreferencesHelper.deleteStringFromShPreferences(this, ShPreferencesHelper.KEY_PIN)
                ShPreferencesHelper.putBooleanToShPreferences(this, ShPreferencesHelper.KEY_HAS_BIOMETRIC_AUTH, false)
                binding.switchBiometricAuthentication.isChecked = false
            }
        }

        binding.switchBiometricAuthentication.setOnCheckedChangeListener { compoundButton, b ->
            ShPreferencesHelper.putBooleanToShPreferences(this@SettingsActivity, ShPreferencesHelper.KEY_HAS_BIOMETRIC_AUTH, b)
        }

        binding.buttonChangePIN.setOnClickListener {
            startLoginActivityToChangePIN()
        }

        viewModel.liveDataHasPIN.observe(this) {
            if (it) {
                val typedValue = TypedValue()
                theme.resolveAttribute(com.google.android.material.R.attr.colorOnBackground, typedValue, true)
                binding.textCaptionBiometricAuth.setTextColor(typedValue.data)
            }
            else {
                val typedValue = TypedValue()
                theme.resolveAttribute(R.attr.colorOnBackgroundDisabled, typedValue, true)
                binding.textCaptionBiometricAuth.setTextColor(typedValue.data)
            }

            binding.switchHasPIN.isChecked = it
            binding.buttonChangePIN.isEnabled = it
            binding.switchBiometricAuthentication.isEnabled = it
        }
    }

    private fun configureSwitchDarkTheme() {
        binding.switchDarkTheme.isChecked = ShPreferencesHelper.getBooleanFromShPreferences(this, ShPreferencesHelper.KEY_HAS_DARK_THEME)
        binding.switchDarkTheme.setOnCheckedChangeListener { compoundButton, b ->
            ShPreferencesHelper.putBooleanToShPreferences(this@SettingsActivity, ShPreferencesHelper.KEY_HAS_DARK_THEME, b)
            recreate()
        }
    }

    private fun configureSwitchLoginTextCaptions() {
        binding.switchLoginTextCaptions.isChecked = ShPreferencesHelper.getBooleanFromShPreferences(this, ShPreferencesHelper.KEY_HAS_SPLASH_TEXT_BLOCK)
        binding.switchLoginTextCaptions.setOnCheckedChangeListener { compoundButton, b ->
            ShPreferencesHelper.putBooleanToShPreferences(this, ShPreferencesHelper.KEY_HAS_SPLASH_TEXT_BLOCK, b)
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
            .setCancelable(false)
            .setOnDismissListener {
                binding.switchHasPIN.isChecked = false
            }
            .create()
            .show()
    }
}