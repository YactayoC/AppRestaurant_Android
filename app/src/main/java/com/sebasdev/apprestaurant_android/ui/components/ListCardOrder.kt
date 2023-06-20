package com.sebasdev.apprestaurant_android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sebasdev.apprestaurant_android.domain.model.Order


@Composable
fun ListCardOrder(order: List<Order>, navigationController: NavHostController) {
  LazyVerticalGrid(
    columns = GridCells.Fixed(1),
    verticalArrangement = Arrangement.spacedBy(15.dp),
    contentPadding = PaddingValues(bottom = 50.dp),
    horizontalArrangement = Arrangement.spacedBy(15.dp),
    content = {
      items(order.size) {
        CardOrder(order[it], navigationController)
      }
    }
  )
}