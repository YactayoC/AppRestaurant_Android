package com.sebasdev.apprestaurant_android.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.sebasdev.apprestaurant_android.ui.theme.ColorBlackCustom


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(navigationController: NavHostController, text: String) {
  TopAppBar(
    title = { Text(text = text) },
    colors = TopAppBarDefaults.largeTopAppBarColors(
      containerColor = Color.Transparent,
      titleContentColor = ColorBlackCustom,
      navigationIconContentColor = ColorBlackCustom,
    ),
    navigationIcon = {
      IconButton(onClick = {
        navigationController.popBackStack()
      }) {
        Icon(imageVector = Icons.Outlined.ArrowBackIosNew, contentDescription = "back")
      }
    }
  )
}