package com.sebasdev.apprestaurant_android.ui.viewmodel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebasdev.apprestaurant_android.domain.model.Category
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.domain.usecase.HomeUseCase
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import kotlinx.coroutines.launch

class HomeViewModel(private val preferencesDataStore: PreferencesDataStore): ViewModel() {
  private val homeUseCase = HomeUseCase()

  private val _valueSearch = MutableLiveData<String>()
  val valueSearch: LiveData<String> = _valueSearch

  private val _products = MutableLiveData<List<Product>>()
  val products: LiveData<List<Product>> = _products

  private val _categories = MutableLiveData<List<Category>>()
  val categories: LiveData<List<Category>> = _categories

  fun getProducts() {
    viewModelScope.launch {
      try {
        _products.value = homeUseCase.getProducts()
      } catch (e: Exception) {
        Log.e("LOGGER", "Error en la petición de login: ${e.message}")
      }
    }
  }

  fun getCategories() {
    viewModelScope.launch {
      try {
        _categories.value = homeUseCase.getCategories()
      } catch (e: Exception) {
        Log.e("LOGGER", "Error en la petición de categorias: ${e.message}")
      }
    }
  }

  fun onValueSearchChanged(valueSearch: String = "a") {
    _valueSearch.value = valueSearch
  }
}