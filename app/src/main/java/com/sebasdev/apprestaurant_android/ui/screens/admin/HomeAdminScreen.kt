package com.sebasdev.apprestaurant_android.ui.screens.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sebasdev.apprestaurant_android.R
import com.sebasdev.apprestaurant_android.ui.navigation.AppScreens
import com.sebasdev.apprestaurant_android.ui.theme.ColorWhiteCustom

@Composable
fun HomeAdminScreen(navigationController: NavHostController) {
  Column(
    Modifier
      .fillMaxSize()
      .padding(15.dp),
  ) {
    Logo()
    Spacer(modifier = Modifier.size(30.dp))
    Text(
      text = "¡Bienvenido Administrador!",
      fontSize = 25.sp,
      fontWeight = FontWeight.Bold,
      textAlign = TextAlign.Center,
      modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.size(30.dp))
    ButtonOptions(navigationController)
  }
}

@Composable
private fun Logo() {
  Image(
    painter = painterResource(id = R.drawable.logo),
    contentDescription = "logo",
    modifier = Modifier
      .fillMaxWidth()
      .size(240.dp),
    alignment = Alignment.Center
  )
}


@Composable()
fun ButtonOptions(
  navigationController: NavHostController,
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
  ) {
    Button(
      onClick = {
        navigationController.navigate(AppScreens.ManagmentAdminScreen.route)
      },
      modifier = Modifier.fillMaxWidth(),
      colors = ButtonDefaults.buttonColors(
        disabledContainerColor = Color(0xFFE4575F),
        disabledContentColor = ColorWhiteCustom,
      ),
      shape = MaterialTheme.shapes.medium
    ) {
      Text(
        text = "Gestionar Inventario",
        fontSize = 15.sp,
        fontWeight = FontWeight.Medium,
        color = ColorWhiteCustom,
        modifier = Modifier.padding(8.dp)
      )
    }
    Button(
      onClick = {
        navigationController.navigate(AppScreens.LoginScreen.route)
      },
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp),
      colors = ButtonDefaults.buttonColors(
        disabledContainerColor = Color(0xFFE4575F),
        disabledContentColor = ColorWhiteCustom,
      ),
      shape = MaterialTheme.shapes.medium
    ) {
      Text(
        text = "Cerrar Sesion",
        fontSize = 15.sp,
        fontWeight = FontWeight.Medium,
        color = ColorWhiteCustom,
        modifier = Modifier.padding(8.dp)
      )
    }
  }
}