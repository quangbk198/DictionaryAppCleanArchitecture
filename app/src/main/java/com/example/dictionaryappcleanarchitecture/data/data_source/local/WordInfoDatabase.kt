package com.example.dictionaryappcleanarchitecture.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dictionaryappcleanarchitecture.data.data_source.local.entity.WordInfoEntity

@Database(
    entities = [WordInfoEntity::class],
    version = 1
)
abstract class WordInfoDatabase: RoomDatabase() {
    abstract fun wordInfoDao(): WordInfoDao
}