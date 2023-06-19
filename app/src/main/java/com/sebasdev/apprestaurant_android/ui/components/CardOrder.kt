package com.sebasdev.apprestaurant_android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sebasdev.apprestaurant_android.domain.model.Order
import com.sebasdev.apprestaurant_android.ui.theme.ColorWhiteCustom


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardOrder(order: Order) {
  val color = when(order.state) {
    "en cocina" -> Color(0xFFdc8620)
    "en camino" -> Color(0xFF20BADC)
    "entregado" -> Color(0xFF12FDC20)
    "cancelado" -> Color(0xFFDC2020)
    else -> Color(0xFFdc8620)
  }

  Card(
    onClick = { /*TODO*/ },
    colors = CardDefaults.cardColors(
      containerColor = ColorWhiteCustom
    ),
    elevation = CardDefaults.cardElevation(5.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(text = "N. Pedido ${order._id}", color = MaterialTheme.colorScheme.secondary, modifier = Modifier.weight(1f))
        TextButton(
          onClick = { /*TODO*/ },
          colors = ButtonDefaults.buttonColors(
            containerColor = color,
          ),
          modifier = Modifier
            .align(Alignment.CenterVertically)
            .height(30.dp)
        ) {
          Text(text = "${order.state.capitalize()}", fontSize = 12.sp)
          Icon(
            imageVector = Icons.Outlined.KeyboardArrowRight,
            contentDescription = "detail",
            modifier = Modifier.size(18.dp)
          )
        }
      }
      Text(text = "${order.product.name.capitalize()}", fontSize = 16.sp)
      Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp)
      ) {
        Text(
          text = "Cantidad: ${order.quantity}",
          fontSize = 12.sp,
          color = MaterialTheme.colorScheme.secondary
        )
        Text(
          text = "Total: S/ ${order.total}",
          fontSize = 12.sp,
          color = MaterialTheme.colorScheme.secondary
        )
      }
    }
  }
}