package com.helio.ecodesafios.data.remote

import com.helio.ecodesafios.data.model.ClimaResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API simples usando Open-Meteo (sem necessidade de chave).
 * Exemplo de chamada: /v1/forecast?latitude=-8.05&longitude=-34.9&current_weather=true
 */
interface WeatherApi {

    @GET("v1/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current_weather") currentWeather: Boolean = true
    ): ClimaResponse
}


