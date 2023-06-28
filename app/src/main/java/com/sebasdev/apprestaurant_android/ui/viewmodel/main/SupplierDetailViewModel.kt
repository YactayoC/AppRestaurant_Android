package com.sebasdev.apprestaurant_android.ui.viewmodel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebasdev.apprestaurant_android.data.remote.model.request.CreateSupplierRequest
import com.sebasdev.apprestaurant_android.domain.usecase.HomeUseCase
import kotlinx.coroutines.launch

class SupplierDetailViewModel : ViewModel() {
  private val homeUseCase = HomeUseCase()

  private val _name = MutableLiveData<String>()
  val name: LiveData<String> = _name

  private val _ruc = MutableLiveData<String>()
  val ruc: LiveData<String> = _ruc

  private val _phone = MutableLiveData<String>()
  val phone: LiveData<String> = _phone

  private val _direction = MutableLiveData<String>()
  val direction: LiveData<String> = _direction

  private val _email = MutableLiveData<String>()
  val email: LiveData<String> = _email

  private val _message = MutableLiveData<String>()
  val message: LiveData<String> = _message

  fun getSupplierById(id: String) {
    viewModelScope.launch {
      try {
        Log.d("LOGGER", "Obteniendo proveedor $id")
        val fetchedSupplier = homeUseCase.getSupplier(id)
        _name.value = fetchedSupplier.name
        _ruc.value = fetchedSupplier.ruc
        _phone.value = fetchedSupplier.phone
        _direction.value = fetchedSupplier.direction
        _email.value = fetchedSupplier.email
      } catch (e: Exception) {
        Log.e("LOGGER", "Error al obtener el proveedor: ${e.message}")
      }
    }
  }

  fun addSupplier() {
    viewModelScope.launch {
      try {
        val fetchedSupplier = homeUseCase.addSupplier(
          CreateSupplierRequest(
            name = _name.value!!,
            ruc = _ruc.value!!,
            phone = _phone.value!!,
            direction = _direction.value!!,
            email = _email.value!!
          )
        )
        _message.value = fetchedSupplier
      } catch (e: Exception) {
        Log.e("LOGGER", "Error al agregar el proveedor: ${e.message}")
      }
    }
  }

  fun updateSupplier(idSupplier: String) {
    viewModelScope.launch {
      try {
        val fetchedSupplier = homeUseCase.updateSupplier(
          idSupplier,
          CreateSupplierRequest(
            name = _name.value!!,
            ruc = _ruc.value!!,
            phone = _phone.value!!,
            direction = _direction.value!!,
            email = _email.value!!
          )
        )
        _message.value = fetchedSupplier
      } catch (e: Exception) {
        Log.e("LOGGER", "Error al actualizar el proveedor: ${e.message}")
      }
    }
  }

  fun deleteSupplier(idSupplier: String) {
    viewModelScope.launch {
      try {
        val fetchedSupplier = homeUseCase.deleteSupplier(idSupplier)
        _message.value = fetchedSupplier
      } catch (e: Exception) {
        Log.e("LOGGER", "Error al eliminar el proveedor: ${e.message}")
      }
    }
  }

  fun formSupplierChanged(
    name: String,
    ruc: String,
    phone: String,
    direction: String,
    email: String
  ) {
    _name.value = name
    _ruc.value = ruc
    _phone.value = phone
    _direction.value = direction
    _email.value = email
  }

  fun clearData() {
    _message.value = ""
    _name.value = ""
    _ruc.value = ""
    _phone.value = ""
    _direction.value = ""
    _email.value = ""
  }
}