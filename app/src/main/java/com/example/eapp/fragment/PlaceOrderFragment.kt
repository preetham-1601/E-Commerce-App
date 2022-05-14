package com.example.eapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.eapp.R
import com.example.eapp.databinding.FragmentPlaceOrderBinding

class PlaceOrderFragment : Fragment() {
    private var _binding: FragmentPlaceOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaceOrderBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btn.setOnClickListener {

            findNavController().navigate(R.id.action_placeOrderFragment_to_homeFragment)
        }
        return view
    }

}