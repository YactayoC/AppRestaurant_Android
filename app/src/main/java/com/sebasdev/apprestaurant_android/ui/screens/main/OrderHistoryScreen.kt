package com.sebasdev.apprestaurant_android.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sebasdev.apprestaurant_android.domain.model.Order
import com.sebasdev.apprestaurant_android.ui.components.ListCardOrder
import com.sebasdev.apprestaurant_android.ui.components.MyTopAppBar
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.OrderHistoryViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(navigationController: NavHostController, orderHistoryViewModel: OrderHistoryViewModel) {
  val ordersRecents: List<Order> by orderHistoryViewModel.ordersHistory.observeAsState(initial = emptyList())

  LaunchedEffect(true) {
    orderHistoryViewModel.getListOrdersHistory()
  }

  Scaffold(
    topBar = {
      MyTopAppBar(navigationController, "Historial de pedidos")
    },
    content = {
      Column(
        Modifier
          .fillMaxSize()
          .padding(15.dp),
      ) {
        Spacer(modifier = Modifier.size(60.dp))
        ListCardOrder(ordersRecents)
      }
    },
  )
}