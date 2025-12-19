## Eco Desafios Sustentáveis

Aplicativo Android em Jetpack Compose focado em **hábitos sustentáveis do dia a dia**.  
O app lista desafios ecológicos (economizar água, energia, reduzir resíduos, mobilidade sustentável) e integra com uma **API pública de clima (Open‑Meteo)** para ajudar o usuário a planejar ações sustentáveis conforme o tempo atual.

### Integrantes da equipe

- Substitua por: **Nomes completos dos integrantes** e RAs/matrículas.

### Problema e solução proposta

- **Problema**: muitas pessoas querem adotar hábitos sustentáveis, mas não sabem por onde começar ou não mantêm regularidade.
- **Solução**: app que sugere desafios ecológicos diários, mostra pontuação por desafio concluído e exibe o clima atual para apoiar escolhas como andar a pé/bicicleta, secar roupa ao ar livre etc.

### Tecnologias utilizadas

- **Linguagem**: Kotlin
- **UI**: Jetpack Compose + Material3
- **Arquitetura**: MVVM
- **Reatividade**: `StateFlow` (estado de tela) + `SharedFlow` (one‑shot events)
- **Navegação**: Navigation Compose
- **Assíncrono**: Coroutines (`viewModelScope`, `suspend` functions)
- **Rede**: Retrofit + OkHttp + Moshi
- **Acessibilidade**:
  - `contentDescription` em botões e itens de lista
  - Cores com bom contraste (tons de verde sobre fundo claro/escuro)
  - Componentes grandes e fáceis de tocar; layout responsivo em diferentes tamanhos de tela

### Estrutura de pastas (principal)

- `app/src/main/java/com/helio/ecodesafios/`
  - `EcoApp.kt` – classe `Application`
  - `MainActivity.kt` – Activity principal com `setContent` e `EcoAppRoot`
  - `data/`
    - `model/Desafio.kt` – modelo dos desafios
    - `model/ClimaDto.kt` – modelos para resposta da API de clima
    - `remote/WeatherApi.kt` – interface Retrofit da Open‑Meteo
    - `remote/RetrofitProvider.kt` – criação do Retrofit/OkHttp
    - `repository/EcoRepository.kt` – regras de negócio e acesso a dados
  - `ui/`
    - `navigation/EcoNavHost.kt` – Navigation Compose (lista ↔ detalhe)
    - `screens/ListaDesafiosScreen.kt` – lista de desafios + clima
    - `screens/DesafioItem.kt` – item da lista
    - `screens/DetalheDesafioScreen.kt` – detalhes do desafio
    - `theme/Theme.kt` e `theme/Type.kt` – tema do app (Material3)
    - `viewmodel/EcoViewModel.kt` – ViewModel com `StateFlow` e `SharedFlow`

### Arquitetura e comunicação (MVVM + StateFlow/SharedFlow)

- **View (Compose)** consome apenas o `EcoUiState`:
  - `ListaDesafiosScreen` e `DetalheDesafioScreen` recebem o estado por parâmetro.
- **ViewModel (`EcoViewModel`)**:
  - expõe `uiState: StateFlow<EcoUiState>` com:
    - `carregando`, `desafios`, `temperaturaAtual`, `ventoAtual`, `erro`
  - expõe `eventos: SharedFlow<EcoUiEvent>` para:
    - mensagens únicas (snackbar) e navegação para detalhes.
  - usa `viewModelScope.launch` para chamar funções `suspend` do repositório.
- **Repository (`EcoRepository`)**:
  - fornece lista fixa de desafios sustentáveis.
  - chama a API Open‑Meteo via Retrofit e retorna `Result<ClimaResponse>`.

### One‑shot events

- Implementados com `MutableSharedFlow<EcoUiEvent>` com `replay = 0`.
- O `EcoNavHost` escuta o fluxo e:
  - mostra `Snackbar` para mensagens (erro, sucesso, clima atualizado).
  - faz navegação para tela de detalhes quando necessário.

### Como rodar o projeto

1. **Abrir no Android Studio**  
   - `File` → `Open...` → selecione a pasta `helioatvfinal`.
2. **Sincronizar Gradle**  
   - Aguarde o Android Studio baixar dependências.
3. **Executar**  
   - Escolha um emulador ou dispositivo físico.
   - Clique em **Run 'app'**.

> Observação: é necessário ter Android Studio atualizado (AGP 8.x, Kotlin 1.9+) e JDK 17 configurado.

### Funcionalidades principais

- **Lista de desafios sustentáveis** com título, descrição, categoria e pontos.
- **Conclusão de desafio**:
  - botão “Concluir desafio” marca o item como concluído e emite mensagem de parabéns.
- **Tela de detalhes**:
  - mostra informações completas do desafio selecionado.
- **Clima atual (API Open‑Meteo)**:
  - exibe temperatura e velocidade do vento.
  - botão “Atualizar clima” faz nova chamada à API usando Retrofit + coroutines.

### Acessibilidade e usabilidade

- `contentDescription` em:
  - botão “Atualizar clima”
  - itens da lista de desafios
  - botão de concluir desafio nas telas de lista e detalhes
- Contraste de cores pensado para boa legibilidade em temas claro/escuro.
- Layout em `LazyColumn`/`Column` com espaçamento amplo e fontes Material3, favorecendo leitura e navegação por toque/gestos.

### Como gerar APK (para entrega)

1. No Android Studio, abra `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`.
2. Ao final, clique no link mostrado na barra inferior para abrir a pasta com o APK.
3. Copie o APK para uma pasta `release/` no repositório ou envie o link no README.

### GitHub e colaboração sugerida

- **Branches**:
  - `main` – versão estável para apresentação.
  - `feature/ui`, `feature/network`, `feature/viewmodel` – features específicas.
- **Pull Requests**:
  - cada integrante cria PR com sua parte, revisado por outro colega.
- **Commits**:
  - mensagens claras, em português ou inglês, explicando o que foi alterado.


