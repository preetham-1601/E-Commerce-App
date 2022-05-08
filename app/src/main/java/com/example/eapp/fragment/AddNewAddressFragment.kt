package com.example.eapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eapp.R
import com.example.eapp.databinding.FragmentAddNewAddressBinding


class AddNewAddressFragment : Fragment() {
    private var _binding: FragmentAddNewAddressBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNewAddressBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
}