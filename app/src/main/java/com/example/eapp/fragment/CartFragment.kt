package com.example.eapp.fragment

import android.app.Activity
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.android.volley.toolbox.Volley
import com.example.eapp.R
import com.example.eapp.databinding.FragmentCartBinding
import com.example.eapp.util.SessionManager
import com.squareup.picasso.Picasso


class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
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
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root

        sessionManager = SessionManager(context as Activity)


        binding.toolbar.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.toolbar.title = "Cart"

        binding.toolbar.toolbar.setNavigationOnClickListener { view ->
            val builder = AlertDialog.Builder(context as Activity)
            builder.setTitle("Confirmation")
                .setMessage("If you leave the screen the cart items will be removed")
                .setPositiveButton("Yes") { _, _ ->

                    findNavController().navigate(R.id.action_cartFragment_to_homeFragment)

                    Volley.newRequestQueue(context as Activity).cancelAll(this::class.java.simpleName)
                    ActivityCompat.finishAffinity(context as Activity)
                }
                .setNegativeButton("No") { _, _ ->

                }
                .create()
                .show()

        }

        binding.can.setOnClickListener {
            val builder = AlertDialog.Builder(context as Activity)
            builder.setTitle("Confirmation")
                .setMessage("Cancel items in cart")
                .setPositiveButton("Yes") { _, _ ->

                    findNavController().navigate(R.id.action_cartFragment_to_homeFragment)

                    Volley.newRequestQueue(context as Activity).cancelAll(this::class.java.simpleName)
                    ActivityCompat.finishAffinity(context as Activity)
                }
                .setNegativeButton("No") { _, _ ->

                }
                .create()
                .show()

        }
        binding.btnCrt.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_addressFragment)
        }

        binding.btnCust.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_customizationFragment)
        }

        binding.idItem.text = arguments?.getString("caption")
        val bun = arguments?.getString("image_url")
        Picasso.get().load(bun).into(binding.idImage);





        return view
    }
}