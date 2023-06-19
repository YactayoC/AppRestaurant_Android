package com.sebasdev.apprestaurant_android.ui.viewmodel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.domain.usecase.HomeUseCase
import com.sebasdev.apprestaurant_android.domain.usecase.UserUseCase
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailViewModel(private val preferencesDataStore: PreferencesDataStore): ViewModel() {
  private val homeUseCase = HomeUseCase()
  private val userUseCase = UserUseCase()

  private val _product = MutableLiveData<Product>()
  val product: LiveData<Product> = _product

  private val _isFavorite = MutableLiveData<Boolean>().apply { value = false }
  val isFavorite: LiveData<Boolean> = _isFavorite

  private val _messageResponse = MutableLiveData<String>()
  val messageResponse: LiveData<String> = _messageResponse

  private val _messageResponseCart = MutableLiveData<String>()
  val messageResponseCart: LiveData<String> = _messageResponseCart

  fun getProduct(id: String) {
    viewModelScope.launch {
      try {
        val fetchedProduct = homeUseCase.getProduct(id)
        val userProductsFavorite =
          userUseCase.getProductsFavorite(preferencesDataStore.getDataUser().first()._id)
        val isFavorite = userProductsFavorite.any { it._id == id }

        withContext(Dispatchers.Main) {
          _product.value = fetchedProduct
          _isFavorite.value = isFavorite
        }
      } catch (e: Exception) {
        Log.e("LOGGER", "Error en la petición de login: ${e.message}")
      }
    }
  }

  fun onClickButtonFavorite(idProduct: String) {
    viewModelScope.launch {
      try {
        val updateIsFavorite = !_isFavorite.value!!
        Log.d("LOGGER", "updateIsFavorite: $updateIsFavorite")
        val idUser = preferencesDataStore.getDataUser().first()._id
        val response = userUseCase.addProductFavorite(idUser, idProduct, updateIsFavorite)

        withContext(Dispatchers.Main) {
          _isFavorite.value = updateIsFavorite
          _messageResponse.value = response
        }

      } catch (e: Exception) {
        Log.e("LOGGER", "Error en la petición de agregar producto favorito: ${e.message}")
      }
    }
  }

  fun addToCart(item: Product) {
    viewModelScope.launch {
      preferencesDataStore.modifyCartItem(item, 1)
      _messageResponseCart.value = "Producto agregado al carrito"
    }
  }

  fun clearMessageResponse() {
    _messageResponse.value = ""
  }

  fun clearMessageResponseCart() {
    _messageResponseCart.value = ""
  }
}