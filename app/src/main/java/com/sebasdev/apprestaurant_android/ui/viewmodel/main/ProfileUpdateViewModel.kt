package com.sebasdev.apprestaurant_android.ui.viewmodel.main

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebasdev.apprestaurant_android.domain.usecase.UserUseCase
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileUpdateViewModel(private val preferencesDataStore: PreferencesDataStore) : ViewModel() {
    private val userUseCase = UserUseCase()
    private val user = preferencesDataStore.getDataUser()

    private val _idUser = MutableLiveData<String>()
    val idUser: LiveData<String> = _idUser

    private val _profile = MutableLiveData<String>()
    val profile: LiveData<String> = _profile

    private val _fullname = MutableLiveData<String>()
    val fullname: LiveData<String> = _fullname

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> = _phone

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _direction = MutableLiveData<String>()
    val direction: LiveData<String> = _direction

    private val _district = MutableLiveData<String>().apply { value = "Villa el Salvador" }
    val district: LiveData<String> = _district

    private val _selectedImageUri = MutableLiveData<Uri>()
    val selectedImageUri: LiveData<Uri> = _selectedImageUri

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun onLoadUserData() {
        viewModelScope.launch {
            _fullname.value = user.first().fullname
            _phone.value = user.first().phone
            _email.value = user.first().email
            _direction.value = user.first().direction
            _profile.value = user.first().profile
        }
    }

    fun onSelectedImageUriChanged(valueSearch: Uri) {
        viewModelScope.launch {
            _selectedImageUri.value = Uri.parse(valueSearch.toString())
        }
    }

    fun onProfileUpdateChange(
        fullname: String,
        phone: String,
        email: String,
        password: String,
        direction: String,
        district: String
    ) {
        _fullname.value = fullname
        _phone.value = phone
        _email.value = email
        _password.value = password
        _direction.value = direction
        _district.value = district
    }

    fun onUpdateUser() {
        viewModelScope.launch {
            if (_fullname.value != null && _phone.value != null && _email.value != null && _password.value != null && _direction.value != null && _district.value != null) {
                Log.d(
                    "ProfileUpdateViewModel",
                    "onUpdateUser: " + _fullname.value + " " + _phone.value + " " + _email.value + " " + _password.value + " " + _direction.value + " " + _district.value
                )
            }
            if (_password.value == null || _password.value == "") {
                _message.value = "Porfavor ingrese una contraseña o ingrese su contraseña actual"
            } else {
                userUseCase.updateUser(
                    user.first()._id,
                    _fullname.value!!,
                    _direction.value!!,
                    _phone.value!!,
                    _password.value!!,
                )
                _message.value = "Usuario actualizado correctamente"
            }
        }
    }

    fun clearMessage() {
        _message.value = ""
    }
}