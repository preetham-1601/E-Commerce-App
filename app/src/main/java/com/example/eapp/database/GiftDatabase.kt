package com.example.eapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GiftEntity::class,], version = 1, exportSchema = false)
abstract class GiftDatabase : RoomDatabase() {

    abstract fun GiftDao(): GiftDao


}
