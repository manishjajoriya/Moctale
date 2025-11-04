package com.manishjajoriya.moctale.presentation.authScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.manishjajoriya.moctale.MainViewModel
import com.manishjajoriya.moctale.R
import com.manishjajoriya.moctale.navgraph.Routes
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun AuthScreen(
    viewModel: MainViewModel,
    navController: NavController,
) {
  var authToken by remember { mutableStateOf("") }
  val loading by viewModel.loading.collectAsState()
  val isLogin by viewModel.isLogin.collectAsState()
  val error by viewModel.error.collectAsState()
  var loginAttempted by remember { mutableStateOf(false) }

  if (isLogin == true) navController.navigate(Routes.ExploreScreen.route)

  Column(
      modifier = Modifier.fillMaxWidth().fillMaxHeight(.8f),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
  ) {
    Image(painter = painterResource(R.drawable.ic_moctale_logo), contentDescription = null)
    Spacer(modifier = Modifier.height(40.dp))
    Text(
        text = "--- Instruction to get your Auth Token: ---",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(.9f),
        style = Typography.bodyLarge.copy(Color.White.copy(.7f)),
    )
    Text(
        text =
            "1. Go to mocatle.in and login with your username and password\n" +
                "2. Right click and go to \"Inspect\" or press F12\n" +
                "3. Go to Storage > Cookies\n" +
                "4. Copy \"auth_token\" value and paste below",
        modifier = Modifier.fillMaxWidth(.9f),
        style = Typography.bodyLarge.copy(Color.White.copy(.7f)),
    )
    TextField(
        value = authToken,
        onValueChange = { input ->
          val lines = input.lines()
          authToken =
              if (lines.size > 3) {
                lines.joinToString("")
              } else input
          if (loginAttempted) loginAttempted = false
        },
        placeholder = {
          Text(
              text = "Your Auth Token",
              style = Typography.titleLarge.copy(color = Color.White.copy(.7f), fontSize = 18.sp),
          )
        },
        textStyle = Typography.titleLarge.copy(fontSize = 18.sp),
        shape = RoundedCornerShape(16.dp),
        minLines = 3,
        maxLines = 3,
        modifier = Modifier.fillMaxWidth(.9f).padding(top = 16.dp).height(120.dp),
        isError = loginAttempted && isLogin != true && !loading,
        colors =
            TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                errorTextColor = Color.Red,
                focusedContainerColor = Color(0xFF2A2A2A),
                unfocusedContainerColor = Color(0xFF2A2A2A),
            ),
    )
    error?.let { error ->
      Text(
          error,
          modifier = Modifier.fillMaxWidth(.9f).padding(top = 16.dp),
          textAlign = TextAlign.Center,
          style = Typography.bodyLarge.copy(color = Color.Red.copy(.9f)),
      )
    }
    Spacer(modifier = Modifier.height(16.dp))
    Box(
        modifier =
            Modifier.clip(RoundedCornerShape(24.dp))
                .clickable(
                    onClick = {
                      loginAttempted = true
                      viewModel.saveAuthToken(authToken)
                    }
                )
                .background(
                    brush =
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFFaf48ff), Color(0xFF8f44f0))
                        ),
                    shape = RoundedCornerShape(24.dp),
                )
                .padding(horizontal = 80.dp, vertical = 12.dp)
    ) {
      Text(text = "Login", style = Typography.titleMedium)
    }
  }
}
