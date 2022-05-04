package com.example.eapp.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.eapp.R
import com.example.eapp.databinding.FragmentRegisterBinding
import com.example.eapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        // Initialising auth object
        auth = Firebase.auth


        binding.btnRegister.setOnClickListener {
            registerUser()

        }




        return view
    }

    private fun registerUser() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val mobileNumber = binding.etMob.text.toString()
        val address = binding.etAddr.text.toString()
        val city = binding.etCity.text.toString()
        val state = binding.etState.text.toString()
        val pinCode = binding.etPin.text.toString()
        val pass = binding.etPass.text.toString()
        val confirmPassword = binding.etConfPass.text.toString()



        // check pass
        if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(activity as Context, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        if (pass != confirmPassword) {
            Toast.makeText(activity as Context, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return
        }
        // If all credential are correct
        // We call createUserWithEmailAndPassword
        // using auth object and pass the
        // email and pass in it.
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(requireActivity()) {task ->
            if (task.isSuccessful) {
                Toast.makeText(activity as Context, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                database= FirebaseDatabase.getInstance().getReference("Users")
                val user = User(name,email,mobileNumber,address,city,state,pinCode,pass)

                database.child(name).setValue(user)
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            } else {
                Toast.makeText(activity as Context, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}