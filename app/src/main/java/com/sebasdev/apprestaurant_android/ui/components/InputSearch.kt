package com.sebasdev.apprestaurant_android.ui.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sebasdev.apprestaurant_android.ui.navigation.AppScreens
import com.sebasdev.apprestaurant_android.ui.theme.ColorPlaceholder


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputSearch(
  valueSearch: String,
  navigationController: NavHostController,
  onValueSearchChanged: (String) -> Unit
) {
  TextField(
    value = valueSearch,
    onValueChange = { onValueSearchChanged(it) },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    singleLine = true,
    maxLines = 1,
    modifier = Modifier
      .fillMaxWidth()
      .border(0.dp, Color.Transparent)
      .shadow(5.dp, CircleShape),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color(0xFFFFFFFF),
      textColor = ColorPlaceholder,
      focusedIndicatorColor = Color.Transparent,
      unfocusedIndicatorColor = Color.Transparent,
      disabledIndicatorColor = Color.Transparent
    ),
    placeholder = {
      Text(text = "Que te gustaria comer hoy?")
    },
    leadingIcon = {
      Icon(
        imageVector = Icons.Outlined.Search,
        contentDescription = "search",
        tint = ColorPlaceholder
      )
    },
    keyboardActions = KeyboardActions(
      onDone = {
        if (valueSearch.isNotEmpty()) {
          navigationController.navigate(AppScreens.ResultSearchScreen.createRoute(valueSearch))
        } else {
          Log.d("InputSearch", "El campo de búsqueda está vacío")
        }
      })
  )
}