package com.sebasdev.apprestaurant_android.ui.viewmodel.auth

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebasdev.apprestaurant_android.domain.usecase.AuthUseCase
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
  private val authUseCase = AuthUseCase()

  private val _fullname = MutableLiveData<String>()
  val fullname: LiveData<String> = _fullname

  private val _phone = MutableLiveData<String>()
  val phone: LiveData<String> = _phone

  private val _email = MutableLiveData<String>()
  val email: LiveData<String> = _email

  private val _password = MutableLiveData<String>()
  val password: LiveData<String> = _password

  private val _isButtonRegisterEnabled = MutableLiveData<Boolean>()
  val isButtonRegisterEnabled: LiveData<Boolean> = _isButtonRegisterEnabled

  private val _isLoading = MutableLiveData<Boolean>()
  val isLoading: LiveData<Boolean> = _isLoading

  private val _messageResponse = MutableLiveData<String>()
  val messageResponse: LiveData<String> = _messageResponse

  fun onRegisterChanged(fullname: String, phone: String, email: String, password: String) {
    _fullname.value = fullname
    _phone.value = phone
    _email.value = email
    _password.value = password
    _isButtonRegisterEnabled.value = enableButtonRegister(fullname, phone, email, password)
  }

  private fun enableButtonRegister(
    fullname: String,
    phone: String,
    email: String,
    password: String
  ): Boolean {
    val fullnameRegex = Regex("^[a-zA-Z ]+\$")
    val phoneRegex = Regex("^[0-9]{1,9}\$")
    return fullnameRegex.matches(fullname.trim()) && fullname.trim().length >= 5
            && Patterns.EMAIL_ADDRESS.matcher(email).matches()
            && password.length >= 6
            && phoneRegex.matches(phone.trim())
  }

  fun onSubmitRegister() {
    viewModelScope.launch {
      try {
        val result =
          authUseCase.onRegister(fullname.value!!, email.value!!, password.value!!, phone.value!!)

        if (result.errorMessage != null) {
          _messageResponse.value = result.errorMessage.message
          _isButtonRegisterEnabled.value = true
          _isLoading.value = false
        } else {
          _messageResponse.value = result.message
          _email.value = ""
          _password.value = ""
          _fullname.value = ""
          _phone.value = ""
        }
      } catch (e: Exception) {
        Log.e("LOGGER", "Error en la petición de regsitro: ${e.message}")
        _isButtonRegisterEnabled.value = true
        _isLoading.value = false
      }

      _isButtonRegisterEnabled.value = true
      _isLoading.value = false
    }
  }

  fun clearMessageErrorResponse() {
    _messageResponse.value = ""
  }
}