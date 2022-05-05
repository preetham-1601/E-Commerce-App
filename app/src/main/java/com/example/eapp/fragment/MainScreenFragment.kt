package com.example.eapp.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
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




        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (sessionManager.isLoggedIn()) {
            binding.btnGet.visibility = View.GONE
            Handler().postDelayed({
                findNavController().navigate(R.id.action_mainScreenFragment_to_homeFragment)
            }, 2000)


        } else {
            binding.btnGet.setOnClickListener {

                findNavController().navigate(R.id.action_mainScreenFragment_to_loginFragment)
                //findNavController().navigate(R.id.action_mainScreenFragment_to_loginFragment)
            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}