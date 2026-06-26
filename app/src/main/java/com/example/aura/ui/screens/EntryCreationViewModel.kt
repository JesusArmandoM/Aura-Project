package com.example.aura.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aura.data.JournalEntry
import com.example.aura.data.JournalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

data class EntryCreationUiState(
    val title: String = "",
    val date: Long = Calendar.getInstance().timeInMillis,
    val content: String = "",
    val mood: String = "Neutral",
    val isEntrySaved: Boolean = false
)

class EntryCreationViewModel(private val repository: JournalRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(EntryCreationUiState())
    val uiState: StateFlow<EntryCreationUiState> = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun updateDate(date: Long) {
        _uiState.update { it.copy(date = date) }
    }

    fun updateContent(content: String) {
        _uiState.update { it.copy(content = content) }
    }

    fun updateMood(mood: String) {
        _uiState.update { it.copy(mood = mood) }
    }

    fun saveEntry() {
        val currentState = _uiState.value
        if (currentState.title.isBlank() || currentState.content.isBlank()) return

        viewModelScope.launch {
            repository.insertEntry(
                JournalEntry(
                    title = currentState.title,
                    date = currentState.date,
                    content = currentState.content,
                    mood = currentState.mood
                )
            )
            _uiState.update { it.copy(isEntrySaved = true) }
        }
    }
}
