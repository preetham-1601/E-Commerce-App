package com.example.eapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gifts")
data class GiftEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "caption") val caption: String
)