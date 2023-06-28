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

class OrderRecentsViewModel(private val preferencesDataStore: PreferencesDataStore): ViewModel() {
  private val orderUseCase = OrderUseCase()

  private val _ordersRecents = MutableLiveData<List<Order>>()
  val ordersRecents: LiveData<List<Order>> = _ordersRecents

  fun getListOrdersRecents() {
    viewModelScope.launch {
      try {
        Log.d("LOGGER", "Id del usuario: ${preferencesDataStore.getDataUser().first()._id}")
        val response = orderUseCase.getOrders(preferencesDataStore.getDataUser().first()._id)
        _ordersRecents.value = response.filter {
          it.state == "en cocina" || it.state == "en camino"
        }
        Log.d("LOGGER", "Response: $response")
      } catch (e: Exception) {
        Log.e("LOGGER", "Error en la petición de obtener las ordenes recientes: ${e.message}")
      }
    }
  }

}