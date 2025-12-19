package com.helio.ecodesafios.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.helio.ecodesafios.data.model.Desafio

@Composable
fun DetalheDesafioScreen(
    desafio: Desafio?,
    onConcluirDesafio: () -> Unit,
    onVoltar: () -> Unit,
    paddingValues: PaddingValues
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        if (desafio == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Desafio não encontrado.",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Button(onClick = onVoltar) {
                    Text(text = "Voltar")
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = desafio.titulo,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Categoria: ${desafio.categoria}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Pontos: ${desafio.pontos}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = desafio.descricao,
                    style = MaterialTheme.typography.bodyLarge
                )

                Button(
                    onClick = onConcluirDesafio,
                    enabled = !desafio.concluido,
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics {
                            contentDescription = "Botão para concluir desafio na tela de detalhes"
                        }
                ) {
                    Text(
                        text = if (desafio.concluido) "Desafio já concluído" else "Concluir desafio"
                    )
                }

                Button(
                    onClick = onVoltar,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Voltar")
                }
            }
        }
    }
}


