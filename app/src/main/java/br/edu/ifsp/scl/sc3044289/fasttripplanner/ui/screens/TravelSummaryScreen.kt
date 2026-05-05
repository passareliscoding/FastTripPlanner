package br.edu.ifsp.scl.sc3044289.fasttripplanner.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifsp.scl.sc3044289.fasttripplanner.TripViewModel
import br.edu.ifsp.scl.sc3044289.fasttripplanner.ui.theme.Green50
import br.edu.ifsp.scl.sc3044289.fasttripplanner.ui.theme.Green900
import java.text.NumberFormat
import java.util.Locale

/**
 * Tela 3 — Resumo da Viagem
 *
 * Exibe todos os dados inseridos nas telas anteriores e o custo total
 * calculado pelo [TripViewModel] (RF03, RF05).
 *
 * @param viewModel ViewModel compartilhado com os dados de todas as telas
 * @param onRestart Callback para reiniciar o planejamento (reseta e volta à Tela 1)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelSummaryScreen(
    viewModel: TripViewModel,
    onRestart: () -> Unit
) {
    // Formata valores como moeda brasileira
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    // Obtém o resultado do cálculo a partir do ViewModel (RF05)
    val cost = viewModel.calculateCost()

    val daysInt = viewModel.days.toIntOrNull() ?: 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resumo da Viagem") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Cabeçalho 
            Text(text = "📋", fontSize = 48.sp)

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Seu planejamento",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Card: Dados da Viagem 
            SummaryCard(title = "DADOS DA VIAGEM") {
                SummaryRow(icon = "🌍", label = "Destino", value = viewModel.destination)
                SummaryRow(icon = "📅", label = "Duração", value = "$daysInt ${if (daysInt == 1) "dia" else "dias"}")
                SummaryRow(
                    icon = "💰",
                    label = "Orçamento/dia",
                    value = currencyFormat.format(viewModel.dailyBudget.toDoubleOrNull() ?: 0.0)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Card: Opções Selecionadas
            SummaryCard(title = "OPÇÕES SELECIONADAS") {
                SummaryRow(
                    icon = "🏨",
                    label = "Hospedagem",
                    value = "${viewModel.accommodation} (×${cost?.multiplier ?: 1.0})"
                )

                // Monta lista de serviços selecionados
                val services = buildList {
                    if (viewModel.hasTransport) add("Transporte")
                    if (viewModel.hasFood) add("Alimentação")
                    if (viewModel.hasTours) add("Passeios")
                }
                SummaryRow(
                    icon = "🎒",
                    label = "Serviços",
                    value = if (services.isEmpty()) "Nenhum" else services.joinToString(", ")
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Card: Detalhes do Cálculo 
            SummaryCard(title = "DETALHES DO CÁLCULO") {
                if (cost != null) {
                    SummaryRow(
                        icon = "📊",
                        label = "Base ajustada",
                        value = currencyFormat.format(cost.adjustedBase)
                    )
                    Text(
                        text = "(${currencyFormat.format(viewModel.dailyBudget.toDouble())} × $daysInt dias × ${cost.multiplier})",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                    )
                    SummaryRow(
                        icon = "➕",
                        label = "Extras",
                        value = currencyFormat.format(cost.extras)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Destaque: Total 
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Green50, shape = MaterialTheme.shapes.medium)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "TOTAL DA VIAGEM",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Green900,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (cost != null) currencyFormat.format(cost.total) else "—",
                        fontSize = 38.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Green900
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botão: Novo Planejamento 
            Button(
                onClick = onRestart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("🔄  Novo Planejamento", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// Componentes reutilizáveis de sumário

/**
 * Card com título em caixa alta e conteúdo flexível via slot [content].
 */
@Composable
private fun SummaryCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

/**
 * Linha de resumo com ícone, label e valor alinhados.
 */
@Composable
private fun SummaryRow(icon: String, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$icon  $label",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}