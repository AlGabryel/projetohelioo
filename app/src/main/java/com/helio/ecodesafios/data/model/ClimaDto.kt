package com.helio.ecodesafios.data.model

/**
 * Modelo simples baseado na API pública Open-Meteo
 * Documentação: https://open-meteo.com/
 */
data class ClimaResponse(
    val current_weather: CurrentWeather?
)

data class CurrentWeather(
    val temperature: Double?,
    val windspeed: Double?
)


