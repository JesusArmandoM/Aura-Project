package com.example.aura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.aura.ui.screens.EntryCreationScreen
import com.example.aura.ui.screens.EntryCreationViewModel
import com.example.aura.ui.screens.HomeScreen
import com.example.aura.ui.screens.HomeViewModel
import com.example.aura.ui.theme.AuraTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AuraTheme {
                AuraAppNav()
            }
        }
    }
}

@Serializable
sealed interface NavKey {
    @Serializable
    data object Home : NavKey
    @Serializable
    data object CreateEntry : NavKey
}

@Composable
fun AuraAppNav() {
    val backStack = remember { mutableStateListOf<NavKey>(NavKey.Home) }
    val context = LocalContext.current
    val app = context.applicationContext as AuraApp

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        modifier = Modifier.fillMaxSize()
    ) { key ->
        when (key) {
            is NavKey.Home -> NavEntry(key) {
                val homeViewModel: HomeViewModel = viewModel(
                    factory = remember {
                        object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                @Suppress("UNCHECKED_CAST")
                                return HomeViewModel(app.repository) as T
                            }
                        }
                    }
                )
                HomeScreen(
                    viewModel = homeViewModel,
                    onCreateEntry = { backStack.add(NavKey.CreateEntry) }
                )
            }
            is NavKey.CreateEntry -> NavEntry(key) {
                val creationViewModel: EntryCreationViewModel = viewModel(
                    factory = remember {
                        object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                @Suppress("UNCHECKED_CAST")
                                return EntryCreationViewModel(app.repository) as T
                            }
                        }
                    }
                )
                EntryCreationScreen(
                    viewModel = creationViewModel,
                    onNavigateBack = { backStack.removeLastOrNull() }
                )
            }
        }
    }
}
