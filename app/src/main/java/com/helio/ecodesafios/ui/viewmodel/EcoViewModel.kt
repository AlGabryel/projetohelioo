package com.helio.ecodesafios.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helio.ecodesafios.data.model.Desafio
import com.helio.ecodesafios.data.repository.EcoRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class EcoUiState(
    val carregando: Boolean = false,
    val desafios: List<Desafio> = emptyList(),
    val temperaturaAtual: Double? = null,
    val ventoAtual: Double? = null,
    val erro: String? = null
)

sealed class EcoUiEvent {
    data class Mensagem(val mensagem: String) : EcoUiEvent()
    data class NavegarParaDetalhe(val id: Int) : EcoUiEvent()
}

class EcoViewModel(
    private val repository: EcoRepository = EcoRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(EcoUiState(carregando = true))
    val uiState: StateFlow<EcoUiState> = _uiState.asStateFlow()

    // One-shot events (mensagens, navegação)
    private val _eventos = MutableSharedFlow<EcoUiEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val eventos: SharedFlow<EcoUiEvent> = _eventos.asSharedFlow()

    init {
        carregarDadosIniciais()
    }

    private fun carregarDadosIniciais() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(carregando = true, erro = null)
            try {
                val desafios = repository.carregarDesafios()
                _uiState.value = _uiState.value.copy(
                    carregando = false,
                    desafios = desafios
                )
                carregarClima()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    carregando = false,
                    erro = "Erro ao carregar desafios"
                )
                _eventos.emit(EcoUiEvent.Mensagem("Erro ao carregar desafios"))
            }
        }
    }

    fun carregarClima() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(carregando = true, erro = null)
            val resultado = repository.carregarClimaAtual()
            resultado
                .onSuccess { resposta ->
                    val temp = resposta.current_weather?.temperature
                    val vento = resposta.current_weather?.windspeed
                    _uiState.value = _uiState.value.copy(
                        carregando = false,
                        temperaturaAtual = temp,
                        ventoAtual = vento
                    )
                    _eventos.emit(
                        EcoUiEvent.Mensagem(
                            "Clima atualizado: ${temp ?: "-"}°C, vento ${vento ?: "-"} km/h"
                        )
                    )
                }
                .onFailure {
                    _uiState.value = _uiState.value.copy(
                        carregando = false,
                        erro = "Erro ao carregar clima"
                    )
                    _eventos.emit(EcoUiEvent.Mensagem("Erro ao carregar clima"))
                }
        }
    }

    fun concluirDesafio(id: Int) {
        viewModelScope.launch {
            val listaAtualizada = _uiState.value.desafios.map { desafio ->
                if (desafio.id == id) desafio.copy(concluido = true) else desafio
            }
            _uiState.value = _uiState.value.copy(desafios = listaAtualizada)
            _eventos.emit(EcoUiEvent.Mensagem("Parabéns! Você concluiu um desafio sustentável."))
            _eventos.emit(EcoUiEvent.NavegarParaDetalhe(id))
        }
    }
}


