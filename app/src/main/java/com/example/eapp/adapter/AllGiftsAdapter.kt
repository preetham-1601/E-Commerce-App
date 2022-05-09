package com.example.eapp.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.toolbox.Volley
import com.example.eapp.R
import com.example.eapp.database.GiftDatabase
import com.example.eapp.database.GiftEntity
import com.example.eapp.fragment.HomeFragmentDirections
import com.example.eapp.fragment.LoginFragment
import com.example.eapp.model.Gift
import com.example.eapp.util.SessionManager
import com.squareup.picasso.Picasso

class AllGiftsAdapter(private val context: Context, private var gifts: ArrayList<Gift>,
                      private var giftsFilter: ArrayList<Gift>
) : RecyclerView.Adapter<AllGiftsAdapter.ItemViewHolder>(),Filterable {


    lateinit var sessionManager: SessionManager
    fun setData(gifts: ArrayList<Gift>){
        this.gifts = gifts
        this.giftsFilter = giftsFilter
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        sessionManager = SessionManager(context)
        val gift = gifts[position]
        //holder.idImage.setImageResource(gift.giftImage)
        Picasso.get().load(gift.giftImage).into(holder.idImage);
        holder.idItem.text=gift.giftCaption


        val listOfFavourites = GetAllFavAsyncTask(context).execute().get()

        if (listOfFavourites.isNotEmpty() && listOfFavourites.contains(gift.giftId.toString())) {
            holder.favImage.setImageResource(R.drawable.ic_action_fav_checked)
        } else {
            holder.favImage.setImageResource(R.drawable.ic_action_fav)
        }

        holder.favImage.setOnClickListener {
            if (sessionManager.isLoggedIn()){
                    val giftEntity = GiftEntity(
                        gift.giftId,
                        gift.giftImage,
                        gift.giftCaption,

                        )

                    if (!DBAsyncTask(context, giftEntity, 1).execute().get()) {
                        val async =
                            DBAsyncTask(context, giftEntity, 2).execute()
                        val result = async.get()
                        if (result) {
                            holder.favImage.setImageResource(R.drawable.ic_action_fav_checked)
                        }
                    } else {
                        val async = DBAsyncTask(context, giftEntity, 3).execute()
                        val result = async.get()

                        if (result) {
                            holder.favImage.setImageResource(R.drawable.ic_action_fav)
                        }
                    }
            } else {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Confirmation")
                    .setMessage("Login to Use Your WishList")
                    .setPositiveButton("Login") { _, _ ->
                        sessionManager.setLogin(false)
                        findNavController(holder.idItem).navigate(R.id.action_homeFragment_to_loginFragment)

                    }
                    .setNegativeButton("No") { _, _ ->

                    }
                    .create()
                    .show()
            }


        }

        holder.llContent.setOnClickListener {
            if(sessionManager.isFav()){
                val uid = sessionManager.getUid()
                val bundle = bundleOf("image_url" to gift.giftImage,"caption" to gift.giftCaption,"uid" to uid)
                sessionManager.setFavFrag(false)
                findNavController(holder.idItem).navigate(R.id.action_favouritesFragment_to_itemDetailsFragment,bundle)
            }else{
                val uid = sessionManager.getUid()

                val bundle = bundleOf("image_url" to gift.giftImage,"caption" to gift.giftCaption,"uid" to uid)
                findNavController(holder.idItem).navigate(R.id.action_homeFragment_to_itemDetailsFragment,bundle)
            }
        }


    }

    override fun getItemCount(): Int {
        return gifts.size
    }

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val idImage: ImageView = view.findViewById(R.id.id_image)
        val idItem: TextView = view.findViewById(R.id.id_item)
        val llContent: CardView = view.findViewById(R.id.llContent)
        val favImage: ImageView = view.findViewById(R.id.imgIsFav)

    }

    class DBAsyncTask(context: Context, val giftEntity: GiftEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {

        val db = Room.databaseBuilder(context, GiftDatabase::class.java, "gift-db").build()

        override fun doInBackground(vararg params: Void?): Boolean {

            /*
            Mode 1 -> Check DB if the book is favourite or not
            Mode 2 -> Save the book into DB as favourite
            Mode 3 -> Remove the favourite book
            */

            when (mode) {

                1 -> {
                    val gif: GiftEntity? =
                        db.GiftDao().getGiftById(giftEntity.id)
                    db.close()
                    return gif != null
                }

                2 -> {
                    db.GiftDao().insertGift(giftEntity)
                    db.close()
                    return true
                }

                3 -> {
                    db.GiftDao().deleteGift(giftEntity)
                    db.close()
                    return true
                }
            }

            return false
        }

    }


    /*Since the outcome of the above background method is always a boolean, we cannot use the above here.
    * We require the list of favourite restaurants here and hence the outcome would be list.
    * For simplicity we obtain the list of restaurants and then extract their ids which is then compared to the ids
    * inside the list sent to the adapter */

    class GetAllFavAsyncTask(
        context: Context
    ) :
        AsyncTask<Void, Void, List<String>>() {

        val db = Room.databaseBuilder(context, GiftDatabase::class.java, "gift-db").build()
        override fun doInBackground(vararg params: Void?): List<String> {

            val list = db.GiftDao().getAllGifts()
            val listOfIds = arrayListOf<String>()
            for (i in list) {
                listOfIds.add(i.id)
            }
            return listOfIds
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {

                val filterResult = FilterResults()
                if(charSequence == null|| charSequence.length<0){


                    filterResult.count= giftsFilter.size
                    filterResult.values= giftsFilter

                }else{


                    var searchChr = charSequence.toString().lowercase()
                    Toast.makeText(
                        context,
                        "$searchChr",
                        Toast.LENGTH_SHORT
                    ).show()
                    val itemModel = ArrayList<Gift>()
                    for(item in gifts){

                        if(item.giftCaption.toString().lowercase().contains(searchChr)){
                            itemModel.add(item)


                        }
                    }
                    filterResult.count =itemModel.size

                    filterResult.values =itemModel

                }
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                gifts = filterResults!!.values as ArrayList<Gift>


                notifyDataSetChanged()

            }


        }
    }


}