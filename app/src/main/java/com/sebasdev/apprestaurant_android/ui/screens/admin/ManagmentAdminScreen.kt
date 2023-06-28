package com.sebasdev.apprestaurant_android.ui.screens.admin

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sebasdev.apprestaurant_android.R
import com.sebasdev.apprestaurant_android.ui.components.MyTopAppBar
import com.sebasdev.apprestaurant_android.ui.navigation.AppScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagmentAdminScreen(navigationController: NavHostController) {
  Scaffold(
    topBar = {
      MyTopAppBar(
        navigationController,
        "Opciones de gestión"
      )
    },
  ) {
    Column(
      Modifier
        .fillMaxSize()
        .padding(15.dp),
      verticalArrangement = Arrangement.spacedBy(50.dp)
    ) {
      Spacer(modifier = Modifier.size(40.dp))
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clickable {
            navigationController.navigate(AppScreens.ManagmentProductsAdminScreen.route)
          }
      ) {
        Image(
          painter = painterResource(id = R.drawable.products),
          contentDescription = "Products",
          modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .drawWithContent {
              drawContent()
              drawRect(color = Color.Black.copy(alpha = 0.5f))
            },
          contentScale = ContentScale.Crop
        )
        Text(
          text = "PRODUCTOS",
          modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(10.dp),
          color = Color.White,
          fontSize = 30.sp
        )

      }

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clickable {
            navigationController.navigate(AppScreens.ManagmentListItemAdminScreen.createRoute("suppliers"))
          }
      ) {
        Image(
          painter = painterResource(id = R.drawable.supplier),
          contentDescription = "Supplier",
          modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .drawWithContent {
              drawContent()
              drawRect(color = Color.Black.copy(alpha = 0.5f))
            },
          contentScale = ContentScale.Crop
        )
        Text(
          text = "PROVEEDORES",
          modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(10.dp),
          color = Color.White,
          fontSize = 30.sp
        )
      }
    }
  }

}