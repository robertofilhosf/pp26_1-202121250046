package debate;

public class MediarDebate implements MediadorBase {
    private EstadoDebate estadoAtual;
    private Inquiridor inquiridor;
    private Inquirido inquirido;
    private GerenteDebate gerente;
    private final GerenciaDireitoResposta gerenciaDR;
    private ConfiguraTempo config;
    private LogSistem log;

    public MediarDebate() {
        this.gerenciaDR = new GerenciaDireitoResposta();
        this.gerente = new GerenteDebatePadrao(); // Padrão inicial
    }

    @Override
    public void debate(ConfiguraTempo config, LogSistem log) {
        if (inquiridor == null || inquirido == null) {
            System.out.println("Debate não pode iniciar: defina inquiridor e inquirido.");
            log.register_log("Debate cancelado: inquiridor ou inquirido não definido.");
            return;
        }

        if (inquiridor.eh_mesmo(inquirido)) {
            System.out.println("Debate não pode iniciar: inquiridor e inquirido não podem ser a mesma pessoa.");
            log.register_log("Debate cancelado: inquiridor e inquirido são a mesma pessoa.");
            return;
        }

        this.config = config;
        this.log = log;

        log.register_log("Início do debate: " + inquiridor.get_nome() + " x " + inquirido.get_nome());
        System.out.println("\n========== INÍCIO DO DEBATE ==========");
        System.out.println("Inquiridor: " + inquiridor.get_nome() + " (" + inquiridor.get_partido() + ")");
        System.out.println("Inquirido: " + inquirido.get_nome() + " (" + inquirido.get_partido() + ")");
        System.out.println("Duração das falas: " + config.resumo_tempos());

        // Vincula a gerênciaDR ao microfone de todos os políticos participantes
        inquiridor.set_mediador(this);
        inquirido.set_mediador(this);
        for (ColaboradorPolitico p : FachadaDebate.get_instance().get_gerenciador().get_politicos()) {
            p.set_mediador(this);
        }

        // Inicia com o EstadoPergunta
        this.estadoAtual = new EstadoPergunta();

        // Processa as etapas sequenciais (Pergunta -> Resposta -> Réplica -> Tréplica -> AnaliseDR)
        processarEstado(); // Pergunta
        processarEstado(); // Resposta
        processarEstado(); // Réplica
        processarEstado(); // Tréplica
        processarEstado(); // AnaliseDR -> vai para DireitoResposta ou volta para Pergunta

        // Se o estado passou a ser DireitoResposta, executa as defesas
        if (this.estadoAtual instanceof EstadoDireitoResposta) {
            processarEstado(); // DireitoResposta -> volta para Pergunta (fim do ciclo)
        }

        System.out.println("\n========== FIM DO DEBATE ==========");
        log.register_log("Fim do debate: " + inquiridor.get_nome() + " x " + inquirido.get_nome());
    }

    public void processarEstado() {
        if (estadoAtual != null) {
            estadoAtual.executar(this);
        }
    }

    public void setEstado(EstadoDebate e) {
        this.estadoAtual = e;
    }

    public EstadoDebate getEstado() {
        return estadoAtual;
    }

    public void avaliarSolicitacoesDR() {
        if (gerente != null) {
            gerente.avaliarFila(gerenciaDR);
        }
    }

    public void executarDireitosResposta() {
        GerenciaEleitor gerenciaEleitor = getGerenciaEleitor();
        while (gerenciaDR.possuiSolicitacoes()) {
            SolicitacaoDireitoResposta sol = gerenciaDR.proximaSolicitacao();
            if (sol == null || !sol.is_aprovado()) {
                continue;
            }

            ColaboradorPolitico politico = sol.get_politico();

            if (gerenciaEleitor != null) {
                gerenciaEleitor.notificar_inicio_fala(
                        politico.get_nome(),
                        politico.get_partido(),
                        "Direito de Resposta");
            }

            DefesaDireitoResposta defesa = new DefesaDireitoResposta(politico);
            defesa.executar(config.get_temp_direito_resposta(), log);
        }
        gerenciaDR.limpar();
    }

    public GerenteDebate getGerente() {
        return gerente;
    }

    public void setGerente(GerenteDebate gerente) {
        this.gerente = gerente;
    }

    public GerenciaDireitoResposta getGerenciaDR() {
        return gerenciaDR;
    }

    public ConfiguraTempo getConfig() {
        return config;
    }

    public LogSistem getLog() {
        return log;
    }

    public GerenciaEleitor getGerenciaEleitor() {
        return FachadaDebate.get_instance().get_gerencia_eleitor();
    }

    public void set_inquiridor(ColaboradorPolitico in) {
        this.inquiridor = new Inquiridor(in);
        this.inquiridor.set_inquiridor(true);
        if (inquirido != null && inquiridor.eh_mesmo(inquirido)) {
            this.inquirido = null;
        }
    }

    public Inquiridor get_inquiridor() {
        return inquiridor;
    }

    public boolean set_inquirido(ColaboradorPolitico inqui) {
        if (inquiridor != null && inquiridor.eh_mesmo(inqui)) {
            return false;
        }
        this.inquirido = new Inquirido(inqui);
        this.inquirido.set_inquiridor(false);
        return true;
    }

    public Inquirido get_inquirido() {
        return inquirido;
    }
}
