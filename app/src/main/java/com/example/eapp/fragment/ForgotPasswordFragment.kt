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
import com.example.eapp.databinding.FragmentForgotPasswordBinding
import com.example.eapp.util.SessionManager
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment : Fragment() {
    private var _binding: FragmentForgotPasswordBinding? = null
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
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()

        sessionManager = SessionManager(activity as Context)


        binding.btnReset.setOnClickListener {

            val email: String = binding.etEmail.text.toString()
            if(email.isEmpty()){
                Toast.makeText(context, "Please enter email address", Toast.LENGTH_SHORT).show()
            }else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener {task->
                        if(task.isSuccessful){
                            Toast.makeText(context, "Email sent successfully you can reset your password", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
                        }else{
                            Toast.makeText(context, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()

                        }

                    }
            }

        }


        return view
    }
}