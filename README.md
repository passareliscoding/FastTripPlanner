# FastTripPlanner

FastTripPlanner é um aplicativo Android desenvolvido em Kotlin com o objetivo de planejar uma viagem simples a partir de dados informados pelo usuário. O projeto aplica conceitos de múltiplas telas, navegação entre Activities com Intents explícitas, validação de formulário, gerenciamento de estado e cálculo de custo total.

## Objetivo

Desenvolver um aplicativo Android funcional que integre os principais conceitos abordados na disciplina de desenvolvimento móvel, incluindo:

- Três telas implementadas como Activities.
- Navegação entre telas usando Intents explícitas.
- Envio de dados entre telas por extras da Intent.
- Gerenciamento de estado para preservar dados durante recomposições e rotação de tela.
- Validação de dados de entrada.
- Cálculo do custo total da viagem.

## Tecnologias Utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Material 3
- ViewModel
- Intents explícitas

## Restrições do Projeto

- O aplicativo contém três telas, cada uma representada por uma Activity.
- O projeto foi desenvolvido em Kotlin.
- Não utiliza listas, banco de dados ou APIs externas.
- Utiliza componentes básicos de interface.
- O código está organizado em Activities, telas Compose, tema e ViewModel.
- A entrega inclui o código-fonte completo e este README.

## Funcionalidades

### Tela 1 - Dados da Viagem

Activity inicial do aplicativo, responsável por coletar os dados principais da viagem.

Campos disponíveis:

- Destino
- Número de dias
- Orçamento diário

Funcionalidades:

- Valida se o destino foi informado.
- Valida se o número de dias é maior que zero.
- Valida se o orçamento diário é maior que zero.
- Avança para a segunda tela usando Intent explícita.
- Envia destino, dias e orçamento diário para a próxima Activity.

### Tela 2 - Opções da Viagem

Tela responsável pela personalização da viagem.

Opções de hospedagem:

- Econômica
- Conforto
- Luxo

Serviços adicionais:

- Transporte
- Alimentação
- Passeios

Funcionalidades:

- Permite escolher apenas um tipo de hospedagem.
- Permite selecionar múltiplos serviços adicionais.
- Botão "Calcular" envia todos os dados para a tela de resumo via Intent explícita.
- Botão "Voltar" retorna para a tela anterior.

### Tela 3 - Resumo da Viagem

Tela responsável por exibir o resumo completo do planejamento.

Funcionalidades:

- Exibe os dados inseridos na primeira tela.
- Exibe as opções selecionadas na segunda tela.
- Calcula e apresenta o custo total da viagem.
- Possui botão para reiniciar o planejamento.

## Requisitos Funcionais

| Código | Descrição |
| --- | --- |
| RF01 | Permitir inserir dados da viagem. |
| RF02 | Permitir selecionar opções adicionais. |
| RF03 | Exibir resumo completo. |
| RF04 | Realizar navegação via Intents explícitas. |
| RF05 | Calcular corretamente o custo total. |

## Requisitos Não Funcionais

| Código | Descrição |
| --- | --- |
| RNF01 | Ser compatível com Android 8.0 ou superior. |
| RNF02 | Possuir código organizado e comentado. |
| RNF03 | Utilizar boas práticas de desenvolvimento. |
| RNF04 | Entrega contendo README e vídeo. |
| RNF05 | Preservar estado na rotação da tela. |

## Regras de Cálculo

O cálculo do valor total da viagem segue as regras abaixo.

### Custo Base

```text
custoBase = dias * orçamentoDiario
```

### Multiplicador de Hospedagem

| Hospedagem | Multiplicador |
| --- | --- |
| Econômica | 1.0 |
| Conforto | 1.5 |
| Luxo | 2.2 |

```text
custoAjustado = custoBase * multiplicadorHospedagem
```

### Extras

| Serviço | Valor |
| --- | --- |
| Transporte | R$ 300,00 fixo |
| Alimentação | R$ 50,00 por dia |
| Passeios | R$ 120,00 por dia |

```text
extras = transporte + alimentação + passeios
total = custoAjustado + extras
```

## Fluxo de Navegação

O aplicativo utiliza Intents explícitas para navegar entre as Activities:

```text
MainActivity -> TravelOptionsActivity -> TravelSummaryActivity
```

Os dados são enviados entre as telas por meio de extras da Intent. Cada Activity recebe os dados necessários, carrega o estado no ViewModel e exibe sua respectiva tela.

## Estrutura do Projeto

```text
app/src/main/java/br/edu/ifsp/scl/sc3044289/fasttripplanner/
|-- MainActivity.kt
|-- TravelOptionsActivity.kt
|-- TravelSummaryActivity.kt
|-- TripViewModel.kt
|-- Navigation.kt
|-- ui/
    |-- screens/
    |   |-- TravelDataScreen.kt
    |   |-- TravelOptionsScreen.kt
    |   |-- TravelSummaryScreen.kt
    |-- theme/
```

## Como Executar

1. Abra o projeto no Android Studio.
2. Aguarde a sincronização do Gradle.
3. Selecione um emulador ou dispositivo físico com Android 8.0 ou superior.
4. Execute o aplicativo pelo botão Run.

