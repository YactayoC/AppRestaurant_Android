package com.sebasdev.apprestaurant_android.ui.viewmodel.auth

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.sebasdev.apprestaurant_android.domain.usecase.AuthUseCase
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import com.sebasdev.apprestaurant_android.ui.navigation.AppScreens
import kotlinx.coroutines.launch

class LoginViewModel(private val preferencesDataStore: PreferencesDataStore): ViewModel() {
  private val authUseCase = AuthUseCase()

  private val _email = MutableLiveData<String>()
  val email: LiveData<String> = _email

  private val _password = MutableLiveData<String>()
  val password: LiveData<String> = _password

  private val _isButtonLoginEnabled = MutableLiveData<Boolean>()
  val isButtonLoginEnabled: LiveData<Boolean> = _isButtonLoginEnabled

  private val _isLoading = MutableLiveData<Boolean>()
  val isLoading: LiveData<Boolean> = _isLoading

  private val _messageErrorResponse = MutableLiveData<String>()
  val messageErrorResponse: LiveData<String> = _messageErrorResponse

  fun onLoginChanged(email: String, password: String) {
    _email.value = email
    _password.value = password
    _isButtonLoginEnabled.value = enableButtonLogin(email, password)
  }

  private fun enableButtonLogin(email: String, password: String) =
    Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 6

  fun onSubmitLogin(navigationController: NavHostController) {
    viewModelScope.launch {
      _isLoading.value = true
      _isButtonLoginEnabled.value = false

      try {
        val result = authUseCase.onLogin(email.value!!, password.value!!)

        if (result.errorMessage != null) {
          _messageErrorResponse.value = result.errorMessage.message
          _isButtonLoginEnabled.value = true
          _isLoading.value = false
        } else {
          result.user?.let {
            preferencesDataStore.setDataUser(
              result.user._id,
              result.user.fullname,
              result.user.phone,
              result.user.email,
              result.user.profile,
              result.user.direction ?: "",
            )
          }
          navigationController.navigate(AppScreens.HomeScreen.route)
          Log.i("LOGGER", result.toString())
        }
      } catch (e: Exception) {
        Log.e("LOGGER", "Error en la petición de login: ${e.message}")
        _isButtonLoginEnabled.value = true
        _isLoading.value = false
      }
    }
  }

  fun clearMessageErrorResponse() {
    _messageErrorResponse.value = ""
  }
}