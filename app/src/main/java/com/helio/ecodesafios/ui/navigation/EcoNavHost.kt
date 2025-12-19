package com.helio.ecodesafios.ui.navigation

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.helio.ecodesafios.ui.screens.DetalheDesafioScreen
import com.helio.ecodesafios.ui.screens.ListaDesafiosScreen
import com.helio.ecodesafios.ui.viewmodel.EcoUiEvent
import com.helio.ecodesafios.ui.viewmodel.EcoUiState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

object EcoRoutes {
    const val LISTA = "lista"
    const val DETALHE = "detalhe/{id}"
}

@Composable
fun EcoNavHost(
    navController: NavHostController,
    uiState: EcoUiState,
    onDesafioClick: (Int) -> Unit,
    onConcluirDesafio: (Int) -> Unit,
    onAtualizarClima: () -> Unit,
    eventos: SharedFlow<EcoUiEvent>
) {
    val snackbarHostState = remember { SnackbarHostState() }

    // Tratamento de one-shot events (mensagens de erro, sucesso, etc.)
    LaunchedEffect(Unit) {
        eventos.collectLatest { evento ->
            when (evento) {
                is EcoUiEvent.Mensagem -> {
                    snackbarHostState.showSnackbar(evento.mensagem)
                }
                is EcoUiEvent.NavegarParaDetalhe -> {
                    navController.navigate("detalhe/${evento.id}")
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = EcoRoutes.LISTA
        ) {
            composable(EcoRoutes.LISTA) {
                ListaDesafiosScreen(
                    state = uiState,
                    onDesafioClick = onDesafioClick,
                    onConcluirDesafio = onConcluirDesafio,
                    onAtualizarClima = onAtualizarClima,
                    paddingValues = padding
                )
            }

            composable(EcoRoutes.DETALHE) { entry ->
                val id = entry.arguments?.getString("id")?.toIntOrNull() ?: 0
                val desafio = uiState.desafios.find { it.id == id }
                DetalheDesafioScreen(
                    desafio = desafio,
                    onConcluirDesafio = { onConcluirDesafio(id) },
                    onVoltar = { navController.popBackStack() },
                    paddingValues = padding
                )
            }
        }
    }
}


