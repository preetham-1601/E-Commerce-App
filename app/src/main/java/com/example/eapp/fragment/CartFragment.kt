package com.example.eapp.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
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



        if(sessionManager.isFav()){
            binding.rlFavorites.visibility = View.VISIBLE
            binding.btm.visibility = View.VISIBLE
            binding.rlNoFavorites.visibility = View.GONE
            val sun = arguments?.getString("caption")
            binding.idItem.text = sun
            val bun = arguments?.getString("image_url")
            Picasso.get().load(bun).into(binding.idImage)

            binding.toolbar.toolbar.setNavigationOnClickListener { view ->
                val builder = AlertDialog.Builder(context as Activity)
                builder.setTitle("Confirmation")
                    .setMessage("If you leave the screen the cart items will be removed")
                    .setPositiveButton("Yes") { _, _ ->

                        findNavController().navigate(R.id.action_cartFragment_to_homeFragment)


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


                    }
                    .setNegativeButton("No") { _, _ ->

                    }
                    .create()
                    .show()

            }
            binding.btnCrt.setOnClickListener {
                val bundle = bundleOf("caption" to sun,"image_url" to bun)
                findNavController().navigate(R.id.action_cartFragment_to_addressFragment,bundle)
            }

            binding.btnCust.setOnClickListener {
                val bundle = bundleOf("caption" to sun,"image_url" to bun)
                findNavController().navigate(R.id.action_cartFragment_to_customizationFragment,bundle)
            }
        }else{
            binding.rlFavorites.visibility = View.GONE
            binding.rlNoFavorites.visibility = View.VISIBLE
            binding.btm.visibility = View.GONE

            binding.toolbar.toolbar.setNavigationOnClickListener { view ->


                        findNavController().navigate(R.id.action_cartFragment_to_homeFragment)


            }
        }










        return view
    }
}