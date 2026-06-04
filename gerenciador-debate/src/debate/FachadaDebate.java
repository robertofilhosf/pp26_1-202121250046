package debate;

public class FachadaDebate {
    private static FachadaDebate instance;
    private final ConfiguraTempo config;
    private final MediadorBase mediador;
    private final GerenciaPolitico gerenciador;
    private final GerenciaEleitor gerencia_eleitor;
    private final LogSistem log;

    private FachadaDebate() {
        this.config = new ConfiguraTempo();
        this.mediador = new MediarDebate();
        this.gerenciador = new GerenciaPolitico();
        this.gerencia_eleitor = new GerenciaEleitor();
        this.log = LogSistem.get_instance("debate.log");
    }

    public static FachadaDebate get_instance() {
        if (instance == null) {
            instance = new FachadaDebate();
        }
        return instance;
    }

    public void configuracao(int pergunta, int resposta, int replica, int treplica) {
        config.set_temp_pergunta(pergunta);
        config.set_temp_resposta(resposta);
        config.set_temp_replica(replica);
        config.set_temp_treplica(treplica);
        log.register_log("Configuração: pergunta=" + pergunta + "s, resposta=" + resposta
                + "s, réplica=" + replica + "s, tréplica=" + treplica + "s");
        System.out.println("Tempos configurados com sucesso.");
    }

    /**
     * Cadastra um político usando o padrão Builder (via GerenciaPolitico).
     */
    public void cadastrar_politicos(String nome, String partido, MediadorBase mediadorRef) {
        gerenciador.criar_politico(nome, partido, mediadorRef, gerencia_eleitor);
        log.register_log("Político cadastrado: " + nome + " (" + partido + ")");
        System.out.println("Político cadastrado: " + nome + " (" + partido + ")");
    }

    /**
     * Cadastra um político a partir de um protótipo existente (Prototype + Builder).
     * Clona o protótipo e aplica o novo nome e partido.
     */
    public void cadastrar_politico_de_prototipo(String nomePrototipo, String partidoPrototipo,
            String novoNome, String novoPartido) {
        ColaboradorPolitico prototipo = gerenciador.obter_politico(nomePrototipo, partidoPrototipo);
        if (prototipo == null) {
            System.out.println("Protótipo não encontrado: " + nomePrototipo + " (" + partidoPrototipo + ")");
            log.register_log("Cadastro por protótipo falhou: " + nomePrototipo
                    + " (" + partidoPrototipo + ") não encontrado.");
            return;
        }
        gerenciador.criar_politico_de_prototipo(prototipo, novoNome, novoPartido);
        log.register_log("Político cadastrado: " + novoNome + " (" + novoPartido
                + ") a partir de " + nomePrototipo + " (" + partidoPrototipo + ")");
        System.out.println("Político cadastrado: " + novoNome + " (" + novoPartido
                + ") — baseado em " + nomePrototipo + " (" + partidoPrototipo + ")");
    }

    public void cadastrar_eleitor(String nomeEleitor, String nomeCandidato, String partidoCandidato) {
        boolean cadastrado = gerencia_eleitor.cadastrar_eleitor(
                nomeEleitor, nomeCandidato, partidoCandidato, gerenciador);
        if (!cadastrado) {
            System.out.println("Não foi possível cadastrar o eleitor. Verifique o candidato ou se o nome já existe.");
            log.register_log("Cadastro de eleitor falhou: " + nomeEleitor
                    + " -> " + nomeCandidato + " (" + partidoCandidato + ")");
            return;
        }
        log.register_log("Eleitor cadastrado: " + nomeEleitor
                + " acompanha " + nomeCandidato + " (" + partidoCandidato + ")");
        System.out.println("Eleitor cadastrado: " + nomeEleitor
                + " receberá notificações de " + nomeCandidato + " (" + partidoCandidato + ").");
    }

    /**
     * Cadastra um eleitor a partir de um protótipo existente (Prototype + Builder).
     * Clona o eleitor protótipo e define novo nome e candidato.
     */
    public void cadastrar_eleitor_de_prototipo(String nomePrototipo, String novoNomeEleitor,
            String nomeCandidato, String partidoCandidato) {
        Eleitor prototipo = null;
        for (Eleitor e : gerencia_eleitor.get_eleitores()) {
            if (e.get_nome().equalsIgnoreCase(nomePrototipo)) {
                prototipo = e;
                break;
            }
        }
        if (prototipo == null) {
            System.out.println("Eleitor protótipo não encontrado: " + nomePrototipo);
            log.register_log("Cadastro de eleitor por protótipo falhou: " + nomePrototipo + " não encontrado.");
            return;
        }
        boolean cadastrado = gerencia_eleitor.cadastrar_eleitor_de_prototipo(
                prototipo, novoNomeEleitor, nomeCandidato, partidoCandidato, gerenciador);
        if (!cadastrado) {
            System.out.println("Não foi possível cadastrar o eleitor. Verifique o candidato ou se o nome já existe.");
            log.register_log("Cadastro de eleitor por protótipo falhou: " + novoNomeEleitor
                    + " -> " + nomeCandidato + " (" + partidoCandidato + ")");
            return;
        }
        log.register_log("Eleitor cadastrado: " + novoNomeEleitor
                + " acompanha " + nomeCandidato + " (" + partidoCandidato
                + ") — baseado em " + nomePrototipo);
        System.out.println("Eleitor cadastrado: " + novoNomeEleitor
                + " receberá notificações de " + nomeCandidato + " (" + partidoCandidato
                + ") — baseado em " + nomePrototipo + ".");
    }

    public void sorteio_inquiridor() {
        ColaboradorPolitico sorteado = gerenciador.sortear_politico();
        if (sorteado == null) {
            System.out.println("Nenhum político cadastrado para sorteio.");
            log.register_log("Sorteio falhou: nenhum político cadastrado.");
            return;
        }
        ((MediarDebate) mediador).set_inquiridor(sorteado);
        log.register_log("Inquiridor sorteado: " + sorteado.get_nome() + " (" + sorteado.get_partido() + ")");
        System.out.println("Inquiridor sorteado: " + sorteado.get_nome() + " (" + sorteado.get_partido() + ")");
    }

    public void escolher_inquirido(String nome, String partido) {
        ColaboradorPolitico politico = gerenciador.obter_politico(nome, partido);
        if (politico == null) {
            System.out.println("Político não encontrado: " + nome + " (" + partido + ")");
            log.register_log("Escolha de inquirido falhou: " + nome + " (" + partido + ") não encontrado.");
            return;
        }
        MediarDebate mediar = (MediarDebate) mediador;
        if (mediar.get_inquiridor() == null) {
            System.out.println("Defina o inquiridor antes de escolher o inquirido.");
            log.register_log("Escolha de inquirido falhou: inquiridor não definido.");
            return;
        }
        if (!mediar.set_inquirido(politico)) {
            System.out.println("O inquirido não pode ser a mesma pessoa que o inquiridor.");
            log.register_log("Escolha de inquirido falhou: mesma pessoa que o inquiridor ("
                    + politico.get_nome() + " / " + politico.get_partido() + ").");
            return;
        }
        log.register_log("Inquirido escolhido: " + politico.get_nome() + " (" + politico.get_partido() + ")");
        System.out.println("Inquirido definido: " + politico.get_nome() + " (" + politico.get_partido() + ")");
    }

    public void executa_debate(ConfiguraTempo configRef, LogSistem logRef) {
        if (!configRef.tempos_configurados()) {
            System.out.println("Configure os tempos do debate na opção 1 (mínimo 1 segundo por etapa).");
            log.register_log("Debate cancelado: tempos não configurados.");
            return;
        }
        System.out.println("Tempos do debate: " + configRef.resumo_tempos());
        mediador.debate(configRef, logRef);
    }

    public void acessar_log() {
        System.out.println("\n--- Histórico do log (" + log.get_file_path() + ") ---");
        log.get_logs_register();
    }

    public ConfiguraTempo get_config() {
        return config;
    }

    public MediadorBase get_mediador() {
        return mediador;
    }

    public GerenciaPolitico get_gerenciador() {
        return gerenciador;
    }

    public LogSistem get_log() {
        return log;
    }

    public GerenciaEleitor get_gerencia_eleitor() {
        return gerencia_eleitor;
    }
}
