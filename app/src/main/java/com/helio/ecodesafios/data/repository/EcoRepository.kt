package com.helio.ecodesafios.data.repository

import com.helio.ecodesafios.data.model.ClimaResponse
import com.helio.ecodesafios.data.model.Desafio
import com.helio.ecodesafios.data.remote.RetrofitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EcoRepository {

    // Lista fixa de desafios locais (poderia vir de uma API/BD)
    private val desafiosIniciais = listOf(
        Desafio(
            id = 1,
            titulo = "Economizar água no banho",
            descricao = "Reduza o tempo do seu banho para no máximo 5 minutos hoje.",
            categoria = "Água",
            pontos = 10
        ),
        Desafio(
            id = 2,
            titulo = "Separar lixo reciclável",
            descricao = "Separe plástico, papel, metal e vidro do lixo comum.",
            categoria = "Resíduos",
            pontos = 15
        ),
        Desafio(
            id = 3,
            titulo = "Usar transporte sustentável",
            descricao = "Faça um trajeto do dia a pé, de bicicleta ou transporte público.",
            categoria = "Mobilidade",
            pontos = 20
        ),
        Desafio(
            id = 4,
            titulo = "Desligar aparelhos da tomada",
            descricao = "Ao sair de casa, desligue aparelhos em stand-by da tomada.",
            categoria = "Energia",
            pontos = 8
        )
    )

    suspend fun carregarDesafios(): List<Desafio> = withContext(Dispatchers.IO) {
        desafiosIniciais
    }

    suspend fun carregarClimaAtual(
        latitude: Double = -8.05,   // Exemplo: Recife
        longitude: Double = -34.9
    ): Result<ClimaResponse> = withContext(Dispatchers.IO) {
        return@withContext try {
            val resposta = RetrofitProvider.weatherApi.getCurrentWeather(
                latitude = latitude,
                longitude = longitude
            )
            Result.success(resposta)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


