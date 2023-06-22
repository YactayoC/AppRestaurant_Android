package com.sebasdev.apprestaurant_android.ui.screens.main

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person2
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.sebasdev.apprestaurant_android.ui.components.MyTopAppBar
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import com.sebasdev.apprestaurant_android.ui.theme.ColorWhiteCustom
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.ProfileUpdateViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileUpdateScreen(
  navigationController: NavHostController,
  preferencesDataStore: PreferencesDataStore,
  profileUpdateViewModel: ProfileUpdateViewModel
) {

  val fullname: String by profileUpdateViewModel.fullname.observeAsState(initial = "")
  val phone: String by profileUpdateViewModel.phone.observeAsState(initial = "")
  val email: String by profileUpdateViewModel.email.observeAsState(initial = "")
  val password: String by profileUpdateViewModel.password.observeAsState(initial = "")
  val direction: String by profileUpdateViewModel.direction.observeAsState(initial = "")
  val district: String by profileUpdateViewModel.district.observeAsState(initial = "Villa el Salvador")
  val profile: String by profileUpdateViewModel.profile.observeAsState(initial = "")
  val message: String by profileUpdateViewModel.message.observeAsState(initial = "")
  val selectedImageUri: Uri? by profileUpdateViewModel.selectedImageUri.observeAsState(initial = null)
  val context = LocalContext.current

  LaunchedEffect(true) {
    //ofileUpdateViewModel.onSelectedImageUriChanged(Uri.parse(userData.profile))
    profileUpdateViewModel.onLoadUserData()
  }

  LaunchedEffect(message) {
    if (message.isNotEmpty()) {
      Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
      profileUpdateViewModel.clearMessage()
    }
  }

  Scaffold(
    topBar = {
      MyTopAppBar(navigationController, "Editar perfil")
    },
    content = {
      Column(
        Modifier
          .fillMaxSize()
          .padding(15.dp)
      ) {
        Spacer(modifier = Modifier.size(55.dp))
        Profile(
          profile = profile,
          selectedImageUri = selectedImageUri as Uri?,
        ) { profileUpdateViewModel.onSelectedImageUriChanged(it) }
        Spacer(modifier = Modifier.size(20.dp))
        Column(
          Modifier
            .fillMaxWidth()
            .height(420.dp)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 30.dp),
        ) {

          InputNames(fullname) { profileUpdateViewModel.onProfileUpdateChange(it, phone, email, password, direction, district)}
          Spacer(modifier = Modifier.size(20.dp))
          InputPhone(phone)  { profileUpdateViewModel.onProfileUpdateChange(fullname, it, email, password, direction, district)}
          Spacer(modifier = Modifier.size(20.dp))
          InputEmail(email)  { profileUpdateViewModel.onProfileUpdateChange(fullname, phone, it, password, direction, district)}
          Spacer(modifier = Modifier.size(20.dp))
          InputPassword(password)  { profileUpdateViewModel.onProfileUpdateChange(fullname, phone, email, it, direction, district)}
          Spacer(modifier = Modifier.size(20.dp))
          InputDirection(direction)  { profileUpdateViewModel.onProfileUpdateChange(fullname, phone, email, password, it, district)}
          Spacer(modifier = Modifier.size(20.dp))
          InputDistrict(district)  { profileUpdateViewModel.onProfileUpdateChange(fullname, phone, email, password, direction, it)}
        }
        ButtonUpdateProfile(true, false) { profileUpdateViewModel.onUpdateUser() }
      }
    },
  )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun Profile(profile: String, selectedImageUri: Uri?, onImageSelected: (Uri) -> Unit) {

  val launcher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent(),
    onResult = { uri: Uri? ->
      uri?.let { onImageSelected(it) }
    }
  )

  Column(
    Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top
  ) {
    val imageModifier = Modifier
      .size(200.dp)
      .clip(CircleShape)
      .clickable {
        launcher.launch("image/*")
      }

    if (selectedImageUri != null) {
      Image(
        painter = rememberImagePainter(data = selectedImageUri),
        contentDescription = "avatar",
        contentScale = ContentScale.Crop,
        modifier = imageModifier
      )
    } else {
      Image(
        painter = rememberImagePainter(data = profile.ifEmpty { "https://www.tenforums.com/geek/gars/images/2/types/thumb_14400082930User.png" }, builder = {
          size(OriginalSize)
          crossfade(true)
        }),
        contentScale = ContentScale.Crop,
        contentDescription = "avatar",
        modifier = imageModifier
      )
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
    enabled = false,
    value = email,
    onValueChange = {onTextChanged(it) },
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

@ExperimentalMaterial3Api
@Composable()
private fun InputDirection(direction: String, onTextChanged: (String) -> Unit) {
  TextField(
    value = direction,
    onValueChange = { onTextChanged(it) },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    singleLine = true,
    maxLines = 1,
    modifier = Modifier.fillMaxWidth(),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.Transparent
    ),
    placeholder = {
      Text(text = "Dirección")
    },
    leadingIcon = {
      Icon(imageVector = Icons.Outlined.LocationOn, contentDescription = "direction")
    })
}

@ExperimentalMaterial3Api
@Composable()
private fun InputDistrict(district: String, onTextChanged: (String) -> Unit) {
  TextField(
    value = district,
    enabled = false,
    onValueChange = { onTextChanged(it) },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    singleLine = true,
    maxLines = 1,
    modifier = Modifier.fillMaxWidth(),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.Transparent
    ),
    placeholder = {
      Text(text = "Distrito")
    },
    leadingIcon = {
      Icon(imageVector = Icons.Outlined.LocationCity, contentDescription = "direction")
    })
}

@Composable()
fun ButtonUpdateProfile(
  isButtonRegisterEnable: Boolean,
  isLoading: Boolean,
  onUpdateUser: () -> Unit
) {
  Button(
    enabled = isButtonRegisterEnable,
    onClick = { onUpdateUser() },
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
      Text(text = "Actualizar perfil", modifier = Modifier.padding(5.dp))
    }
  }
}