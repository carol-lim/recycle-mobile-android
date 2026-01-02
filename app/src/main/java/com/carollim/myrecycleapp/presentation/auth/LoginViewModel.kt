package com.carollim.myrecycleapp.presentation.auth

import android.app.Application
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carollim.myrecycleapp.R
import com.carollim.myrecycleapp.domain.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val app: Application
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(app.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    private val googleSignInClient = GoogleSignIn.getClient(app, gso)

    val signInIntent: Intent = googleSignInClient.signInIntent

    fun onSignInResult(result: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(result)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account?.idToken
            if (idToken != null) {
                signInWithGoogle(idToken)
            }
        } catch (e: ApiException) {
            _loginState.value = LoginState(error = e.message)
        }
    }

    private fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _loginState.value = LoginState(isLoading = true)
            repo.signInWithGoogle(idToken).fold(
                onSuccess = {
                    _loginState.value = LoginState(isSuccess = true)
                },
                onFailure = {
                    _loginState.value = LoginState(error = it.message)
                }
            )
        }
    }
}