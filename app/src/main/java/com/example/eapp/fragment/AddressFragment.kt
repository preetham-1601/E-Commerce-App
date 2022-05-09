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
import com.example.eapp.databinding.FragmentAddressBinding
import com.example.eapp.util.SessionManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddressFragment : Fragment() {
    private var _binding: FragmentAddressBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentAddressBinding.inflate(inflater, container, false)
        val view = binding.root

        sessionManager = SessionManager(context as Activity)
        val uid = sessionManager.getUid()
        val sun = arguments?.getString("caption")
        val bun = arguments?.getString("image_url")

        readData(uid.toString())
        binding.toolbar.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.toolbar.title = "Address"

        binding.toolbar.toolbar.setNavigationOnClickListener { view ->
            val bundle = bundleOf("caption" to sun,"image_url" to bun)
            findNavController().navigate(R.id.action_addressFragment_to_cartFragment,bundle)
        }




        sessionManager = SessionManager(activity as Context)
        binding.btnPay.setOnClickListener {

            val bundle = bundleOf("caption" to sun,"image_url" to bun)
            findNavController().navigate(R.id.action_addressFragment_to_paymentsFragment,bundle)

        }
        binding.btnAdd.setOnClickListener {
            val bundle = bundleOf("caption" to sun,"image_url" to bun)

            findNavController().navigate(R.id.action_addressFragment_to_addNewAddressFragment,bundle)
        }



        return view
    }

    private fun readData(uid: String) {


        database = FirebaseDatabase.getInstance().getReference("NewUsers")
        database.child("user").child(uid).get().addOnSuccessListener {
            if(true){

                val name =it.child("name").value
                val address =it.child("address").value
                val pinCode =it.child("pinCode").value
                val city =it.child("city").value
                val state =it.child("state").value

                binding.name.text = name.toString()
                binding.check.text = address.toString()
                binding.txt2.text = pinCode.toString()
                binding.city.text = city.toString()
                binding.state.text = state.toString()

            }
            else{
                Toast.makeText(context, "no $uid", Toast.LENGTH_SHORT).show()

            }
        }.addOnFailureListener{
            Toast.makeText(context, "Failed $uid", Toast.LENGTH_SHORT).show()
        }


    }
}