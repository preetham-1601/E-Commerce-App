package com.example.eapp.fragment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.eapp.R
import com.example.eapp.databinding.FragmentPaymentsBinding
import com.example.eapp.util.SessionManager
import com.squareup.picasso.Picasso

class PaymentsFragment : Fragment() {

    val GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user"
    var GOOGLE_PAY_REQUEST_CODE = 123
    var name = "Highbrow Director"
    var upiId = "preetham.kasturi16@okhdfcbank"
    var transactionNote = "pay test"
    var amount = "300"
    var status: String? = null
    var uri: Uri? = null

    private var _binding: FragmentPaymentsBinding? = null
    private val binding get() = _binding!!
    lateinit var sessionManager: SessionManager

    private fun isAppInstalled(context: Context, packageName: String): Boolean {
        return try {
            context.packageManager.getApplicationInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun getUpiPaymentUri(
        name: String,
        upiId: String,
        transactionNote: String,
        amount: String
    ): Uri? {
        return Uri.Builder()
            .scheme("upi")
            .authority("pay")
            .appendQueryParameter("pa", upiId)
            .appendQueryParameter("pn", name)
            .appendQueryParameter("tn", transactionNote)
            .appendQueryParameter("am", amount)
            .appendQueryParameter("cu", "INR")
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        val view = binding.root

        sessionManager = SessionManager(context as Activity)

        val sun = arguments?.getString("caption")

        val bun = arguments?.getString("image_url")

        binding.toolbar.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.toolbar.title = "Payments"

        amount = sun?.substring(10,13).toString()
        binding.rate.text = amount

        binding.idItem.text = sun
        Picasso.get().load(bun).into(binding.idImage)

        binding.toolbar.toolbar.setNavigationOnClickListener { view ->
            val bundle = bundleOf("caption" to sun, "image_url" to bun)
            findNavController().navigate(R.id.action_paymentsFragment_to_cartFragment, bundle)
        }


        binding.googlePayButton.setOnClickListener {
            if (amount!!.isNotEmpty()) {
                uri = getUpiPaymentUri(name, upiId, transactionNote, amount)
                payWithGPay()
            } else {
                Toast.makeText(context, "bus", Toast.LENGTH_SHORT).show()
            } }

        return view
    }


    private fun payWithGPay() {
        if (isAppInstalled(context as Activity ,GOOGLE_PAY_PACKAGE_NAME)) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = uri
            intent.setPackage(GOOGLE_PAY_PACKAGE_NAME)
            startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE)
        } else {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = uri
            intent.setPackage(GOOGLE_PAY_PACKAGE_NAME)
            startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE)
            Toast.makeText(context, "Please Install GPay", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            status = data.getStringExtra("Status")!!.toLowerCase()
        }
        if (RESULT_OK == resultCode && status == "success") {
            Toast.makeText(context, "Transaction Successful", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_paymentsFragment_to_placeOrderFragment)
            onDestroy()

        } else {
            Toast.makeText(context, "Transaction Failed", Toast.LENGTH_SHORT).show()
            val sun = arguments?.getString("caption")
            val bun = arguments?.getString("image_url")
            val bundle = bundleOf("caption" to sun, "image_url" to bun)
            findNavController().navigate(R.id.action_paymentsFragment_to_cartFragment, bundle)
        }
    }
}



