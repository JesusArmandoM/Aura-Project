package com.example.aura

import android.app.Application
import com.example.aura.data.AuraDatabase
import com.example.aura.data.JournalRepository

class AuraApp : Application() {
    val database by lazy { AuraDatabase.getDatabase(this) }
    val repository by lazy { JournalRepository(database.journalDao()) }
}
