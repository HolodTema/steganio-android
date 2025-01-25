package com.terabyte.steganio.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.terabyte.steganio.databinding.FragmentTextEncryptBinding

class TextEncryptFragment: Fragment() {
    private lateinit var binding: FragmentTextEncryptBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTextEncryptBinding.inflate(layoutInflater)
        return binding.root
    }
}