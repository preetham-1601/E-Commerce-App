package com.example.eapp.fragment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.eapp.R
import com.example.eapp.databinding.FragmentCustomizationBinding
import com.example.eapp.util.SessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

class CustomizationFragment : Fragment() {
    private var _binding: FragmentCustomizationBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    lateinit var sessionManager: SessionManager

    lateinit var ImageUri : Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomizationBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()

        binding.toolbar.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.toolbar.title = "Customization"

        val sun = arguments?.getString("caption")

        val bun = arguments?.getString("image_url")

        binding.toolbar.toolbar.setNavigationOnClickListener { view ->
            val bundle = bundleOf("caption" to sun,"image_url" to bun)
            findNavController().navigate(R.id.action_customizationFragment_to_cartFragment,bundle)
        }



        sessionManager = SessionManager(activity as Context)
        binding.btnUp.setOnClickListener {

            selectImage()
        }
        binding.reset.setOnClickListener {
            binding.editTextTextPersonName.text.clear()
        }
        binding.save.setOnClickListener {

            val bundle = bundleOf("caption" to sun,"image_url" to bun)
            Toast.makeText(activity as Context, "Items saved", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_customizationFragment_to_cartFragment,bundle)
            uplodeImage()
        }




        return view
    }


    fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent,100)

    }
    fun uplodeImage(){

        val formatter = SimpleDateFormat("yyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName =formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

        storageReference.putFile(ImageUri).addOnSuccessListener{

            binding.imgUpld.setImageURI(null)
            Toast.makeText(context,"Successful Upload",Toast.LENGTH_LONG).show()

        }.addOnFailureListener{


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            ImageUri = data?.data!!
            binding.imgUpld.setImageURI(ImageUri)

        }
    }
}