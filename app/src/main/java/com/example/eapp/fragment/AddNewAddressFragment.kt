package com.example.eapp.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.eapp.R
import com.example.eapp.databinding.FragmentAddNewAddressBinding
import com.example.eapp.util.SessionManager


class AddNewAddressFragment : Fragment() {
    private var _binding: FragmentAddNewAddressBinding? = null
    private val binding get() = _binding!!
    lateinit var sessionManager: SessionManager


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

        sessionManager = SessionManager(context as Activity)

        val sun = arguments?.getString("caption")

        val bun = arguments?.getString("image_url")

        binding.toolbar.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.toolbar.title = "Address"

        binding.toolbar.toolbar.setNavigationOnClickListener { view ->
            val bundle = bundleOf("caption" to sun,"image_url" to bun)
            findNavController().navigate(R.id.action_addressFragment_to_cartFragment,bundle)
        }


        binding.btnAddad.setOnClickListener {
            val bundle = bundleOf("caption" to sun,"image_url" to bun)
            Toast.makeText(
                activity as Context,
                "Added New Address",
                Toast.LENGTH_SHORT
            ).show()

            findNavController().navigate(R.id.action_addNewAddressFragment_to_addressFragment,bundle)
        }





        return view
    }
}