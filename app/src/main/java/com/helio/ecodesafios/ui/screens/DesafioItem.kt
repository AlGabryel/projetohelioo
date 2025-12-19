package com.helio.ecodesafios.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.helio.ecodesafios.data.model.Desafio

@Composable
fun DesafioItem(
    desafio: Desafio,
    onClick: () -> Unit,
    onConcluir: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick)
            .semantics {
                contentDescription = "Desafio: ${desafio.titulo}, categoria ${desafio.categoria}"
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = desafio.titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = desafio.descricao,
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Categoria: ${desafio.categoria}",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = "Pontos: ${desafio.pontos}",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Button(
                onClick = onConcluir,
                enabled = !desafio.concluido,
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription =
                            if (desafio.concluido) {
                                "Desafio já concluído"
                            } else {
                                "Botão para concluir desafio sustentável"
                            }
                    }
            ) {
                Text(
                    text = if (desafio.concluido) "Concluído" else "Concluir desafio"
                )
            }
        }
    }
}


