package com.sebasdev.apprestaurant_android.ui.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CartViewModel(private val preferencesDataStore: PreferencesDataStore): ViewModel() {
  private val _products = MutableLiveData<List<Product>>()
  val products: LiveData<List<Product>> = _products

  private val _direction = MutableLiveData<String>()
  val direction: LiveData<String> = _direction

  private val _numberCard = MutableLiveData<String>()
  val numberCard: LiveData<String> = _numberCard

  private val _dateCard = MutableLiveData<String>()
  val dateCard: LiveData<String> = _dateCard

  private val _cvvCard = MutableLiveData<String>()
  val cvvCard: LiveData<String> = _cvvCard

  private val _fullnames = MutableLiveData<String>()
  val fullnames: LiveData<String> = _fullnames

  private val _email = MutableLiveData<String>()
  val email: LiveData<String> = _email

  private val _subTotalAmount = MutableLiveData<Double>()
  val subTotalAmount: LiveData<Double> = _subTotalAmount

  private val _totalAmount = MutableLiveData<Double>()
  val totalAmount: LiveData<Double> = _totalAmount

  private val _message = MutableLiveData<String>()
  val message: LiveData<String> = _message

  private val _isButtonPayEnabled = MutableLiveData<Boolean>()
  val isButtonPayEnabled: LiveData<Boolean> = _isButtonPayEnabled

  private val _showModal = MutableLiveData<Boolean>()
  val showModal: LiveData<Boolean> = _showModal

  init {
    _dateCard.value = ""
  }

  fun loadDataPay() {
    viewModelScope.launch {
      calculateSubTotal()
      val userData = preferencesDataStore.getDataUser().first()
      _direction.value = userData.direction
      _numberCard.value = ""
      _dateCard.value = ""
      _cvvCard.value = ""
      _fullnames.value = userData.fullname
      _email.value = userData.email
    }
  }

  fun formPayChange(direction: String, numberCard: String, dateCard: String?, cvvCard: String) {
    _direction.value = direction
    _numberCard.value = numberCard
    _dateCard.value = dateCard
    _cvvCard.value = cvvCard
    _isButtonPayEnabled.value = enableButtonPay(direction, numberCard, dateCard, cvvCard)
  }

  fun onPay() {
    viewModelScope.launch {
      preferencesDataStore.clearCartItems()
      getCartProducts()
      _message.value = "Se proceso el pago correctamente"
      _showModal.value = false
    }
  }

  private fun enableButtonPay(direction: String, numberCard: String, dateCard: String?, cvvCard: String): Boolean {
    return (direction.isNotEmpty() && numberCard.isNotEmpty() && dateCard != null && cvvCard.isNotEmpty())
  }

  fun clearMessage() {
    _message.value = ""
  }

  fun setShowModal(isShow: Boolean) {
    _showModal.value = isShow
  }

  fun getCartProducts() {
    viewModelScope.launch {
      val cartProducts = preferencesDataStore.getCartItems()
      _products.value = cartProducts
      calculateSubTotal()
    }
  }

  fun increaseQuantity(product: Product) {
    viewModelScope.launch {
      preferencesDataStore.modifyCartItem(product, 1)
      getCartProducts()
      calculateSubTotal()
    }
  }

  fun decreaseQuantity(product: Product) {
    viewModelScope.launch {
      if (product.quantity!! > 1) {
        preferencesDataStore.modifyCartItem(product, -1)
        calculateSubTotal()
      } else {
        preferencesDataStore.removeCartItem(product)
        calculateSubTotal()
      }
      getCartProducts()
      calculateSubTotal()
    }
  }

  private fun calculateSubTotal() {
    viewModelScope.launch {
      val cartProducts = preferencesDataStore.getCartItems()
      val total = cartProducts.sumOf { it.price * it.quantity!! }
      _subTotalAmount.value = total
    }
  }

  fun calculateTotal() {
    viewModelScope.launch {
      val total = _subTotalAmount.value?.plus(7.5)
      _totalAmount.value = total
    }
  }
}