package br.edu.ifsp.scl.sc3044289.fasttripplanner.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifsp.scl.sc3044289.fasttripplanner.TripViewModel

/**
 * Tela 2 — Opções da Viagem
 *
 * Permite ao usuário escolher:
 *   - Tipo de hospedagem via RadioButtons (seleção exclusiva) (RF02)
 *   - Serviços adicionais via Checkboxes (múltipla seleção)  (RF02)
 *
 * O estado é mantido no [TripViewModel] (sobrevive a rotações — RNF05).
 *
 * @param viewModel  ViewModel compartilhado
 * @param onNavigate Callback para navegar à Tela 3
 * @param onBack     Callback para voltar à Tela 1
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelOptionsScreen(
    viewModel: TripViewModel,
    onNavigate: () -> Unit,
    onBack: () -> Unit
) {
    // Opções de hospedagem com seus multiplicadores exibidos
    val accommodationOptions = listOf(
        "Econômica" to "×1,0",
        "Conforto" to "×1,5",
        "Luxo" to "×2,2"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Opções da Viagem") },
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
            Text(text = "🏨", fontSize = 48.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Personalize sua viagem",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Card: Tipo de Hospedagem
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "TIPO DE HOSPEDAGEM",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // selectableGroup: semântica de grupo de opções exclusivas
                    Column(modifier = Modifier.selectableGroup()) {
                        Button(
                            onClick = {
                                viewModel.accommodation = "EconomicMode"
                                viewModel.hasTours = false
                                viewModel.isEconomicMode = true
                            }
                        ) {
                            Text(
                                text = "Modo Econômico"
                            )
                        }
                        accommodationOptions.forEach { (option, multiplier) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                                    // selectable: torna a linha inteira clicável (melhor UX)
                                    .selectable(
                                        selected = viewModel.accommodation == option,
                                        onClick = {
                                            if (!viewModel.isEconomicMode) viewModel.accommodation = option
                                                  },
                                        role = Role.RadioButton
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = viewModel.accommodation == option,
                                    onClick = null   // clique já tratado no Row
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "$option  ($multiplier)",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Card: Serviços Adicionais
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "SERVIÇOS ADICIONAIS",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Serviço: Transporte
                    ServiceCheckboxRow(
                        label = "Transporte",
                        sublabel = "+R$ 300,00 fixo",
                        checked = viewModel.hasTransport,
                        onCheckedChange = { viewModel.hasTransport = it }
                    )

                    // Serviço: Alimentação
                    ServiceCheckboxRow(
                        label = "Alimentação",
                        sublabel = "+R$ 50,00/dia",
                        checked = viewModel.hasFood,
                        onCheckedChange = { viewModel.hasFood = it }
                    )

                    // Serviço: Passeios
                    ServiceCheckboxRow(
                        label = "Passeios",
                        sublabel = "+R$ 120,00/dia",
                        checked = viewModel.hasTours,
                        onCheckedChange = { viewModel.hasTours = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botões de ação
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Botão Voltar
                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Text("← Voltar", fontSize = 15.sp)
                }

                // Botão Calcular
                Button(
                    onClick = onNavigate,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Text("Calcular", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// Componente reutilizável: linha com Checkbox + label + sublabel

/**
 * Linha de checkbox com label principal e sublabel de preço.
 * A linha inteira é clicável.
 *
 * @param label         Nome do serviço
 * @param sublabel      Descrição do preço
 * @param checked       Estado atual do checkbox
 * @param onCheckedChange Callback ao alternar
 */
@Composable
private fun ServiceCheckboxRow(
    label: String,
    sublabel: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .selectable(
                selected = checked,
                onClick = { onCheckedChange(!checked) },
                role = Role.Checkbox
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null,   // clique tratado no Row
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.secondary
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = sublabel,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}