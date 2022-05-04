package com.example.eapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.eapp.R
import com.example.eapp.databinding.FragmentItemDetailsBinding
import com.example.eapp.model.Gift
import com.squareup.picasso.Picasso

class ItemDetailsFragment : Fragment() {
    private var _binding: FragmentItemDetailsBinding? = null
    private val binding get() = _binding!!
    private var searchGiftList = arrayListOf<Gift>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailsBinding.inflate(inflater, container, false)
        val view = binding.root



        binding.txtView.text = arguments?.getString("caption")
        val bun = arguments?.getString("image_url")
        Picasso.get().load(bun).into(binding.imageView);


        binding.btnCust.setOnClickListener {
            findNavController().navigate(R.id.action_itemDetailsFragment_to_customizationFragment)
        }


        return view
    }
}