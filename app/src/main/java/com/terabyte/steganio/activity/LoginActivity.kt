package com.terabyte.steganio.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.terabyte.steganio.R
import com.terabyte.steganio.TextActivity
import com.terabyte.steganio.databinding.ActivityLoginBinding
import com.terabyte.steganio.util.INTENT_KEY_LOGIN_ACTIVITY_MODE
import com.terabyte.steganio.util.INTENT_KEY_PIN_TO_CONFIRM
import com.terabyte.steganio.util.LOGIN_ACTIVITY_MODE_CONFIRM
import com.terabyte.steganio.util.LOGIN_ACTIVITY_MODE_CREATE
import com.terabyte.steganio.util.showToast
import com.terabyte.steganio.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()
        window.navigationBarColor = ContextCompat.getColor(this, R.color.colorOnSecondary)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root_activity_login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        viewModel.liveDataMode.observe(this) { mode ->
            if (mode.isEmpty()) {
                binding.buttonBack.visibility = View.GONE
            }
            else {
                binding.buttonBack.visibility = View.VISIBLE
            }
        }

        if (intent.extras != null && intent.extras!!.containsKey(INTENT_KEY_LOGIN_ACTIVITY_MODE)) {
            val mode = intent.extras!!.getString(INTENT_KEY_LOGIN_ACTIVITY_MODE)
            if (mode == LOGIN_ACTIVITY_MODE_CREATE) {
                viewModel.setModeCreate()
            }
            else if (mode == LOGIN_ACTIVITY_MODE_CONFIRM) {
                val pinToConfirm = intent.extras!!.getString(INTENT_KEY_PIN_TO_CONFIRM)!!
                viewModel.setModeConfirm(pinToConfirm)
            }
        }

        val indicators = listOf(
            binding.imageDigitIndicator1,
            binding.imageDigitIndicator2,
            binding.imageDigitIndicator3,
            binding.imageDigitIndicator4
        )
        val backgroundIndicatorChecked = ResourcesCompat.getDrawable(resources, R.drawable.background_digit_indicator_checked, theme)
        val backgroundIndicatorUnchecked = ResourcesCompat.getDrawable(resources, R.drawable.background_digit_indicator_unchecked, theme)
        viewModel.liveDataPIN.observe(this) { pin ->
            for ((i, indicator) in indicators.withIndex()) {
                if (i < pin.length) {
                    indicator.background = backgroundIndicatorChecked
                }
                else {
                    indicator.background = backgroundIndicatorUnchecked
                }
            }

            if (pin.length == 4) {
                viewModel.checkPIN(this)
            }
        }
        viewModel.liveDataLoginText.observe(this) { text ->
            binding.textLogin.text = text
        }

        val buttonDigits = listOf(
            binding.buttonDigit1,
            binding.buttonDigit2,
            binding.buttonDigit3,
            binding.buttonDigit4,
            binding.buttonDigit5,
            binding.buttonDigit6,
            binding.buttonDigit7,
            binding.buttonDigit8,
            binding.buttonDigit9,
            binding.buttonDigit0,
        ).forEach { buttonDigit ->
            buttonDigit.setOnClickListener {
                viewModel.liveDataPIN.value += buttonDigit.text
            }
        }

        binding.buttonClear.setOnClickListener {
            viewModel.liveDataPIN.value = ""
        }

        binding.buttonFingerprint.setOnClickListener {
            // TODO: fingerprint login
            showToast("Fingerprint login feature is coming soon!")
        }

        binding.buttonBack.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }


    }
}