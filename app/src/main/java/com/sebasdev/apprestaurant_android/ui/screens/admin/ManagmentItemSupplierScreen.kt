package com.sebasdev.apprestaurant_android.ui.screens.admin

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.sebasdev.apprestaurant_android.R
import com.sebasdev.apprestaurant_android.ui.components.MyTopAppBar
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.ProductDetailViewModel
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.SupplierDetailViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagmentItemSupplierScreen(
  navigationController: NavHostController,
  supplierDetailViewModel: SupplierDetailViewModel,
  id: String,
  option: String,
) {
  val nameSupplier: String by supplierDetailViewModel.name.observeAsState("")
  val rucSupplier: String by supplierDetailViewModel.ruc.observeAsState("")
  val phoneSupplier: String by supplierDetailViewModel.phone.observeAsState("")
  val directionSupplier: String by supplierDetailViewModel.direction.observeAsState("")
  val emailSupplier: String by supplierDetailViewModel.email.observeAsState("")
  val message: String by supplierDetailViewModel.message.observeAsState("")
  val context = LocalContext.current

  LaunchedEffect(true) {
    if (id != "0") {
      supplierDetailViewModel.getSupplierById(id)
    }
  }

  LaunchedEffect(message) {
    if (message.isNotEmpty()) {
      Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
      ).show()
    }
  }

  Scaffold(
    topBar = {
      MyTopAppBar(
        navigationController,
        if (option == "add") "Agregar proveedor" else "Editar proveedor"
      )
    },
  ) {
    Column(
      Modifier
        .fillMaxSize()
        .padding(15.dp),
    ) {
      Spacer(modifier = Modifier.size(60.dp))
      if (option == "add") {
        ImageSupplier()
      }
      Spacer(modifier = Modifier.size(20.dp))
      Column(
        Modifier
          .fillMaxWidth()
          .height(420.dp)
          .verticalScroll(rememberScrollState())
          .padding(bottom = 30.dp),
      ) {
        InputNameSupplier(nameSupplier) {
          supplierDetailViewModel.formSupplierChanged(
            it,
            rucSupplier,
            phoneSupplier,
            directionSupplier,
            emailSupplier
          )
        }
        Spacer(modifier = Modifier.size(20.dp))
        InputRucSupplier(rucSupplier) {
          supplierDetailViewModel.formSupplierChanged(
            nameSupplier,
            it,
            phoneSupplier,
            directionSupplier,
            emailSupplier
          )
        }
        Spacer(modifier = Modifier.size(20.dp))
        InputPhoneSupplier(phoneSupplier) {
          supplierDetailViewModel.formSupplierChanged(
            nameSupplier,
            rucSupplier,
            it,
            directionSupplier,
            emailSupplier
          )
        }
        Spacer(modifier = Modifier.size(20.dp))
        InputDirectionSupplier(directionSupplier) {
          supplierDetailViewModel.formSupplierChanged(
            nameSupplier,
            rucSupplier,
            phoneSupplier,
            it,
            emailSupplier
          )
        }
        Spacer(modifier = Modifier.size(20.dp))
        InputEmailSupplier(emailSupplier) {
          supplierDetailViewModel.formSupplierChanged(
            nameSupplier,
            rucSupplier,
            phoneSupplier,
            directionSupplier,
            it
          )
        }
      }
      Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
      ) {
        ButtonSupplier(option) {
          if (option == "add") {
            supplierDetailViewModel.addSupplier()
            supplierDetailViewModel.clearData()
          } else {
            supplierDetailViewModel.updateSupplier(id)
          }
        }
        ButtonDeleteSupplier(navigationController) { supplierDetailViewModel.deleteSupplier(id) }
      }
    }
  }
}

@Composable
fun ImageSupplier() {
  Column(
    Modifier
      .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      painter = painterResource(id = R.drawable.proveedor),
      contentScale = ContentScale.Crop,
      contentDescription = "avatar",
      modifier = Modifier
        .size(200.dp)
        .clip(CircleShape)
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputNameSupplier(name: String, onTextChanged: (String) -> Unit) {
  TextField(
    value = name.capitalize(),
    onValueChange = { onTextChanged(it) },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    singleLine = true,
    maxLines = 1,
    modifier = Modifier.fillMaxWidth(),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.Transparent
    ),
    label = {
      Text(text = "Nombre del proveedor")
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputRucSupplier(description: String, onTextChanged: (String) -> Unit) {
  TextField(
    value = description,
    onValueChange = { onTextChanged(it) },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    singleLine = true,
    maxLines = 1,
    modifier = Modifier.fillMaxWidth(),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.Transparent
    ),
    label = {
      Text(text = "RUC")
    })
}

@ExperimentalMaterial3Api
@Composable()
private fun InputPhoneSupplier(price: String, onTextChanged: (String) -> Unit) {
  TextField(
    value = price,
    onValueChange = { onTextChanged(it) },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
    singleLine = true,
    maxLines = 1,
    modifier = Modifier.fillMaxWidth(),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.Transparent
    ),
    label = {
      Text(text = "Celular")
    },
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputDirectionSupplier(description: String, onTextChanged: (String) -> Unit) {
  TextField(
    value = description,
    onValueChange = { onTextChanged(it) },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    singleLine = true,
    maxLines = 1,
    modifier = Modifier.fillMaxWidth(),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.Transparent
    ),
    label = {
      Text(text = "Dirección")
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputEmailSupplier(description: String, onTextChanged: (String) -> Unit) {
  TextField(
    value = description,
    onValueChange = { onTextChanged(it) },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
    singleLine = true,
    maxLines = 1,
    modifier = Modifier.fillMaxWidth(),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.Transparent
    ),
    label = {
      Text(text = "Email de contacto")
    })
}

@Composable
private fun ButtonSupplier(option: String, optionSupplier: () -> Unit) {
  Button(
    onClick = {
      optionSupplier()
    },
    modifier = Modifier
      .fillMaxWidth()
      .height(50.dp)
  ) {
    Text(
      text = if (option == "add") "Agregar proveedor" else "Actualizar proveedor",
      fontSize = 18.sp
    )
  }
}

@Composable
private fun ButtonDeleteSupplier(
  navigationController: NavHostController,
  deleteSupplier: () -> Unit
) {
  Button(
    onClick = {
      deleteSupplier()
      navigationController.popBackStack()
    }, modifier = Modifier
      .fillMaxWidth()
      .height(50.dp),
    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
  ) {
    Text(
      text = "Eliminar proveedor",
      fontSize = 18.sp
    )
  }
}