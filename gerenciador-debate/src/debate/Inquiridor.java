package debate;

public class Inquiridor extends ColaboradorPolitico {

    public Inquiridor(String nome, String partido) {
        super(nome, partido);
    }

    public Inquiridor(ColaboradorPolitico politico) {
        super(politico.get_nome(), politico.get_partido());
        this.set_mediador(politico.get_mediador());
        this.set_inquiridor(politico.get_inquiridor());
        this.microfone = politico.get_microfone();
        this.gerencia_eleitor = politico.gerencia_eleitor;
    }

    public void escolha_inquirido(ColaboradorPolitico politico) {
        if (eh_mesmo(politico)) {
            System.out.println("O inquirido não pode ser a mesma pessoa que o inquiridor.");
            return;
        }
        if (mediador instanceof MediarDebate mediar && mediar.set_inquirido(politico)) {
            logEscolha(politico);
        }
    }

    private void logEscolha(ColaboradorPolitico politico) {
        System.out.println(get_nome() + " escolheu " + politico.get_nome()
                + " (" + politico.get_partido() + ") como inquirido.");
    }

    public void perguntar(int tempo, LogSistem log) {
        System.out.println("\n--- Pergunta de " + get_nome() + " (" + get_partido() + ") ---");
        notificar_eleitores_antes_fala("pergunta");
        get_microfone().liga();
        get_microfone().passa_tempo(tempo);
        get_microfone().desliga();
        log.register_log("Pergunta: " + get_nome() + " (" + get_partido() + ") - " + tempo + "s");
    }

    public void replica(int tempo, LogSistem log) {
        System.out.println("\n--- Réplica de " + get_nome() + " (" + get_partido() + ") ---");
        notificar_eleitores_antes_fala("réplica");
        get_microfone().liga();
        get_microfone().passa_tempo(tempo);
        get_microfone().desliga();
        log.register_log("Réplica: " + get_nome() + " (" + get_partido() + ") - " + tempo + "s");
    }
}
