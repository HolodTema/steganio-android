package com.terabyte.steganio.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.terabyte.steganio.databinding.FragmentTextDecryptBinding

class TextDecryptFragment: Fragment() {
    private lateinit var binding: FragmentTextDecryptBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTextDecryptBinding.inflate(layoutInflater)

        return binding.root
    }
}