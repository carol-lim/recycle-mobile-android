package com.carollim.myrecycleapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carollim.myrecycleapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    private val _hasSignedOut = MutableStateFlow(false)
    val hasSignedOut: StateFlow<Boolean> = _hasSignedOut

    fun onSignOutClick() {
        viewModelScope.launch {
            repo.signOut().onSuccess {
                _hasSignedOut.value = true
            }
        }
    }
}