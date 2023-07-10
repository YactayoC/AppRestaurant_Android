package com.sebasdev.apprestaurant_android.ui.viewmodel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebasdev.apprestaurant_android.domain.model.Category
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.domain.model.Supplier
import com.sebasdev.apprestaurant_android.domain.usecase.HomeUseCase
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val preferencesDataStore: PreferencesDataStore): ViewModel() {
  private val homeUseCase = HomeUseCase()

  private val _valueSearch = MutableLiveData<String>()
  val valueSearch: LiveData<String> = _valueSearch

  private val _products = MutableLiveData<List<Product>>()
  val products: LiveData<List<Product>> = _products

  private val _categories = MutableLiveData<List<Category>>()
  val categories: LiveData<List<Category>> = _categories

  private val _suppliers = MutableLiveData<List<Supplier>>()
  val suppliers: LiveData<List<Supplier>> = _suppliers

  fun getProducts() {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        val products = homeUseCase.getProducts()
        withContext(Dispatchers.Main) {
          _products.value = products
        }
      } catch (e: Exception) {
        Log.e("LOGGER", "Error en la petición de login: ${e.message}")
      }
    }
  }

  fun getSuppliers() {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        val suppliers = homeUseCase.getSuppliers()
        withContext(Dispatchers.Main) {
          _suppliers.value = suppliers
        }
      } catch (e: Exception) {
        Log.e("LOGGER", "Error en la petición de proveedores: ${e.message}")
      }
    }
  }

  fun getCategories() {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        val categories = homeUseCase.getCategories()
        withContext(Dispatchers.Main) {
          _categories.value = categories
        }
      } catch (e: Exception) {
        Log.e("LOGGER", "Error en la petición de categorias: ${e.message}")
      }
    }
  }

  fun onValueSearchChanged(valueSearch: String = "") {
    _valueSearch.value = valueSearch
  }
}