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
import androidx.compose.material.icons.outlined.Person2
import androidx.compose.material.icons.outlined.Phone
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
import com.sebasdev.apprestaurant_android.ui.viewmodel.auth.RegisterViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navigationController: NavHostController, registerViewModel: RegisterViewModel) {
  Column(
    Modifier
      .fillMaxSize()
      .padding(15.dp),
  ) {
    Logo()
    Spacer(modifier = Modifier.size(30.dp))
    Content(navigationController, registerViewModel)
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
fun Content(navigationController: NavHostController, registerViewModel: RegisterViewModel) {
  val fullname: String by registerViewModel.fullname.observeAsState(initial = "")
  val phone: String by registerViewModel.phone.observeAsState(initial = "")
  val email: String by registerViewModel.email.observeAsState(initial = "")
  val password: String by registerViewModel.password.observeAsState(initial = "")
  val isButtonRegisterEnable: Boolean by registerViewModel.isButtonRegisterEnabled.observeAsState(
    initial = false
  )
  val isLoading: Boolean by registerViewModel.isLoading.observeAsState(initial = false)
  val messageResponse: String by registerViewModel.messageResponse.observeAsState(initial = "")
  val context = LocalContext.current

  Column {
    Text(text = "Registrate", fontWeight = FontWeight.Bold, fontSize = 30.sp)
    Spacer(modifier = Modifier.size(20.dp))
    Column(
      modifier = Modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(20.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      InputNames(fullname) { registerViewModel.onRegisterChanged(it, phone, email, password) }
      InputPhone(phone) { registerViewModel.onRegisterChanged(fullname, it, email, password) }
      InputEmail(email) { registerViewModel.onRegisterChanged(fullname, phone, it, password) }
      InputPassword(password) { registerViewModel.onRegisterChanged(fullname, phone, email, it) }
      RegisterButton(navigationController, isButtonRegisterEnable, isLoading, registerViewModel)

      LaunchedEffect(messageResponse) {
        if (messageResponse.isNotEmpty()) {
          Toast.makeText(context, messageResponse, Toast.LENGTH_SHORT).show()
          registerViewModel.clearMessageErrorResponse()
        }
      }
    }
  }
}

@ExperimentalMaterial3Api
@Composable()
private fun InputNames(fullname: String, onTextChanged: (String) -> Unit) {
  TextField(
    value = fullname,
    onValueChange = { onTextChanged(it) },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    singleLine = true,
    maxLines = 1,
    modifier = Modifier.fillMaxWidth(),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.Transparent
    ),
    placeholder = {
      Text(text = "Ingresa tu nombre completo")
    },
    leadingIcon = {
      Icon(imageVector = Icons.Outlined.Person2, contentDescription = "names")
    })
}

@ExperimentalMaterial3Api
@Composable()
private fun InputPhone(phone: String, onTextChanged: (String) -> Unit) {
  TextField(
    value = phone,
    onValueChange = { onTextChanged(it) },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
    singleLine = true,
    maxLines = 1,
    modifier = Modifier.fillMaxWidth(),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.Transparent
    ),
    placeholder = {
      Text(text = "Ingresa tu número de teléfono")
    },
    leadingIcon = {
      Icon(imageVector = Icons.Outlined.Phone, contentDescription = "phone")
    })
}

@ExperimentalMaterial3Api
@Composable()
private fun InputEmail(email: String, onTextChanged: (String) -> Unit) {
  TextField(
    value = email,
    onValueChange = { onTextChanged(it) },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
    singleLine = true,
    maxLines = 1,
    modifier = Modifier.fillMaxWidth(),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.Transparent
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
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    singleLine = true,
    maxLines = 1,
    modifier = Modifier.fillMaxWidth(),
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
fun RegisterButton(
  navigationController: NavHostController,
  isButtonRegisterEnable: Boolean,
  isLoading: Boolean,
  registerViewModel: RegisterViewModel
) {
  Button(
    enabled = isButtonRegisterEnable,
    onClick = { registerViewModel.onSubmitRegister() },
    modifier = Modifier.fillMaxWidth(),
    shape = MaterialTheme.shapes.medium,
    colors = ButtonDefaults.buttonColors(
      disabledContainerColor = Color(0xFFE4575F),
      disabledContentColor = ColorWhiteCustom,
    ),
  ) {
    if (isLoading) {
      CircularProgressIndicator(
        color = ColorWhiteCustom,
        modifier = Modifier.size(30.dp)
      )
    } else {
      Text(text = "Registrarme", modifier = Modifier.padding(5.dp))
    }
  }
  Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
    Text(
      text = "Tienes una cuenta? ",
      fontWeight = FontWeight.Medium,
      color = MaterialTheme.colorScheme.secondary
    )
    Text(
      text = "Inicia Sesión",
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.primary,
      modifier = Modifier.clickable {
        navigationController.navigate(AppScreens.LoginScreen.route)
      })
  }
}