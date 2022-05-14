package com.example.eapp.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth
    lateinit var sessionManager: SessionManager
    lateinit var database: DatabaseReference


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

        binding.toolbar.toolbar.inflateMenu(R.menu.logout_menu)
        binding.toolbar.toolbar.title = "Settings"

        val uid = sessionManager.getUid()

        binding.toolbar.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_logout ->{
                    mAuth = FirebaseAuth.getInstance()
                    val builder = AlertDialog.Builder(context as Activity)
                    builder.setTitle("Logout")
                        .setMessage("Do you want to Logout?")
                        .setPositiveButton("Logout") { _, _ ->
                            mAuth.signOut()
                            sessionManager.setLogin(false)
                            findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
                        }
                        .setNegativeButton("No") { _, _ ->

                        }
                        .create()
                        .show()
                    true
                }
                else -> false
            }

        }

        readData(uid.toString())



        binding.bottomNavigationView.selectedItemId = R.id.settings
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){

                R.id.home->{

                    findNavController().navigate(R.id.action_settingsFragment_to_homeFragment)}
                R.id.fav->{

                    findNavController().navigate(R.id.action_settingsFragment_to_favouritesFragment)
                }

            }
            true
        }
    }

    private fun readData(uid: String) {


        database = FirebaseDatabase.getInstance().getReference("NewUsers")
        database.child("user").child(uid).get().addOnSuccessListener {
            if(true){

                val name =it.child("name").value
                val email =it.child("email").value
                val mobileNumber =it.child("mobileNumber").value

                binding.txt1.text = "Hii!! ${name.toString()}"
                binding.txt2.text = email.toString()
                binding.txt3.text = mobileNumber.toString()

            }
            else{
                Toast.makeText(context, "no $uid", Toast.LENGTH_SHORT).show()

            }
        }.addOnFailureListener{
            Toast.makeText(context, "Failed $uid", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout_menu, menu)
        return super.onCreateOptionsMenu(menu,inflater)
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}