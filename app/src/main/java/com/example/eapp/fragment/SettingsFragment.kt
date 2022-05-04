package com.example.eapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.eapp.R
import com.example.eapp.adapter.AllGiftsAdapter
import com.example.eapp.databinding.FragmentFavouritesBinding
import com.example.eapp.databinding.FragmentSettingsBinding
import com.example.eapp.model.Gift
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var giftList = arrayListOf<Gift>()
    private var searchGiftList = arrayListOf<Gift>()

    lateinit var recyclerAdapter: AllGiftsAdapter

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



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}