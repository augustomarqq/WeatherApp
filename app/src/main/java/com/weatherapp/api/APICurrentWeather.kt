package com.weatherapp.api
import com.weatherapp.model.Weather

data class APICurrentWeather(
    var location: APILocation? = null,
    var current: APIWeather? = null
)

fun APICurrentWeather.toWeather(): Weather {
    return Weather(
        date = this.current?.last_updated ?: "...",
        desc = this.current?.condition?.text ?: "...",
        temp = this.current?.temp_c ?: -1.0,
        imgUrl = "https:" + current?.condition?.icon
    )
}
