package com.sebasdev.apprestaurant_android.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AllInbox
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.MarkunreadMailbox
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.sebasdev.apprestaurant_android.domain.model.UserPreference
import com.sebasdev.apprestaurant_android.ui.components.Header
import com.sebasdev.apprestaurant_android.ui.components.NavigationBottomBar
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import com.sebasdev.apprestaurant_android.ui.navigation.AppScreens
import com.sebasdev.apprestaurant_android.ui.theme.ColorBlackCustom
import com.sebasdev.apprestaurant_android.ui.theme.ColorWhiteCustom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
  navigationController: NavHostController,
  preferencesDataStore: PreferencesDataStore
) {
  val userData: UserPreference by preferencesDataStore.getDataUser()
    .collectAsState(initial = UserPreference("", "", "", "", "", ""))

  Scaffold(
    content = {
      Column(
        Modifier
          .fillMaxSize()
          .padding(15.dp),
      ) {
        Header("Perfil", false, navigationController, preferencesDataStore)
        Spacer(modifier = Modifier.size(20.dp))
        Profile(userData.profile, userData.fullname, navigationController)
        Spacer(modifier = Modifier.size(20.dp))
        OptionsProfile(navigationController, preferencesDataStore)
      }
    },
    bottomBar = { NavigationBottomBar(navigationController, 3) }
  )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Profile(profile: String, fullname: String, navigationController: NavHostController) {
  Column(
    Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top
  ) {
    Image(
      painter = rememberImagePainter(data = profile.ifEmpty { "https://www.tenforums.com/geek/gars/images/2/types/thumb_14400082930User.png" }, builder = {
        size(OriginalSize)
        crossfade(true)
      }),
      contentScale = ContentScale.Crop,
      contentDescription = "avatar",
      modifier = Modifier
        .size(200.dp)
        .clip(CircleShape),
    )
    IconButton(
      onClick = { navigationController.navigate(AppScreens.ProfileUpdateScreen.route) },
      modifier = Modifier
        .offset(x = 0.dp, y = (-30).dp)
        .size(50.dp)
        .clip(
          CircleShape
        ),
      colors = IconButtonDefaults.iconButtonColors(
        containerColor = MaterialTheme.colorScheme.primary,
      )
    ) {
      Icon(
        imageVector = Icons.Outlined.Edit,
        contentDescription = "edit profile",
        tint = ColorWhiteCustom
      )
    }
    Text(
      text = fullname, fontSize = 25.sp, fontWeight = FontWeight.Bold, modifier = Modifier
        .offset(x = 0.dp, y = (-30).dp)
    )
  }
}

@Composable
fun OptionsProfile(navigationController: NavHostController, preferencesDataStore: PreferencesDataStore) {
  Column(
    horizontalAlignment = Alignment.Start,
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    TextButton(
      onClick = {
        navigationController.navigate(AppScreens.OrderHistoryScreen.route)
      },
      colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = Color.Gray
      ),
      shape = RoundedCornerShape(0.dp)
    ) {
      Row(
        Modifier
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Outlined.AllInbox,
          contentDescription = "historial de pedidos",
          tint = ColorBlackCustom,
          modifier = Modifier.size(25.dp)
        )
        Text(
          text = "Historial de pedidos",
          color = ColorBlackCustom,
          fontSize = 20.sp,
          fontWeight = FontWeight.Normal
        )
      }
    }
    Divider(
      thickness = 1.5.dp,
      color = Color(0xFFE0E0E0)
    )
    TextButton(
      onClick = {
        navigationController.navigate(AppScreens.OrderRecentsScreen.route)
      },
      colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = Color.Gray
      ),
      shape = RoundedCornerShape(0.dp)
    ) {
      Row(
        Modifier
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically

      ) {
        Icon(
          imageVector = Icons.Outlined.MarkunreadMailbox,
          contentDescription = "pedidos recientes",
          tint = ColorBlackCustom,
          modifier = Modifier.size(25.dp)
        )
        Text(
          text = "Pedidos recientes",
          color = ColorBlackCustom,
          fontSize = 20.sp,
          fontWeight = FontWeight.Normal
        )
      }
    }
    Divider(
      thickness = 1.5.dp,
      color = Color(0xFFE0E0E0)
    )
    TextButton(
      onClick = {
        navigationController.navigate(AppScreens.ContactScreen.route)
      },
      colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = Color.Gray
      ),
      shape = RoundedCornerShape(0.dp)
    ) {
      Row(
        Modifier
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Outlined.Phone,
          contentDescription = "contactanos",
          tint = ColorBlackCustom,
          modifier = Modifier.size(25.dp)
        )
        Text(
          text = "Contactanos",
          color = ColorBlackCustom,
          fontSize = 20.sp,
          fontWeight = FontWeight.Normal
        )
      }
    }
    Divider(
      thickness = 1.5.dp,
      color = Color(0xFFE0E0E0)
    )
    TextButton(
      onClick = {
        CoroutineScope(Dispatchers.Main).launch {
          preferencesDataStore.clearUserData()
          navigationController.navigate(AppScreens.LoginScreen.route)
        }
      },
      colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = Color.Gray
      ),
      shape = RoundedCornerShape(0.dp)
    ) {
      Row(
        Modifier
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Outlined.ExitToApp,
          contentDescription = "contactanos",
          tint = ColorBlackCustom,
          modifier = Modifier.size(25.dp)
        )
        Text(
          text = "Salir",
          color = ColorBlackCustom,
          fontSize = 20.sp,
          fontWeight = FontWeight.Normal
        )
      }
    }
  }
}