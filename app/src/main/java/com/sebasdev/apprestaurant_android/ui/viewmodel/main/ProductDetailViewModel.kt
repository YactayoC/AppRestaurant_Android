package com.sebasdev.apprestaurant_android.ui.viewmodel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebasdev.apprestaurant_android.data.remote.model.request.CreateProductRequest
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.domain.usecase.HomeUseCase
import com.sebasdev.apprestaurant_android.domain.usecase.UserUseCase
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailViewModel(private val preferencesDataStore: PreferencesDataStore) : ViewModel() {
  private val homeUseCase = HomeUseCase()
  private val userUseCase = UserUseCase()

  private val _image = MutableLiveData<String>()
  val image: LiveData<String> = _image

  private val _name = MutableLiveData<String>()
  val name: LiveData<String> = _name

  private val _description = MutableLiveData<String>()
  val description: LiveData<String> = _description

  private val _price = MutableLiveData<String>()
  val price: LiveData<String> = _price

  private val _category = MutableLiveData<String>()
  val category: LiveData<String> = _category

  private val _product = MutableLiveData<Product>()
  val product: LiveData<Product> = _product

  private val _isFavorite = MutableLiveData<Boolean>().apply { value = false }
  val isFavorite: LiveData<Boolean> = _isFavorite

  private val _messageResponse = MutableLiveData<String>()
  val messageResponse: LiveData<String> = _messageResponse

  private val _messageResponseCart = MutableLiveData<String>()
  val messageResponseCart: LiveData<String> = _messageResponseCart

  private val _message = MutableLiveData<String>()
  val message: LiveData<String> = _message

  fun addProduct() {
    viewModelScope.launch {
      try {
        val fetchedProduct = homeUseCase.addProduct(
          CreateProductRequest(
            name = _name.value!!,
            description = _description.value!!,
            price = _price.value!!.toDouble(),
            category = _category.value!!,
            image = "https://polleriaslagranja.com/wp-content/uploads/2022/10/La-Granja-Real-Food-Chicken-1.4-de-Pollo-a-la-Brasa.png",
          )
        )
        _message.value = fetchedProduct
        _name.value = ""
        _description.value = ""
        _price.value = ""
      } catch (e: Exception) {
        Log.e("LOGGER", "Error al agregar el producto: ${e.message}")
      }
    }
  }

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
        Log.e("LOGGER", "Error al obtener el producto: ${e.message}")
      }
    }
  }

  fun getProductById(id: String) {
    viewModelScope.launch {
      try {
        val fetchedProduct = homeUseCase.getProduct(id)
        withContext(Dispatchers.Main) {
          _product.value = fetchedProduct
          _image.value = _product.value!!.image
          _name.value = _product.value!!.name
          _description.value = _product.value!!.description
          _price.value = _product.value!!.price.toString()
        }
      } catch (e: Exception) {
        Log.e("LOGGER", "Error al obtener el producto: ${e.message}")
      }
    }
  }

  fun updateProduct(idProduct: String) {
    viewModelScope.launch {
      try {
        val fetchedSupplier = homeUseCase.updateProduct(
          idProduct,
          CreateProductRequest(
            name = _name.value!!,
            description = _description.value!!,
            price = _price.value!!.toDouble(),
            category = _category.value!!,
            image = _image.value!!,
          )
        )
        Log.d("LOGGER", "fetchedSupplier: $fetchedSupplier")
        _message.value = fetchedSupplier
      } catch (e: Exception) {
        Log.e("LOGGER", "Error al actualizar el producto: ${e.message}")
      }
    }
  }

  fun deleteProduct(idProduct: String) {
    viewModelScope.launch {
      try {
        val fetchedSupplier = homeUseCase.deleteProduct(idProduct)
        Log.d("LOGGER", "fetchedSupplier: $fetchedSupplier")
        _message.value = fetchedSupplier
      } catch (e: Exception) {
        Log.e("LOGGER", "Error al eliminar el producto: ${e.message}")
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

  fun formProductChanged(
    name: String,
    description: String,
    price: String,
    category: String,
    image: String
  ) {
    _name.value = name
    _description.value = description
    _price.value = price
    _category.value = category
    _image.value = image
  }

  fun addToCart(item: Product) {
    viewModelScope.launch {
      preferencesDataStore.modifyCartItem(item, 1)
      _messageResponseCart.value = "Producto agregado al carrito"
    }
  }

  fun getCategoryAdmin() {
    viewModelScope.launch {
      preferencesDataStore.getCategoryProductAdmin()
        .collect { category -> _category.value = category }
    }
  }

  fun clearMessageResponse() {
    _messageResponse.value = ""
  }

  fun clearMessageResponseCart() {
    _messageResponseCart.value = ""
  }

  fun clearMessage() {
    _message.value = ""
  }
}