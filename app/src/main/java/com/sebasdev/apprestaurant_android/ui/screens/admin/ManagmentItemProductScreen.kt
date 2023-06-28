package com.sebasdev.apprestaurant_android.ui.screens.admin

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.sebasdev.apprestaurant_android.R
import com.sebasdev.apprestaurant_android.data.remote.model.request.CreateProductRequest
import com.sebasdev.apprestaurant_android.ui.components.MyTopAppBar
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.ProductDetailViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagmentItemProductScreen(
  navigationController: NavHostController,
  productDetailViewModel: ProductDetailViewModel,
  id: String,
  option: String,
) {
  val imageProduct: String by productDetailViewModel.image.observeAsState("")
  val nameProduct: String by productDetailViewModel.name.observeAsState("")
  val descriptionProduct: String by productDetailViewModel.description.observeAsState("")
  val priceProduct: String by productDetailViewModel.price.observeAsState("")
  val categoryProduct: String by productDetailViewModel.category.observeAsState("")
  val message: String by productDetailViewModel.message.observeAsState("")
  val context = LocalContext.current


  LaunchedEffect(true) {
    productDetailViewModel.getCategoryAdmin()
    if (id != "0") {
      productDetailViewModel.getProductById(id)
    }
  }

  LaunchedEffect(message) {
    if (message.isNotEmpty()) {
      Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
      productDetailViewModel.clearMessage()
    }
  }

  Scaffold(
    topBar = {
      MyTopAppBar(
        navigationController,
        if (option == "add") "Agregar producto" else "Editar producto"
      )
    },
  ) {
    Column(
      Modifier
        .fillMaxSize()
        .padding(15.dp),
    ) {
      Spacer(modifier = Modifier.size(60.dp))
      ImageProduct(imageProduct, option = option)
      Spacer(modifier = Modifier.size(20.dp))
      Column(
        Modifier
          .fillMaxWidth()
          .height(320.dp)
          .verticalScroll(rememberScrollState())
          .padding(bottom = 30.dp),
      ) {
        InputNameProduct(nameProduct) {
          productDetailViewModel.formProductChanged(
            it,
            descriptionProduct,
            priceProduct.toString(),
            categoryProduct,
            imageProduct
          )
        }
        Spacer(modifier = Modifier.size(20.dp))
        InputDescriptionProduct(descriptionProduct) {
          productDetailViewModel.formProductChanged(
            nameProduct,
            it,
            priceProduct.toString(),
            categoryProduct,
            imageProduct
          )
        }
        Spacer(modifier = Modifier.size(20.dp))
        InputPriceProduct(priceProduct.toString()) {
          productDetailViewModel.formProductChanged(
            nameProduct,
            descriptionProduct,
            it,
            categoryProduct,
            imageProduct
          )
        }
        Spacer(modifier = Modifier.size(20.dp))
        InputCategoryProduct(categoryProduct)
      }
      Column(
        Modifier.fillMaxWidth()
      ) {
        ButtonProduct(option) {
          if (option == "add") productDetailViewModel.addProduct() else productDetailViewModel.updateProduct(
            id
          )
        }

        if (option != "add") {
          ButtonDeleteProduct(navigationController) { productDetailViewModel.deleteProduct(id) }
        }
      }
    }
  }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ImageProduct(
  image: String,
  option: String
) {
  Column(
    Modifier
      .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    if (option == "add") {
      Image(
        painter = painterResource(id = R.drawable.pollo),
        contentScale = ContentScale.Crop,
        contentDescription = "avatar",
        modifier = Modifier
          .size(200.dp)
          .clip(CircleShape)
      )
    } else {
      Image(
        painter = rememberImagePainter(data = image, builder = {
          size(OriginalSize)
          crossfade(true)
        }),
        contentScale = ContentScale.Crop,
        contentDescription = "avatar",
        modifier = Modifier
          .size(200.dp)
          .clip(CircleShape)
      )
    }

  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputNameProduct(name: String, onTextChanged: (String) -> Unit) {
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
      Text(text = "Nombre")
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputDescriptionProduct(description: String, onTextChanged: (String) -> Unit) {
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
      Text(text = "Descripción")
    })
}

@ExperimentalMaterial3Api
@Composable()
private fun InputPriceProduct(price: String, onTextChanged: (String) -> Unit) {
  TextField(
    value = price,
    onValueChange = { onTextChanged(it) },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    singleLine = true,
    maxLines = 1,
    modifier = Modifier.fillMaxWidth(),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.Transparent
    ),
    label = {
      Text(text = "Precio")
    },
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputCategoryProduct(category: String) {
  TextField(
    enabled = false,
    value = category,
    onValueChange = { },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    singleLine = true,
    maxLines = 1,
    modifier = Modifier.fillMaxWidth(),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.Transparent
    ),
    label = {
      Text(text = "Categoria")
    })
}

@Composable
private fun ButtonProduct(option: String, clickButton: () -> Unit) {
  Button(
    onClick = {
      clickButton()
    }, modifier = Modifier
      .fillMaxWidth()
  ) {
    Text(
      text = if (option == "add") "Agregar producto" else "Actualizar producto",
      fontSize = 18.sp
    )
  }
}

@Composable
private fun ButtonDeleteProduct(
  navigationController: NavHostController,
  onDeleteProduct: () -> Unit
) {
  Button(
    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
    onClick = {
      onDeleteProduct()
      navigationController.popBackStack()
    },
    modifier = Modifier
      .fillMaxWidth()
  ) {
    Text(
      text = "Eliminar producto",
      fontSize = 18.sp
    )
  }
}