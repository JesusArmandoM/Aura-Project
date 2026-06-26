package com.example.aura.data

import kotlinx.coroutines.flow.Flow

class JournalRepository(private val journalDao: JournalDao) {
    fun getAllEntriesStream(): Flow<List<JournalEntry>> = journalDao.getAllEntries()

    suspend fun getEntryById(id: Long): JournalEntry? = journalDao.getEntryById(id)

    suspend fun insertEntry(entry: JournalEntry) = journalDao.insertEntry(entry)

    suspend fun deleteEntry(entry: JournalEntry) = journalDao.deleteEntry(entry)
}
