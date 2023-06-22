package com.sebasdev.apprestaurant_android.ui.screens.admin

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.sebasdev.apprestaurant_android.ui.components.MyTopAppBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagmentSupplierAdminScreen(
  navigationController: NavHostController
) {
  Scaffold(
    topBar = {
      MyTopAppBar(navigationController, "Detalles del pedido")
    },
    content = {
      Column {
        Text(text = "ManagmentAdminScreen")
      }
    },
  )
}