# Debate Político — Documentação das Classes UML

## Visão Geral

O sistema simula um debate político entre colaboradores (políticos), com controle de tempo de fala, mediação, log de eventos e suporte a interfaces gráfica (GUI) e de linha de comando (CLI). O design utiliza os padrões **Singleton** (FachadaDebate, LogSistem), **Mediator** (MediadorBase / MediarDebate), e **Facade** (FachadaDebate).

---

## Classes

### `Debate`
Classe que representa o contexto geral do debate. Está associada ao `MediadorBase`.

---

### `ConfiguraTempo`
Armazena e gerencia os tempos (em segundos) de cada etapa do debate.

**Atributos:**
- `tempo_pergunta: int`
- `tempo_resposta: int`
- `tempo_replica: int`
- `tempo_treplica: int`

**Métodos:**
- `ConfiguraTempo()` — construtor
- `set_temp_pergunta(t: int)` / `get_temp_pergunta(): int`
- `set_temp_resposta(t: int)` / `get_temp_resposta(): int`
- `set_temp_replica(t: int)` / `get_temp_replica(): int`
- `set_temp_treplica(t: int)` / `get_temp_treplica(): int`

---

### `MediadorBase` (interface)
Interface base para o mediador do debate. Implementada por `MediarDebate`.

**Métodos:**
- `Debate()` — método abstrato para iniciar o debate

---

### `ColaboradorPolitico`
Representa um político participante do debate. Pode atuar como inquiridor ou inquirido. Implementa o papel genérico antes de ser especializado.

**Atributos:**
- `nome: str`
- `partido: str`
- `inquiridor: bool` — indica se é o inquiridor na rodada
- `microfone: Microfone` — microfone associado
- `mediador: MediadorBase` — referência ao mediador

**Métodos:**
- `ColaboradorPolitico(nome, partido)` — construtor; define `inquiridor = False`
- `OperacaoMediada()` — realiza operação mediada (delegada ao mediador)
- `set_nome(n: str)` / `get_nome(): str`
- `set_partido(p: str)` / `get_partido(): str`
- `set_mediador(Mediator)` — define o mediador
- `set_inquiridor(bool)` / `get_inquiridor()` — controla papel no debate

---

### `Inquiridor` *(especialização de ColaboradorPolitico)*
Papel do político que faz perguntas e réplicas.

**Métodos:**
- `escolha_inquirido(politico: ColaboradorPolitico)` — delega ao mediador a definição do inquirido
- `perguntar(tempo: int, log: LogSistem)` — liga microfone, aguarda o tempo de pergunta, desliga e registra no log
- `replica(tempo: int, log: LogSistem)` — liga microfone, aguarda o tempo de réplica, desliga e registra no log

---

### `Inquirido` *(especialização de ColaboradorPolitico)*
Papel do político que responde e faz tréplicas.

**Métodos:**
- `responder(tempo: int, log: LogSistem)` — liga microfone, aguarda o tempo de resposta, desliga e registra no log
- `treplica(tempo: int, log: LogSistem)` — liga microfone, aguarda o tempo de tréplica, desliga e registra no log

---

### `MediarDebate` *(implementa MediadorBase)*
Mediador concreto que orquestra o debate entre inquiridor e inquirido.

**Atributos:**
- `inquiridor: Inquiridor`
- `inquirido: Inquirido`

**Métodos:**
- `debate(config: ConfiguraTempo, log: LogSistem)` — executa o fluxo completo: pergunta → resposta → réplica → tréplica; registra início e fim no log
- `set_inquiridor(in: ColaboradorPolitico)` — cria instância de `Inquiridor` a partir do político
- `get_inquiridor(): Inquiridor`
- `set_inquirido(inqui: ColaboradorPolitico)` — cria instância de `Inquirido` a partir do político
- `get_inquirido(): Inquirido`

---

### `Microfone`
Representa o microfone de um político. Controla ativação e contagem de tempo.

**Atributos:**
- `MicroAtivo: bool`

**Métodos:**
- `liga()` — define `MicroAtivo = True`
- `desliga()` — define `MicroAtivo = False`
- `passa_tempo(tempo: int)` — decrementa o tempo até zero (simula a contagem regressiva)

---

### `GerenciaPolitico`
Gerencia a lista de políticos cadastrados no sistema.

**Atributos:**
- `politicos: List<ColaboradorPolitico>`
- `quant_politicos: int`

**Métodos:**
- `criar_politico(nome: str, partido: str, mediador: MediadorBase)` — instancia e adiciona um `ColaboradorPolitico`
- `obter_politico(nome: str, partido: str): ColaboradorPolitico` — busca um político pelo nome e partido
- `sortear_politico(): ColaboradorPolitico` — sorteia aleatoriamente um político que ainda não foi inquiridor (ou reinicia os flags se todos já foram); define o flag `inquiridor = True`

---

### `LogSistem` *(Singleton)*
Responsável pelo registro de eventos do sistema em arquivo.

**Atributos:**
- `instance: LogSistem` — instância única (Singleton)
- `file_path: string` — caminho do arquivo de log

**Métodos:**
- `LogSistem(file_path: str)` — construtor privado
- `get_instance(file: str): LogSistem` — retorna a instância única (cria se não existir e registra "Init")
- `get_file_path(): str`
- `register_log(msg: str)` — abre o arquivo, acrescenta a mensagem e fecha
- `get_logs_register(): str` — abre o arquivo em modo leitura, imprime e fecha

---

### `FachadaDebate` *(Singleton + Facade)*
Ponto único de entrada para todas as operações do sistema. Orquestra configuração, cadastro, sorteio, escolha e execução do debate.

**Atributos:**
- `instance: FachadaDebate` — instância única (Singleton)
- `config: ConfiguraTempo`
- `mediador: MediadorBase`
- `gerenciador: GerenciaPolitico`
- `log: LogSistem`

**Métodos:**
- `FachadaDebate()` — construtor privado; inicializa `config`, `mediador` (como `MediarDebate`), `gerenciador` e `log`
- `get_instance(): FachadaDebate` — retorna a instância única
- `configuracao(pergunta, resposta, replica, treplica: int)` — configura os tempos e registra no log
- `cadastrar_politicos(nome, partido: str, mediador: MediadorBase)` — delega ao gerenciador e registra no log
- `sorteio_inquiridor()` — sorteia e define o inquiridor via mediador; registra no log
- `escolher_inquirido(nome, partido: str)` — obtém o político e o define como inquirido via mediador; registra no log
- `executa_debate(config: ConfiguraTempo, log: LogSistem)` — delega ao mediador a execução do debate
- `acessar_log()` — exibe os registros do log

---

### `GUI`
Interface gráfica do usuário. Delega todas as operações à fachada.

**Atributos:**
- `f: FachadaDebate`

**Métodos:**
- `RealizarOperacao()` — executa uma operação via fachada

---

### `CLI`
Interface de linha de comando. Delega todas as operações à fachada.

**Atributos:**
- `f: FachadaDebate`

**Métodos:**
- `RealizarOperacao()` — executa uma operação via fachada

---

## Fluxo do Debate

1. `FachadaDebate.get_instance()` — obtém a instância única
2. `configuracao(...)` — define os tempos de cada etapa
3. `cadastrar_politicos(...)` — cadastra os políticos (1..n)
4. `sorteio_inquiridor()` — sorteia quem pergunta
5. `escolher_inquirido(...)` — define quem responde
6. `executa_debate(...)` — inicia o debate:
   - Inquiridor pergunta → Inquirido responde → Inquiridor replica → Inquirido tréplica
7. `acessar_log()` — consulta o histórico de eventos