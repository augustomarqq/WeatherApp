package com.weatherapp.ui

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherapp.model.City
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.weatherapp.MainViewModel
import com.weatherapp.R
import com.weatherapp.ui.nav.Route

@Composable fun ListPage(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val cityList = viewModel.cities
    val activity = LocalContext.current as? Activity
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(cityList, key = { it.name }) { city ->
            LaunchedEffect(city.name) {
                if (city.weather == null) {
                    viewModel.loadWeather(city.name)
                }
            }
            CityItem(city = city, onClose = {
                viewModel.remove(city)
                Toast.makeText(activity, "${city.name} excluída", Toast.LENGTH_LONG).show()
            }, onClick = {
                viewModel.city = city
                viewModel.page = Route.Home
                Toast.makeText(activity, "${city.name} selecionada", Toast.LENGTH_LONG).show()
            })
        }
    }
}

@Composable
fun CityItem( city: City,
              onClick: () -> Unit,
              onClose: () -> Unit,
              modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(8.dp).clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = city.weather?.imgUrl,
            modifier = Modifier.size(75.dp),
            error = painterResource(id = R.drawable.loading),
            contentDescription = "Imagem"
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = city.name,
                    fontSize = 22.sp,
                    modifier = Modifier.weight(1f, fill = false)
                )

                val monitorIcon = if (city.isMonitored) {
                    Icons.Filled.Notifications
                } else {
                    Icons.Outlined.Notifications
                }
                Icon(
                    imageVector = monitorIcon,
                    contentDescription = if (city.isMonitored) "Monitorada" else "Não monitorada",
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = city.weather?.desc ?: "carregando...",
                fontSize = 14.sp
            )
        }
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}