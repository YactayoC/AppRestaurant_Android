package com.sebasdev.apprestaurant_android

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import com.sebasdev.apprestaurant_android.ui.navigation.AppNavigation
import com.sebasdev.apprestaurant_android.ui.theme.AppRestaurant_AndroidTheme

class MainActivity : ComponentActivity() {
  private lateinit var preferencesDataStore: PreferencesDataStore

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val context = this
    preferencesDataStore = PreferencesDataStore(context)

    setContent {
      AppRestaurant_AndroidTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          AppNavigation(preferencesDataStore)
        }
      }
    }
  }
}