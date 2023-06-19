package com.sebasdev.apprestaurant_android.ui.viewmodel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebasdev.apprestaurant_android.domain.model.Order
import com.sebasdev.apprestaurant_android.domain.usecase.OrderUseCase
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class OrderHistoryViewModel(private val preferencesDataStore: PreferencesDataStore) : ViewModel(){
  private val orderUseCase = OrderUseCase()

  private val _ordersHistory = MutableLiveData<List<Order>>()
  val ordersHistory: LiveData<List<Order>> = _ordersHistory

  fun getListOrdersHistory() {
    viewModelScope.launch {
      try {
        val response = orderUseCase.getOrders(preferencesDataStore.getDataUser().first()._id)
        _ordersHistory.value = response.filter {
          it.state == "entregado" || it.state == "cancelado"
        }
        Log.d("LOGGER", "Response: $response")
      } catch (e: Exception) {
        Log.e("LOGGER", "Error en la petición de obtener las ordenes recientes: ${e.message}")
      }
    }
  }
}