package com.sebasdev.apprestaurant_android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.sebasdev.apprestaurant_android.domain.model.UserPreference
import com.sebasdev.apprestaurant_android.ui.data_store.PreferencesDataStore
import com.sebasdev.apprestaurant_android.ui.navigation.AppScreens


@Composable
fun Header(
  text: String,
  showProfile: Boolean = true,
  navigationController: NavHostController,
  preferencesDataStore: PreferencesDataStore
) {
  val userData: UserPreference by preferencesDataStore.getDataUser()
    .collectAsState(initial = UserPreference("", "", "", "", "", ""))

  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(text = text, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    if (showProfile) {
      Image(
        painter = rememberImagePainter(data = userData.profile, builder = {
          size(OriginalSize)
          crossfade(true)
        }),
        contentDescription = "avatar",
        modifier = Modifier
          .size(50.dp)
          .clip(CircleShape)
          .clickable {
            navigationController.navigate(AppScreens.ProfileScreen.route)
          },
      )
    }
  }
}