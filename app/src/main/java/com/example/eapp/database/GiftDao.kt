package com.example.eapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/* Dao for accessing the data present inside the DB*/

@Dao
interface GiftDao{

    @Insert
    fun insertGift(giftEntity: GiftEntity)

    @Delete
    fun deleteGift(giftEntity: GiftEntity)

    @Query("SELECT * FROM gifts")
    fun getAllGifts(): List<GiftEntity>

    @Query("SELECT * FROM gifts WHERE id = :giftId")
    fun getGiftById(giftId: String): GiftEntity
}