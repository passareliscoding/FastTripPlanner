package br.edu.ifsp.scl.sc3044289.fasttripplanner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * ViewModel compartilhado entre todas as telas (Composables).
 *
 * Mantém o estado do formulário mesmo após rotação de tela (RNF05),
 * pois o ViewModel sobrevive a recomposições e recriações de Activity.
 *
 * Centraliza também a lógica de cálculo do custo total (RF05).
 */
class TripViewModel : ViewModel() {

    // Estado da Tela 1 — Dados da Viagem

    /** Destino informado pelo usuário */
    var destination by mutableStateOf("")

    /** Número de dias (armazenado como String para o campo de texto) */
    var days by mutableStateOf("")

    /** Orçamento diário em R$ (armazenado como String para o campo de texto) */
    var dailyBudget by mutableStateOf("")

    // Estado da Tela 2 — Opções da Viagem

    /**
     * Tipo de hospedagem selecionado.
     * Valores possíveis: "Econômica", "Conforto", "Luxo"
     */
    var accommodation by mutableStateOf("Econômica")

    var isEconomicMode by mutableStateOf(false)

    /** Serviço de transporte selecionado (+R$ 300 fixo) */
    var hasTransport by mutableStateOf(false)

    /** Serviço de alimentação selecionado (+R$ 50/dia) */
    var hasFood by mutableStateOf(false)

    /** Serviço de passeios selecionado (+R$ 120/dia) */
    var hasTours by mutableStateOf(false)

    // Lógica de cálculo — Tela 3 (RF05)

    /**
     * Calcula o custo total da viagem com base em todos os estados atuais.
     *
     * Regras:
     *   custoBase = dias × orçamentoDiário
     *   custoAjustado = custoBase × multiplicadorHospedagem
     *   extras = transporte + alimentação×dias + passeios×dias
     *   total = custoAjustado + extras
     *
     * @return [TripCost] com todos os valores detalhados, ou null se os campos forem inválidos
     */
    fun calculateCost(): TripCost? {
        val daysInt = days.toIntOrNull() ?: return null
        val budgetDbl = dailyBudget.toDoubleOrNull() ?: return null

        if (daysInt <= 0 || budgetDbl <= 0.0) return null

        val baseCost = daysInt.toDouble() * budgetDbl

        val multiplier = when (accommodation) {
            "Econômica" -> 1.0
            "Conforto" -> 1.5
            "Luxo" -> 2.2
            "EconomicMode" -> 0.85
            else -> 1.0
        }

        val adjustedBase = baseCost * multiplier

        var extras = 0.0
        if (hasTransport) extras += 300.0
        if (hasFood) extras += 50.0 * daysInt
        if (hasTours) extras += 120.0 * daysInt

        return TripCost(
            baseCost = baseCost,
            multiplier = multiplier,
            adjustedBase = adjustedBase,
            extras = extras,
            total = adjustedBase + extras
        )
    }

    /**
     * Reinicia todos os campos para os valores padrão.
     * Chamado quando o usuário pressiona "Novo Planejamento" na Tela 3.
     */
    fun reset() {
        destination = ""
        days = ""
        dailyBudget = ""
        accommodation = "Econômica"
        hasTransport = false
        hasFood = false
        hasTours = false
    }
}

// Data class com o resultado detalhado do cálculo

/**
 * Representa o resultado completo do cálculo do custo da viagem.
 *
 * @property baseCost     Custo bruto antes do multiplicador (dias × orçamento)
 * @property multiplier   Multiplicador de hospedagem aplicado (1.0, 1.5 ou 2.2)
 * @property adjustedBase Custo após o multiplicador de hospedagem
 * @property extras       Soma de todos os serviços adicionais
 * @property total        Valor final (adjustedBase + extras)
 */
data class TripCost(
    val baseCost: Double,
    val multiplier: Double,
    val adjustedBase: Double,
    val extras: Double,
    val total: Double
)