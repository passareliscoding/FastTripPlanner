package br.edu.ifsp.scl.sc3044289.fasttripplanner.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifsp.scl.sc3044289.fasttripplanner.TripViewModel

/**
 * Tela 1 — Dados da Viagem
 *
 * Composable que coleta do usuário:
 *   - Destino da viagem
 *   - Número de dias
 *   - Orçamento diário (R$)
 *
 * O estado fica no [TripViewModel], que sobrevive a rotações (RNF05).
 * Validação completa antes de liberar a navegação (RF01).
 *
 * @param viewModel  ViewModel compartilhado com as demais telas
 * @param onNavigate Callback chamado após validação bem-sucedida → navega para Tela 2
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelDataScreen(
    viewModel: TripViewModel,
    onNavigate: () -> Unit
) {
    // Erros de validação — estado local à tela, não precisa sobreviver a rotação
    var destinationError by remember { mutableStateOf<String?>(null) }
    var daysError by remember { mutableStateOf<String?>(null) }
    var budgetError by remember { mutableStateOf<String?>(null) }

    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dados da Viagem") },
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
            Text(text = "✈️", fontSize = 56.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Planeje sua viagem",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Card com os campos de entrada 
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    // Campo: Destino (RF01)
                    OutlinedTextField(
                        value = viewModel.destination,
                        onValueChange = {
                            viewModel.destination = it
                            destinationError = null   // limpa erro ao digitar
                        },
                        label = { Text("Destino") },
                        placeholder = { Text("Ex: Paris, França") },
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                        isError = destinationError != null,
                        supportingText = destinationError?.let { msg ->
                            { Text(msg, color = MaterialTheme.colorScheme.error) }
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Campo: Número de dias (RF01)
                    OutlinedTextField(
                        value = viewModel.days,
                        onValueChange = {
                            viewModel.days = it
                            daysError = null
                        },
                        label = { Text("Número de dias") },
                        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                        isError = daysError != null,
                        supportingText = daysError?.let { msg ->
                            { Text(msg, color = MaterialTheme.colorScheme.error) }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Campo: Orçamento diário (RF01)
                    OutlinedTextField(
                        value = viewModel.dailyBudget,
                        onValueChange = {
                            viewModel.dailyBudget = it
                            budgetError = null
                        },
                        label = { Text("Orçamento diário (R$)") },
                        leadingIcon = { Icon(Icons.Default.Star, contentDescription = null) },
                        isError = budgetError != null,
                        supportingText = budgetError?.let { msg ->
                            { Text(msg, color = MaterialTheme.colorScheme.error) }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        ),
                        prefix = { Text("R$ ") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botão Próximo 
            Button(
                onClick = {
                    // Valida e navega se tudo estiver correto (RF01)
                    if (validate(
                            destination = viewModel.destination,
                            daysStr = viewModel.days,
                            budgetStr = viewModel.dailyBudget,
                            setDestError = { destinationError = it },
                            setDaysError = { daysError = it },
                            setBudgetError = { budgetError = it }
                        )
                    ) {
                        onNavigate()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Próximo  →", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// Função de validação

/**
 * Valida os três campos da Tela 1.
 * Chama os callbacks de erro para exibir mensagens inline nos campos.
 *
 * @return true se todos os campos são válidos
 */
private fun validate(
    destination: String,
    daysStr: String,
    budgetStr: String,
    setDestError: (String?) -> Unit,
    setDaysError: (String?) -> Unit,
    setBudgetError: (String?) -> Unit
): Boolean {
    var valid = true

    if (destination.isBlank()) {
        setDestError("Informe o destino da viagem")
        valid = false
    }

    val days = daysStr.toIntOrNull()
    if (daysStr.isBlank()) {
        setDaysError("Informe o número de dias")
        valid = false
    } else if (days == null || days <= 0) {
        setDaysError("O número de dias deve ser maior que zero")
        valid = false
    }

    val budget = budgetStr.toDoubleOrNull()
    if (budgetStr.isBlank()) {
        setBudgetError("Informe o orçamento diário")
        valid = false
    } else if (budget == null || budget <= 0.0) {
        setBudgetError("O orçamento deve ser maior que zero")
        valid = false
    }

    return valid
}