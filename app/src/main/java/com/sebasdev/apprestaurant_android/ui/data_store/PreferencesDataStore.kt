package com.sebasdev.apprestaurant_android.ui.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sebasdev.apprestaurant_android.domain.model.Product
import com.sebasdev.apprestaurant_android.domain.model.UserPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PreferencesDataStore(context: Context) {
  private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
  private val pref = context.dataStore

  companion object {
    var userProfile = stringPreferencesKey("USER_PROFILE")
    var userId = stringPreferencesKey("USER_ID")
    var userFullname = stringPreferencesKey("USER_FULLNAME")
    var userPhone = stringPreferencesKey("USER_PHONE")
    var userEmail = stringPreferencesKey("USER_EMAIL")
    var userDirection = stringPreferencesKey("USER_DIRECTION")
    var cartItems = stringPreferencesKey("CART_ITEMS")
    var categoryProductAdmin = stringPreferencesKey("CATEGORY_ADMIN")
  }

  suspend fun setDataUser(
    id: String,
    fullname: String,
    phone: String,
    email: String,
    profile: String,
    direction: String,
  ) {
    pref.edit {
      it[userId] = id
      it[userFullname] = fullname
      it[userPhone] = phone
      it[userEmail] = email
      it[userProfile] = profile
      it[userDirection] = direction
    }
  }

  fun getDataUser() = pref.data.map {
    UserPreference(
      _id = it[userId] ?: "",
      fullname = it[userFullname] ?: "",
      profile = it[userProfile] ?: "",
      direction = it[userDirection] ?: "",
      phone = it[userPhone] ?: "",
      email = it[userEmail] ?: "",
    )
  }

  suspend fun clearUserData() {
    pref.edit {
      it.remove(userId)
      it.remove(userFullname)
      it.remove(userPhone)
      it.remove(userEmail)
      it.remove(userProfile)
      it.remove(userDirection)
    }
  }

  suspend fun getCartItems(): List<Product> {
    val cartItemsJson = pref.data.first()[cartItems]
    return if (cartItemsJson.isNullOrEmpty()) {
      emptyList()
    } else {
      val typeToken = object : TypeToken<List<Product>>() {}.type
      Gson().fromJson(cartItemsJson, typeToken)
    }
  }

  suspend fun modifyCartItem(product: Product, quantity: Int) {
    val currentCartItems = getCartItems().toMutableList()

    val existingProductIndex = currentCartItems.indexOfFirst { it._id == product._id }

    if (existingProductIndex != -1) {
      val existingProduct = currentCartItems[existingProductIndex]
      val newQuantity = existingProduct.quantity?.plus(quantity)
      currentCartItems[existingProductIndex] = existingProduct.copy(quantity = newQuantity)
    } else {
      if (quantity > 0) {
        val productWithQuantity = product.copy(quantity = quantity)
        currentCartItems.add(productWithQuantity)
      }
    }

    val cartItemsJson = Gson().toJson(currentCartItems)

    pref.edit {
      it[cartItems] = cartItemsJson
    }
  }

  suspend fun removeCartItem(product: Product) {
    val currentCartItems = getCartItems().toMutableList()
    currentCartItems.remove(product)
    val cartItemsJson = Gson().toJson(currentCartItems)
    pref.edit {
      it[cartItems] = cartItemsJson
    }
  }

  suspend fun setCategoryProductAdmin(category: String) {
    pref.edit {
      it[categoryProductAdmin] = category
    }
  }

  fun getCategoryProductAdmin() = pref.data.map {
    it[categoryProductAdmin] ?: ""
  }

  suspend fun clearCartItems() {
    pref.edit {
      it.remove(cartItems)
    }
  }
}