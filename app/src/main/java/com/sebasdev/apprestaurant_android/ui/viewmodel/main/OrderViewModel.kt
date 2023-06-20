package com.sebasdev.apprestaurant_android.ui.viewmodel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebasdev.apprestaurant_android.domain.usecase.OrderUseCase
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class OrderViewModel(private val preferencesDataStore: PreferencesDataStore): ViewModel() {
  private val orderUseCase = OrderUseCase()

  private val _messageResponse = MutableLiveData<String>()
  val messageResponse: LiveData<String> = _messageResponse

  fun addOrder(subTotal: Double) {
    viewModelScope.launch {
      try {
        val productIntCart = preferencesDataStore.getCartItems().first()
        val user = preferencesDataStore.getDataUser().first()
        val response = orderUseCase.createOrder(
          product = productIntCart._id,
          user = user._id,
          subtotal = subTotal,
          total = (productIntCart.price * productIntCart.quantity!!) + 7.50,
          quantity = productIntCart.quantity,
          state = "en cocina"
        )
        _messageResponse.value = response
      } catch (e: Exception) {
        Log.e("LOGGER", "Error en la petición de agregar orden: ${e.message}")
      }

    }
  }

  fun clearMessage() {
    _messageResponse.value = ""
  }
}