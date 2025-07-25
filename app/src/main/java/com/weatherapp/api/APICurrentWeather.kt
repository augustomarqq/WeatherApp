package com.weatherapp.api
import com.weatherapp.model.Weather

data class APICondition(
    var text: String? = null,
    var icon: String? = null
)

data class APIWeather(
    var last_updated: String? = null,
    var temp_c: Double? = 0.0,
    var maxtemp_c: Double? = 0.0,
    var mintemp_c: Double? = 0.0,
    var condition: APICondition? = null
)
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
