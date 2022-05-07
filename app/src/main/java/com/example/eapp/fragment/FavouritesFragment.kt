package com.example.eapp.fragment

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.eapp.R
import com.example.eapp.adapter.AllGiftsAdapter
import com.example.eapp.database.GiftDatabase
import com.example.eapp.database.GiftEntity
import com.example.eapp.databinding.FragmentFavouritesBinding
import com.example.eapp.databinding.FragmentHomeBinding
import com.example.eapp.model.Gift
import com.example.eapp.util.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var giftList = arrayListOf<Gift>()
    private var searchGiftList = arrayListOf<Gift>()

    lateinit var sessionManager: SessionManager
    lateinit var recyclerAdapter: AllGiftsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        val view = binding.root


        sessionManager = SessionManager(context as Activity)
        sessionManager.setFavFrag(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.toolbar.toolbar.title = "WishList"


        binding.bottomNavigationView.selectedItemId = R.id.fav
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            sessionManager.setFavFrag(false)
            when(it.itemId){

                R.id.home->findNavController().navigate(R.id.action_favouritesFragment_to_homeFragment)
                R.id.settings->findNavController().navigate(R.id.action_favouritesFragment_to_settingsFragment)

            }
            true
        }

        binding.rlLoading.visibility = View.VISIBLE
        setUpRecycler(view)
    }

    private fun setUpRecycler(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)


        /*In case of favourites, simply extract all the data from the DB and send to the adapter.
        * Here we can reuse the adapter created for the home fragment. This will save our time and optimize our app as well*/
        val backgroundList = FavouritesAsync(activity as Context).execute().get()
        if (backgroundList.isEmpty()) {
            binding.rlLoading.visibility = View.GONE
            binding.recyclerView.visibility = View.GONE
            binding.bottomNavigationView.visibility = View.VISIBLE
            binding.rlNoFavorites.visibility = View.VISIBLE
        } else {
            binding.rlFavorites.visibility = View.VISIBLE
            binding.rlLoading.visibility = View.GONE
            binding.rlNoFavorites.visibility = View.GONE
            for (i in backgroundList) {
                giftList.add(
                    Gift(
                        i.id.toString(),
                        i.imageUrl,
                        i.caption
                    )
                )
            }

            recyclerAdapter = AllGiftsAdapter(activity as Context,giftList,searchGiftList)
            val mLayoutManager = GridLayoutManager(activity,2)
            recyclerView.layoutManager = mLayoutManager
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = recyclerAdapter
        }

    }


    /*A new async class for fetching the data from the DB*/
    class FavouritesAsync(context: Context) : AsyncTask<Void, Void, List<GiftEntity>>() {

        val db = Room.databaseBuilder(context, GiftDatabase::class.java, "gift-db").build()

        override fun doInBackground(vararg params: Void?): List<GiftEntity> {

            return db.GiftDao().getAllGifts()
        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
