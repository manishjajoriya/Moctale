package com.manishjajoriya.moctale.presentation.menuScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manishjajoriya.moctale.MainViewModel
import com.manishjajoriya.moctale.R
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun MenuScreen(paddingValues: PaddingValues, mainViewModel: MainViewModel) {

  val context = LocalContext.current

  Column(
      modifier = Modifier.padding(paddingValues).padding(12.dp).fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    TextButton(
        onClick = {
          mainViewModel.clearAuthToken()
          Toast.makeText(context, "You have been log out.", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier.fillMaxWidth(.9f).height(52.dp),
        colors =
            ButtonDefaults.textButtonColors()
                .copy(containerColor = Color(0xFFE07288).copy(.35f), contentColor = Color.White),
        shape = RoundedCornerShape(12.dp),
    ) {
      Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(R.drawable.ic_logout_icon),
            contentDescription = null,
            tint = Color(0xFFE07288),
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Logout",
            style = Typography.titleMedium.copy(fontSize = 18.sp),
        )
      }
    }
  }
}
