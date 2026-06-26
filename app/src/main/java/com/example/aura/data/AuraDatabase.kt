package com.example.aura.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [JournalEntry::class], version = 1, exportSchema = false)
abstract class AuraDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao

    companion object {
        @Volatile
        private var Instance: AuraDatabase? = null

        fun getDatabase(context: Context): AuraDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AuraDatabase::class.java, "aura_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
