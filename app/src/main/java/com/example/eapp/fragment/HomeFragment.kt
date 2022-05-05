package com.example.eapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.eapp.R
import com.example.eapp.adapter.AllGiftsAdapter
import com.example.eapp.model.Gift
import kotlin.collections.HashMap
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.eapp.databinding.FragmentHomeBinding
import java.util.Locale.filter


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var giftList = arrayListOf<Gift>()
    private var giftListFilter = arrayListOf<Gift>()
    private lateinit var navController: NavController

    lateinit var recyclerAdapter: AllGiftsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.bottomNavigationView.selectedItemId = R.id.home

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){

                R.id.fav->findNavController().navigate(R.id.action_homeFragment_to_favouritesFragment)
                R.id.settings->findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)

            }
            true
        }



        val queue = Volley.newRequestQueue(activity as Context)
        val url = "https://v1.nocodeapi.com/preetham/instagram/BDNRRhYYzNySAFHa"

        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET,
            url,
            null,
            Response.Listener
            {
            val data = it.getJSONArray("data")
                for(i in 0 until  data.length()) {
                    val giftJsonObject = data.getJSONObject(i)
                    val giftObject = Gift(
                        giftJsonObject.getString("id"),
                        giftJsonObject.getString("media_url"),
                        giftJsonObject.getString("caption")
                    )
                    giftList.add(giftObject)



                }
                recyclerView = binding.recyclerView
                recyclerAdapter = AllGiftsAdapter(activity as Context,giftList,giftListFilter)
                //recyclerAdapter!!.setData(giftList)
                recyclerView.layoutManager = GridLayoutManager(context, 2)
                recyclerView.adapter = recyclerAdapter




            },
            Response.ErrorListener {
                println("Error is $it")
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-type"] = "application/json"
                headers["token"] = "IGQVJVaHZAiVGhzajVWdVFPeTdNTGdiQlZA2emJsZA20tRVNNQlhQWUdtdW1JbHNpd0ZACTzFBcUVseFZAOcTF6QTVRWmtFdXFBVTQ0eEg0TWFUbmZAvVHdVV1Vqa3ppOXE0RG5TX25LLXdsM0VMSV9TWlJvMQZDZD"
                return headers
            }
        }
        queue.add(jsonObjectRequest)





    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }






    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.options_menu, menu)

        val menuItem = menu!!.findItem(R.id.search)

        val searchView = menuItem.actionView as SearchView

        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(filterString: String?): Boolean {
                recyclerAdapter.filter.filter(filterString)

                Toast.makeText(
                    activity as Context,
                    "no",
                    Toast.LENGTH_SHORT
                ).show()
                return true
            }

            override fun onQueryTextChange(filterString: String?): Boolean {
                recyclerAdapter.filter.filter(filterString)

                Toast.makeText(
                    activity as Context,
                    "Successfully LoggedIn",
                    Toast.LENGTH_SHORT
                ).show()
                return true

            }


        })





        /*searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                searchGiftList.clear()

                val searchText = newText!!.lowercase(Locale.getDefault())

                if(searchText.isNotEmpty()){
                    searchGiftList.forEach{
                        if(it.giftCaption.lowercase(Locale.getDefault()).contains(searchText)){
                            searchGiftList.add(it)
                        }
                    }
                    recyclerView.adapter!!.notifyDataSetChanged()
                }else{

                    Toast.makeText(activity as Context, "No ", Toast.LENGTH_SHORT)
                        .show()
                    searchGiftList.clear()
                    searchGiftList.addAll(giftList)
                    recyclerView.adapter!!.notifyDataSetChanged()


                }

                return false
            }

        })

        return super.onCreateOptionsMenu(menu, inflater)*/


    }





}

