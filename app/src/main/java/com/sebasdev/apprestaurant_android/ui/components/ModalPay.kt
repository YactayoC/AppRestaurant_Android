package com.sebasdev.apprestaurant_android.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person2
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.sebasdev.apprestaurant_android.R
import com.sebasdev.apprestaurant_android.ui.theme.ColorBlackCustom
import com.sebasdev.apprestaurant_android.ui.theme.ColorWhiteCustom
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.CartViewModel
import com.sebasdev.apprestaurant_android.ui.viewmodel.main.OrderViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ModalPay(cartViewModel: CartViewModel, subTotal: Double, orderViewModel: OrderViewModel) {
  LaunchedEffect(Unit) {
    cartViewModel.loadDataPay()
  }

  Column {
    Modal(cartViewModel, subTotal, orderViewModel)
  }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Modal(cartViewModel: CartViewModel, subTotal: Double, orderViewModel: OrderViewModel) {
  val direction: String by cartViewModel.direction.observeAsState(initial = "")
  val numberCard: String by cartViewModel.numberCard.observeAsState(initial = "")
  val dateCard: String by cartViewModel.dateCard.observeAsState(initial = "")
  val cvvCard: String by cartViewModel.cvvCard.observeAsState(initial = "")
  val fullnames: String by cartViewModel.fullnames.observeAsState(initial = "")
  val email: String by cartViewModel.email.observeAsState(initial = "")

  val totalAmount: Double by cartViewModel.totalAmount.observeAsState(initial = 0.0)
  val isButtonPayEnabled: Boolean by cartViewModel.isButtonPayEnabled.observeAsState(initial = false)

  LaunchedEffect(Unit) {
    cartViewModel.calculateTotal()
  }

  ModalDrawerSheet {
    AlertDialog(
      containerColor = Color.White,
      onDismissRequest = {},
      title = {
        Row {
          Image(
            painter = painterResource(id = R.drawable.visa),
            contentDescription = "Visa",
            modifier = Modifier.size(60.dp)
          )
          Image(
            painter = painterResource(id = R.drawable.mastercard),
            contentDescription = "Mastercard",
            modifier = Modifier.size(60.dp)
          )
        }
      },
      text = {
        Column(
          modifier = Modifier
            .fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          InputDirection(direction) {
            cartViewModel.formPayChange(
              it,
              numberCard,
              dateCard,
              cvvCard
            )
          }
          InputNumberCard(numberCard) {
            cartViewModel.formPayChange(
              direction,
              it,
              dateCard,
              cvvCard
            )
          }
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
          ) {
            InputDate(dateCard) { cartViewModel.formPayChange(direction, numberCard, it, cvvCard) }
            InputCVV(cvvCard) { cartViewModel.formPayChange(direction, numberCard, dateCard, it) }
          }
          InputFullNames(fullnames)
          InputEmail(email)
          CardTotal(
            subTotal,
            totalAmount,
            cartViewModel,
            isButtonPayEnabled,
            orderViewModel,
            direction
          )
        }

      },
      confirmButton = {

      },
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputDirection(direction: String, onTextChanged: (String) -> Unit) {
  TextField(
    value = direction,
    onValueChange = { onTextChanged(it) },
    modifier = Modifier.fillMaxWidth(),
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.Transparent
    ),
    label = {
      Text(text = "Direccion")
    },
    leadingIcon = {
      Icon(imageVector = Icons.Outlined.LocationOn, contentDescription = "direction")
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputNumberCard(numberCard: String, onTextChanged: (String) -> Unit) {
  TextField(
    value = numberCard,
    onValueChange = { onTextChanged(it) },
    modifier = Modifier.fillMaxWidth(),
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.Transparent
    ),
    label = {
      Text(text = "Numero de tarjeta")
    },
    leadingIcon = {
      Icon(imageVector = Icons.Outlined.CreditCard, contentDescription = "NumberCard")
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputDate(valueDate: String, onTextChanged: (String) -> Unit) {
  val calendarState = rememberSheetState()
  val selectedDate = remember { mutableStateOf<LocalDate?>(null) }

  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    CalendarDialog(
      state = calendarState,
      selection = CalendarSelection.Date { date ->
        selectedDate.value = date
        onTextChanged(date.toString())
      },
      config = CalendarConfig(
        maxYear = 2100,
        yearSelection = true,
        style = CalendarStyle.MONTH
      )
    )

    TextField(
      enabled = false,
      value = valueDate ?: "",
      onValueChange = { onTextChanged(it) },
      modifier = Modifier
        .width(150.dp)
        .clickable {
          calendarState.show()
        },
      colors = TextFieldDefaults.textFieldColors(
        containerColor = Color.Transparent,
        disabledLabelColor = Color.Black,
        textColor = Color.Black,
        disabledTextColor = Color.Black
      ),
      label = {
        Text(text = "MM/YY")
      },
      leadingIcon = {
        IconButton(onClick = {
          calendarState.show()
        }) {
          Icon(
            imageVector = Icons.Outlined.CalendarToday,
            contentDescription = "MM/YY",
            tint = Color.Black
          )
        }
      })
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputCVV(cvvCard: String, onTextChanged: (String) -> Unit) {
  TextField(
    value = cvvCard,
    onValueChange = { onTextChanged(it) },
    modifier = Modifier.width(110.dp),
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.Transparent
    ),
    label = {
      Text(text = "CVV")
    },
    leadingIcon = {
      Icon(imageVector = Icons.Outlined.CreditCard, contentDescription = "CVV")
    })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputFullNames(value: String) {
  TextField(
    enabled = false,
    value = value,
    onValueChange = { },
    modifier = Modifier.fillMaxWidth(),
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.Transparent
    ),
    label = {
      Text(text = "Nombres")
    },
    leadingIcon = {
      Icon(imageVector = Icons.Outlined.Person2, contentDescription = "fullnames")
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputEmail(value: String) {
  TextField(
    enabled = false,
    value = value,
    onValueChange = { },
    modifier = Modifier.fillMaxWidth(),
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
    colors = TextFieldDefaults.textFieldColors(
      containerColor = Color.Transparent
    ),
    label = {
      Text(text = "Email")
    },
    leadingIcon = {
      Icon(imageVector = Icons.Outlined.Email, contentDescription = "email")
    })
}

@Composable
private fun CardTotal(
  subTotal: Double,
  total: Double? = 0.0,
  cartViewModel: CartViewModel,
  isButtonPayEnabled: Boolean,
  orderViewModel: OrderViewModel,
  direction: String
) {
  Column(
    verticalArrangement = Arrangement.Bottom,
    modifier = Modifier
      .padding(top = 30.dp)
  ) {
    Card(
      colors = CardDefaults.cardColors(
        containerColor = ColorWhiteCustom,
      ),
      modifier = Modifier.fillMaxWidth(),
    ) {
      Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
      ) {
        Row(
          Modifier
            .fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(text = "Subtotal", color = ColorBlackCustom, fontSize = 18.sp)
          Text(text = "S/. ${subTotal ?: 0.0}", color = ColorBlackCustom, fontSize = 18.sp)
        }
        Row(
          Modifier
            .fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(text = "Costo de envio", color = ColorBlackCustom, fontSize = 18.sp)
          Text(text = "S/. 7.50", color = ColorBlackCustom, fontSize = 18.sp)
        }
        Row(
          Modifier
            .fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(text = "Total", color = ColorBlackCustom, fontSize = 18.sp)
          Text(text = "S/. $total", color = ColorBlackCustom, fontSize = 18.sp)
        }

        Button(
          enabled = isButtonPayEnabled,
          onClick = {
            cartViewModel.onPay()
            orderViewModel.addOrder(subTotal, direction)
//            addOrder() // Vienen de orderViewModel
          },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(5.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = ColorWhiteCustom
          )
        ) {
          Text(text = "Pagar ahora", fontSize = 15.sp, modifier = Modifier.padding(5.dp))
        }
        Button(
          onClick = { cartViewModel.setShowModal(false) },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(5.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = Color.Gray,
            contentColor = ColorWhiteCustom
          )
        ) {
          Text(text = "Cancelar", fontSize = 15.sp, modifier = Modifier.padding(5.dp))
        }
      }
    }
  }
}