package com.terabyte.steganio

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.terabyte.steganio.databinding.ActivityMainBinding
import com.terabyte.steganio.util.PythonHelper
import com.terabyte.steganio.util.showToast
import com.terabyte.steganio.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.editContainer.setText(viewModel.liveDataTextContainer.value)
        binding.editSecret.setText(viewModel.liveDataTextSecret.value)
        binding.editContainer.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.liveDataTextContainer.value = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.editSecret.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.liveDataTextSecret.value = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding.buttonEncrypt.setOnClickListener {
            if (binding.editContainer.text.isEmpty()) {
                showToast("Error: container of encryption is empty!")
            }
            else if (binding.editSecret.text.isEmpty()) {
                showToast("Error: secret text to encrypt is empty!")
            }
            else {
                viewModel.encrypt(
                    binding.editContainer.text.toString(),
                    binding.editSecret.text.toString(),
                    successListener = {

                    },
                    failureListener = {
                        showToast("Error: container length is too short for this secret text.")
                    }
                )
            }
        }

        showToast(PythonHelper.getText())
    }
}