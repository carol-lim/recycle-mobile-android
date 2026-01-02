package com.carollim.myrecycleapp.presentation.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    // In a future step, we will add state management for the user's dashboard data
    // and the logic to handle signing out.

    fun onSignOutClick() {
        // This will trigger the sign-out process
    }
}
