package com.carollim.myrecycleapp.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.carollim.myrecycleapp.domain.repository.AuthRepository
import com.carollim.myrecycleapp.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {
    private val _startDestination = mutableStateOf(Screen.Login.route)
    val startDestination: State<String> = _startDestination

    init {
        if (repo.currentUser != null) {
            _startDestination.value = Screen.Home.route
        }
    }
}