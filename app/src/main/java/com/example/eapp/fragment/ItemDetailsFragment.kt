package com.example.eapp.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.eapp.R
import com.example.eapp.databinding.FragmentItemDetailsBinding
import com.example.eapp.model.Gift
import com.example.eapp.util.SessionManager
import com.squareup.picasso.Picasso

class ItemDetailsFragment : Fragment() {
    private var _binding: FragmentItemDetailsBinding? = null
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
        _binding = FragmentItemDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        sessionManager = SessionManager(context as Activity)
        val dun =arguments?.getString("caption")
        binding.txtView.text = dun
        val bun = arguments?.getString("image_url")
        Picasso.get().load(bun).into(binding.imageView);

        binding.btnShare.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.instagram.com/p/CdNKEFrpDXc/")
            startActivity(openURL)
        }

        //https://www.instagram.com/p/CdNKEFrpDXc/
        binding.toolbar.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.toolbar.title = "Item Details"

        binding.toolbar.toolbar.setNavigationOnClickListener { view ->
            sessionManager.setFavFrag(false)
            findNavController().navigate(R.id.action_itemDetailsFragment_to_homeFragment)
        }

        binding.btnCrt.setOnClickListener {

            val bundle = bundleOf("image_url" to bun,"caption" to dun)
            findNavController().navigate(R.id.action_itemDetailsFragment_to_cartFragment,bundle)
        }







        return view
    }
}