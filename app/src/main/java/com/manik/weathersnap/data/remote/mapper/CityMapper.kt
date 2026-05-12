package com.manik.weathersnap.data.remote.mapper

import com.manik.weathersnap.data.remote.dto.CityDto
import com.manik.weathersnap.domain.model.City

fun CityDto.toCity(): City {
    return City(
        name = name,
        latitude = latitude,
        longitude = longitude,
        country = country,
        admin1 = admin1
    )
}
