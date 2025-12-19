package com.helio.ecodesafios.data.model

data class Desafio(
    val id: Int,
    val titulo: String,
    val descricao: String,
    val categoria: String,
    val pontos: Int,
    val concluido: Boolean = false
)


