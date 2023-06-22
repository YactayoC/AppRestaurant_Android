package com.sebasdev.apprestaurant_android.ui.screens.auth

import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sebasdev.apprestaurant_android.R
import com.sebasdev.apprestaurant_android.ui.navigation.AppScreens
import com.sebasdev.apprestaurant_android.ui.theme.ColorWhiteCustom
import com.sebasdev.apprestaurant_android.ui.viewmodel.auth.LoginViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navigationController: NavHostController, loginViewModel: LoginViewModel) {
  Column(
    Modifier
      .fillMaxSize()
      .padding(15.dp),
  ) {
    Logo()
    Spacer(modifier = Modifier.size(30.dp))
    Content(navigationController, loginViewModel)
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

@ExperimentalMaterial3Api
@Composable
fun Content(navigationController: NavHostController, loginViewModel: LoginViewModel) {
  val email: String by loginViewModel.email.observeAsState(initial = "sebas@gmail.com")
  val password: String by loginViewModel.password.observeAsState(initial = "sebas200")
  val isButtonLoginEnable: Boolean by loginViewModel.isButtonLoginEnabled.observeAsState(initial = false)
  val isLoading: Boolean by loginViewModel.isLoading.observeAsState(initial = false)
  val messageErrorResponse: String by loginViewModel.messageErrorResponse.observeAsState(initial = "")
  val context = LocalContext.current


  Column {
    Text(text = "Iniciar Sesión", fontWeight = FontWeight.Bold, fontSize = 30.sp)
    Spacer(modifier = Modifier.size(20.dp))
    Column(
      modifier = Modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(30.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      InputEmail(email) { loginViewModel.onLoginChanged(it, password) }
      InputPassword(password) { loginViewModel.onLoginChanged(email, it) }
      LoginButton(navigationController, isButtonLoginEnable, isLoading, loginViewModel)

      // Show error message
      LaunchedEffect(messageErrorResponse) {
        if (messageErrorResponse.isNotEmpty()) {
          Toast.makeText(context, messageErrorResponse, Toast.LENGTH_SHORT).show()
          loginViewModel.clearMessageErrorResponse()
        }
      }
    }
  }
}

@ExperimentalMaterial3Api
@Composable()
private fun InputEmail(email: String, onTextChanged: (String) -> Unit) {
  TextField(
    value = email,
    onValueChange = { onTextChanged(it) },
    modifier = Modifier.fillMaxWidth(),
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.Transparent,
    ),
    placeholder = {
      Text(text = "Ingresa tu email")
    },
    leadingIcon = {
      Icon(imageVector = Icons.Outlined.Email, contentDescription = "email")
    })
}

@ExperimentalMaterial3Api
@Composable()
private fun InputPassword(password: String, onTextChanged: (String) -> Unit) {
  TextField(
    value = password,
    onValueChange = { onTextChanged(it) },
    modifier = Modifier.fillMaxWidth(),
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    visualTransformation = PasswordVisualTransformation(),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.Transparent
    ),
    placeholder = {
      Text(text = "Ingresa tu contraseña")
    },
    leadingIcon = {
      Icon(imageVector = Icons.Outlined.Lock, contentDescription = "password")
    })
}

@Composable()
fun LoginButton(
  navigationController: NavHostController,
  isButtonLoginEnable: Boolean,
  isLoading: Boolean,
  loginViewModel: LoginViewModel
) {
  Button(
    enabled = isButtonLoginEnable,
    onClick = { loginViewModel.onSubmitLogin(navigationController) },
    modifier = Modifier.fillMaxWidth(),
    colors = ButtonDefaults.buttonColors(
      disabledContainerColor = Color(0xFFE4575F),
      disabledContentColor = ColorWhiteCustom,
    ),
    shape = MaterialTheme.shapes.medium
  ) {
    if (isLoading) {
      CircularProgressIndicator(
        color = ColorWhiteCustom,
        modifier = Modifier.size(30.dp)
      )
    } else {
      Text(text = "Iniciar Sesión", modifier = Modifier.padding(5.dp))
    }
  }
  Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
    Text(
      text = "No tienes una cuenta? ",
      fontWeight = FontWeight.Medium,
      color = MaterialTheme.colorScheme.secondary
    )
    Text(
      text = "Registrate",
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.primary,
      modifier = Modifier.clickable {
        navigationController.navigate(AppScreens.RegisterScreen.route)
      })
  }
}