package com.helio.ecodesafios.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.helio.ecodesafios.data.model.Desafio
import com.helio.ecodesafios.ui.viewmodel.EcoUiState

@Composable
fun ListaDesafiosScreen(
    state: EcoUiState,
    onDesafioClick: (Int) -> Unit,
    onConcluirDesafio: (Int) -> Unit,
    onAtualizarClima: () -> Unit,
    paddingValues: PaddingValues
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Eco Desafios do Dia",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            ClimaAtualSection(
                temperatura = state.temperaturaAtual,
                vento = state.ventoAtual,
                carregando = state.carregando,
                onAtualizarClima = onAtualizarClima
            )

            if (state.carregando && state.desafios.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.semantics {
                            contentDescription = "Carregando desafios sustentáveis"
                        }
                    )
                }
            } else {
                ListaDesafiosContent(
                    desafios = state.desafios,
                    onDesafioClick = onDesafioClick,
                    onConcluirDesafio = onConcluirDesafio
                )
            }
        }
    }
}

@Composable
private fun ClimaAtualSection(
    temperatura: Double?,
    vento: Double?,
    carregando: Boolean,
    onAtualizarClima: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Clima agora (para planejar hábitos sustentáveis):",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Temperatura: ${temperatura?.let { "$it °C" } ?: "--"}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Vento: ${vento?.let { "$it km/h" } ?: "--"}",
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            onClick = onAtualizarClima,
            enabled = !carregando,
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "Botão para atualizar informação de clima"
                }
        ) {
            Text(text = "Atualizar clima")
        }
    }
}

@Composable
private fun ListaDesafiosContent(
    desafios: List<Desafio>,
    onDesafioClick: (Int) -> Unit,
    onConcluirDesafio: (Int) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(desafios) { desafio ->
            DesafioItem(
                desafio = desafio,
                onClick = { onDesafioClick(desafio.id) },
                onConcluir = { onConcluirDesafio(desafio.id) }
            )
        }
    }
}


