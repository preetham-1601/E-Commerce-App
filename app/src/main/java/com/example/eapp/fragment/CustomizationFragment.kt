package com.example.eapp.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.eapp.R
import com.example.eapp.databinding.FragmentCustomizationBinding
import com.example.eapp.util.SessionManager
import com.google.firebase.auth.FirebaseAuth

class CustomizationFragment : Fragment() {
    private var _binding: FragmentCustomizationBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    /*Variables used in managing the login session*/
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomizationBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()



        sessionManager = SessionManager(activity as Context)
        binding.btnUp.setOnClickListener {

            //searchImage()

        }
        binding.reset.setOnClickListener {
            binding.editTextTextPersonName.text.clear()
        }
        binding.save.setOnClickListener {

            //uploadImage()
        }




        return view
    }
}