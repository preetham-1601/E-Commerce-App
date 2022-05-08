package com.example.eapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eapp.databinding.FragmentPaymentsBinding

class PaymentsFragment : Fragment() {
    private var _binding: FragmentPaymentsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
}