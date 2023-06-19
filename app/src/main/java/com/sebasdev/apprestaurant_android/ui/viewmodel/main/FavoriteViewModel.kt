package com.sebasdev.apprestaurant_android.ui.viewmodel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.domain.usecase.UserUseCase
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FavoriteViewModel(private val preferencesDataStore: PreferencesDataStore): ViewModel() {
  private val userUseCase = UserUseCase()

  private val _productsFavorite = MutableLiveData<List<Product>>()
  val productsFavorite: LiveData<List<Product>> = _productsFavorite

  fun getProductsFavorite() {
    viewModelScope.launch {
      try {
        _productsFavorite.value = userUseCase.getProductsFavorite(preferencesDataStore.getDataUser().first()._id)
      } catch (e: Exception) {
        Log.e("LOGGER", "Error en la petición de login: ${e.message}")
      }
    }
  }
}