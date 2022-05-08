package com.example.eapp.fragment

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
import com.example.eapp.databinding.FragmentAddressBinding
import com.example.eapp.util.SessionManager

class AddressFragment : Fragment() {
    private var _binding: FragmentAddressBinding? = null
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
        _binding = FragmentAddressBinding.inflate(inflater, container, false)
        val view = binding.root

        val sun = arguments?.getString("caption")

        val bun = arguments?.getString("image_url")

        binding.toolbar.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.toolbar.title = "Address"

        binding.toolbar.toolbar.setNavigationOnClickListener { view ->
            val bundle = bundleOf("caption" to sun,"image_url" to bun)
            findNavController().navigate(R.id.action_addressFragment_to_cartFragment,bundle)
        }




        sessionManager = SessionManager(activity as Context)
        binding.btnPay.setOnClickListener {

            val bundle = bundleOf("caption" to sun,"image_url" to bun)
            findNavController().navigate(R.id.action_addressFragment_to_addNewAddressFragment,bundle)

        }
        binding.btnAdd.setOnClickListener {
            val bundle = bundleOf("caption" to sun,"image_url" to bun)

            findNavController().navigate(R.id.action_addressFragment_to_addNewAddressFragment,bundle)
        }



        return view
    }
}