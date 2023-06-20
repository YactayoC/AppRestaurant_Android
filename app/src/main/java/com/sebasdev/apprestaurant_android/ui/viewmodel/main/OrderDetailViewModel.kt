package com.sebasdev.apprestaurant_android.ui.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebasdev.apprestaurant_android.domain.model.Order
import com.sebasdev.apprestaurant_android.domain.usecase.OrderUseCase
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import kotlinx.coroutines.launch

class OrderDetailViewModel(private val preferencesDataStore: PreferencesDataStore) : ViewModel() {
  private val orderUseCase = OrderUseCase()

  private val _order = MutableLiveData<Order>()
  val order: LiveData<Order> = _order


  fun getOrderId(idOrder: String) {
    viewModelScope.launch {
      _order.value = orderUseCase.getOrder(idOrder)
    }
  }
}