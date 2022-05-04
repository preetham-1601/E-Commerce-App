package com.example.eapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class normFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_norm, container, false)



    val queue = Volley.newRequestQueue(activity as Context)
    val url ="https://v1.nocodeapi.com/preetham/instagram/uhgCkdLeWpUJCgfJ"
    val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET,url,null,Response.Listener {
        println("Response is $it")
    },Response.ErrorListener {
        println("Error is $it")
    }){
        override fun getHeaders(): MutableMap<String, String> {
            val headers = HashMap<String, String>()
            headers["Content-type"] = "application/json"

            /*The below used token will not work, kindly use the token provided to you in the training*/
            headers["token"] = "IGQVJVdS1RbXBENmhqYmprcldPUGlSOU0wMHg2QW9xYjU5aU92NXFIWVdyQWNCREJRX0xZAREIzZAnFTZAmt3UHRYRi1jZAXo1U0ZAkei1pZADQxT1Vld2QydFplVDlONlFqZAWpLWkZAHM2FzanN1WUhrUzNPQgZDZD"
            return headers
        }
    }

        queue.add(jsonObjectRequest)

        return view
    }
}