package com.sebasdev.apprestaurant_android.ui.screens.admin

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import com.sebasdev.apprestaurant_android.R
import com.sebasdev.apprestaurant_android.ui.components.Header
import com.sebasdev.apprestaurant_android.ui.components.MyTopAppBar
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import com.sebasdev.apprestaurant_android.ui.navigation.AppScreens
import com.sebasdev.apprestaurant_android.ui.theme.ColorWhiteCustom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagmentProductsAdminScreen(
  navigationController: NavHostController,
  preferencesDataStore: PreferencesDataStore
) {
  Scaffold(
    topBar = {
      MyTopAppBar(
        navigationController,
        "Gestionar productos"
      )
    },
    content = {
      Column(
        Modifier
          .fillMaxSize()
          .padding(15.dp),
      ) {
        Spacer(modifier = Modifier.size(60.dp))
        Spacer(modifier = Modifier.size(60.dp))
        CardCategoryOne(navigationController, preferencesDataStore)
        Spacer(modifier = Modifier.size(30.dp))
        CardCategoryTwo(navigationController, preferencesDataStore)
        Spacer(modifier = Modifier.size(30.dp))
        CardCategoryThree(navigationController, preferencesDataStore)
      }
    },
  )
}

@Composable
fun CardCategoryOne(navigationController: NavHostController, preferencesDataStore: PreferencesDataStore) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.primary
    )
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Card(
        colors = CardDefaults.cardColors(
          containerColor = ColorWhiteCustom
        ),
        modifier = Modifier.width(320.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
          Image(
            painter = painterResource(id = R.drawable.categoriapollo),
            contentDescription = "Products",
            modifier = Modifier
              .height(100.dp)
              .width(120.dp)
              .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
          )
          Text(text = "A la brasa", color = Color.Black, fontSize = 20.sp)
        }
      }

      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .fillMaxWidth()
      ) {
        Icon(
          imageVector = Icons.Outlined.ArrowForwardIos,
          contentDescription = "Next",
          Modifier
            .size(30.dp)
            .clickable {
              CoroutineScope(Dispatchers.Main).launch {
                preferencesDataStore.setCategoryProductAdmin("A la brasa")
                navigationController.navigate(AppScreens.ManagmentListItemAdminScreen.createRoute("products"))
              }
            }
        )
      }
    }
  }
}

@Composable
fun CardCategoryTwo(navigationController: NavHostController, preferencesDataStore: PreferencesDataStore) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.primary
    )
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Card(
        colors = CardDefaults.cardColors(
          containerColor = ColorWhiteCustom
        ),
        modifier = Modifier.width(320.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
          Image(
            painter = painterResource(id = R.drawable.categoriaparrilla),
            contentDescription = "Products",
            modifier = Modifier
              .height(100.dp)
              .width(120.dp)
              .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
          )
          Text(text = "Parrillas", color = Color.Black, fontSize = 20.sp)
        }
      }

      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .fillMaxWidth()
      ) {
        Icon(
          imageVector = Icons.Outlined.ArrowForwardIos,
          contentDescription = "Next",
          Modifier
            .size(30.dp)
            .clickable {
              CoroutineScope(Dispatchers.Main).launch {
                preferencesDataStore.setCategoryProductAdmin("Parrillas")
                navigationController.navigate(AppScreens.ManagmentListItemAdminScreen.createRoute("products"))
              }
            }
        )
      }
    }
  }
}

@Composable
fun CardCategoryThree(navigationController: NavHostController, preferencesDataStore: PreferencesDataStore) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.primary
    )
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Card(
        colors = CardDefaults.cardColors(
          containerColor = ColorWhiteCustom
        ),
        modifier = Modifier.width(320.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
          Image(
            painter = painterResource(id = R.drawable.categoriabebidas),
            contentDescription = "Bebidas",
            modifier = Modifier
              .height(100.dp)
              .width(120.dp)
              .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
          )
          Text(text = "Bebidas", color = Color.Black, fontSize = 20.sp)
        }
      }

      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .fillMaxWidth()
      ) {
        Icon(
          imageVector = Icons.Outlined.ArrowForwardIos,
          contentDescription = "Next",
          Modifier
            .size(30.dp)
            .clickable {
              CoroutineScope(Dispatchers.Main).launch {
                preferencesDataStore.setCategoryProductAdmin("Bebidas")
                navigationController.navigate(AppScreens.ManagmentListItemAdminScreen.createRoute("products"))
              }
            }
        )
      }
    }
  }
}