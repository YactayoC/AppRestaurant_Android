package com.sebasdev.apprestaurant_android.ui.screens.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Whatsapp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sebasdev.apprestaurant_android.R
import com.sebasdev.apprestaurant_android.ui.components.MyTopAppBar
import com.sebasdev.apprestaurant_android.ui.theme.ColorWhiteCustom

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
  navigationController: NavHostController,
) {
  val context = LocalContext.current

  Scaffold(
    topBar = {
      MyTopAppBar(navigationController, "Contactanos")
    },
    content = {
      Column(
        Modifier
          .fillMaxSize()
          .padding(15.dp),
      ) {
        Spacer(modifier = Modifier.size(60.dp))
        Image(
          painter = painterResource(id = R.drawable.logo),
          contentDescription = "logo",
          modifier = Modifier
            .fillMaxWidth()
            .size(240.dp),
          alignment = Alignment.Center
        )
        Spacer(modifier = Modifier.size(30.dp))
        Button(
          onClick = {
            openWhatsApp(context)
          },
          modifier = Modifier.fillMaxWidth(),
          colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF0dbe42),
            disabledContentColor = ColorWhiteCustom,
          ),
          shape = MaterialTheme.shapes.medium
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(5.dp)
          ) {
            Icon(imageVector = Icons.Outlined.Whatsapp, contentDescription = "whatsapp", tint = ColorWhiteCustom)
            Text(text = "Contactanos", color = ColorWhiteCustom)
          }

        }
      }
    },
  )
}

private fun openWhatsApp(context: Context) {
  val phoneNumber = "+51932495500"
  val intent = Intent(Intent.ACTION_VIEW)
  val message = "Hola, me gustaría hacer una consulta / reportar un problema / solicitar información. ¿Podrían ayudarme, por favor?"
  intent.data = Uri.parse("https://wa.me/$phoneNumber/?text=${Uri.encode(message)}")
  context.startActivity(intent)
}