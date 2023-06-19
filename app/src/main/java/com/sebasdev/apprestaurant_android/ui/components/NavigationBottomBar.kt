package com.sebasdev.apprestaurant_android.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person2
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sebasdev.apprestaurant_android.ui.navigation.AppScreens
import com.sebasdev.apprestaurant_android.ui.theme.ColorBlackCustom
import com.sebasdev.apprestaurant_android.ui.theme.ColorWhiteCustom


@Composable()
fun NavigationBottomBar(navigationController: NavHostController, indexButtonNav: Int = 0) {
  NavigationBar(
    containerColor = ColorWhiteCustom,
    tonalElevation = 10.dp,
    modifier = Modifier.height(55.dp)
  ) {
    NavigationBarItem(selected = indexButtonNav == 0,
      onClick = {
        navigationController.navigate(AppScreens.HomeScreen.route)
      },
      modifier = Modifier.clip(CircleShape),
      colors = NavigationBarItemDefaults.colors(
        indicatorColor = if (indexButtonNav == 0) MaterialTheme.colorScheme.primary else ColorWhiteCustom,
      ),
      icon = {
        Icon(
          imageVector = Icons.Outlined.Home,
          contentDescription = "home",
          tint = if (indexButtonNav == 0) ColorWhiteCustom else ColorBlackCustom,
          modifier = Modifier
            .padding(5.dp)
            .size(25.dp)
        )
      })
    NavigationBarItem(selected = indexButtonNav == 1,
      onClick = {
        navigationController.navigate(AppScreens.FavoriteScreen.route)
      },
      colors = NavigationBarItemDefaults.colors(
        indicatorColor = if (indexButtonNav == 1) MaterialTheme.colorScheme.primary else ColorWhiteCustom,
      ),
      icon = {
        Icon(
          imageVector = Icons.Outlined.FavoriteBorder,
          contentDescription = "favorites",
          tint = if (indexButtonNav == 1) ColorWhiteCustom else ColorBlackCustom,
          modifier = Modifier
            .padding(5.dp)
            .size(25.dp)
        )
      })
    NavigationBarItem(selected = indexButtonNav == 2,
      onClick = {
        navigationController.navigate(AppScreens.CartScreen.route)
      },
      colors = NavigationBarItemDefaults.colors(
        indicatorColor = if (indexButtonNav == 2) MaterialTheme.colorScheme.primary else ColorWhiteCustom,
      ),
      icon = {
        Icon(
          imageVector = Icons.Outlined.ShoppingCart,
          contentDescription = "cart",
          tint = if (indexButtonNav == 2) ColorWhiteCustom else ColorBlackCustom,
          modifier = Modifier
            .padding(5.dp)
            .size(25.dp)
        )
      })
    NavigationBarItem(selected = indexButtonNav == 3,
      onClick = {
        navigationController.navigate(AppScreens.ProfileScreen.route)
      },
      colors = NavigationBarItemDefaults.colors(
        indicatorColor = if (indexButtonNav == 3) MaterialTheme.colorScheme.primary else ColorWhiteCustom,
      ),
      icon = {
        Icon(
          imageVector = Icons.Outlined.Person2,
          contentDescription = "profile",
          tint = if (indexButtonNav == 3) ColorWhiteCustom else ColorBlackCustom,
          modifier = Modifier
            .padding(5.dp)
            .size(25.dp)
        )
      })
  }
}