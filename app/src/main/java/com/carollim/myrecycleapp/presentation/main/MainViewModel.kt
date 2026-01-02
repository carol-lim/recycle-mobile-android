package com.carollim.myrecycleapp.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.carollim.myrecycleapp.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _startDestination = mutableStateOf(Screen.Login.route)
    val startDestination: State<String> = _startDestination

    // In a future step, we will add logic here to check Firebase Auth
    // and dynamically set the start destination to Screen.Home if the user is logged in.
}