package com.manishjajoriya.moctale.presentation.contentScreen.components

import android.content.Intent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.manishjajoriya.moctale.R
import com.manishjajoriya.moctale.model.content.TicketingSite

@Composable
fun TicketButton(ticketingSite: TicketingSite) {
  val context = LocalContext.current
  TextButton(
      onClick = {
        val intent = Intent(Intent.ACTION_VIEW, ticketingSite.url.toUri())
        context.startActivity(intent)
      },
      shape = RoundedCornerShape(12.dp),
      colors =
          ButtonDefaults.buttonColors(
              containerColor = Color(0xFF474747),
              contentColor = Color.White,
          ),
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      AsyncImage(
          model = ticketingSite.image,
          contentDescription = null,
          modifier = Modifier.size(44.dp).clip(RoundedCornerShape(8.dp)),
      )
      Spacer(modifier = Modifier.width(4.dp))
      Text(text = ticketingSite.name)
      Icon(painter = painterResource(R.drawable.ic_link_icon), contentDescription = "open")
    }
  }
}
