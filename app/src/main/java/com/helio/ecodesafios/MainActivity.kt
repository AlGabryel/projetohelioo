package com.helio.ecodesafios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.helio.ecodesafios.ui.navigation.EcoNavHost
import com.helio.ecodesafios.ui.theme.EcoTheme
import com.helio.ecodesafios.ui.viewmodel.EcoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcoAppRoot()
        }
    }
}

@Composable
fun EcoAppRoot(
    viewModel: EcoViewModel = viewModel()
) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()

    EcoTheme {
        Surface {
            EcoNavHost(
                navController = navController,
                uiState = uiState,
                onDesafioClick = { id ->
                    navController.navigate("detalhe/$id")
                },
                onConcluirDesafio = { id ->
                    viewModel.concluirDesafio(id)
                },
                onAtualizarClima = {
                    viewModel.carregarClima()
                },
                eventos = viewModel.eventos
            )
        }
    }
}


