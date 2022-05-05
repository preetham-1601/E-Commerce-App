package com.example.eapp.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.eapp.R
import com.example.eapp.adapter.AllGiftsAdapter
import com.example.eapp.databinding.FragmentFavouritesBinding
import com.example.eapp.databinding.FragmentSettingsBinding
import com.example.eapp.model.Gift
import com.example.eapp.util.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth
    lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        sessionManager = SessionManager(context as Activity)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.bottomNavigationView.selectedItemId = R.id.settings
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){

                R.id.home->findNavController().navigate(R.id.action_settingsFragment_to_homeFragment)
                R.id.fav->findNavController().navigate(R.id.action_settingsFragment_to_favouritesFragment)

            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout_menu, menu)
        return super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_logout){
            mAuth = FirebaseAuth.getInstance()

            mAuth.signOut()
            sessionManager.setLogin(false)
            findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
            onDestroy()
            return true
        }
        return true
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}