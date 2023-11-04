package com.example.lib_audio.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Music::class], version = 1)
abstract class MusicDatabase: RoomDatabase() {
    abstract fun getMusicDao(): MusicDao
}