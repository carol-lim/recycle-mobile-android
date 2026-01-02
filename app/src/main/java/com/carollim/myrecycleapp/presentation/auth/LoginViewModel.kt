package com.carollim.myrecycleapp.presentation.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    // In a future step, we will add state management for the login process (loading, success, error)
    // and the logic to handle the Google Sign-In result.

    fun onSignInClick() {
        // This will trigger the Google Sign-In UI
    }
}
