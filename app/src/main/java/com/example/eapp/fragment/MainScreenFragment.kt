package com.example.eapp.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.findNavController
import com.example.eapp.R
import com.example.eapp.databinding.FragmentMainScreenBinding
import com.example.eapp.util.SessionManager


class MainScreenFragment : Fragment() {
    private var _binding: FragmentMainScreenBinding? = null
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
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        val view = binding.root


        sessionManager = SessionManager(activity as Context)

        binding.btnGet.setOnClickListener {

            //openNewActivity()
            findNavController().navigate(R.id.action_mainScreenFragment_to_loginFragment)
        }


        return view
    }

    fun openNewActivity() {
        if (sessionManager.isLoggedIn()) {
            findNavController().navigate(R.id.action_mainScreenFragment_to_homeFragment)

        } else {
            findNavController().navigate(R.id.action_mainScreenFragment_to_loginFragment)

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}