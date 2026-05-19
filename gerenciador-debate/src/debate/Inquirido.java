package debate;

public class Inquirido extends ColaboradorPolitico {

    public Inquirido(String nome, String partido) {
        super(nome, partido);
    }

    public Inquirido(ColaboradorPolitico politico) {
        super(politico.get_nome(), politico.get_partido());
        this.set_mediador(politico.get_mediador());
        this.set_inquiridor(politico.get_inquiridor());
        this.microfone = politico.get_microfone();
        this.gerencia_eleitor = politico.gerencia_eleitor;
    }

    public void responder(int tempo, LogSistem log) {
        System.out.println("\n--- Resposta de " + get_nome() + " (" + get_partido() + ") ---");
        notificar_eleitores_antes_fala("resposta");
        get_microfone().liga();
        get_microfone().passa_tempo(tempo);
        get_microfone().desliga();
        log.register_log("Resposta: " + get_nome() + " (" + get_partido() + ") - " + tempo + "s");
    }

    public void treplica(int tempo, LogSistem log) {
        System.out.println("\n--- Tréplica de " + get_nome() + " (" + get_partido() + ") ---");
        notificar_eleitores_antes_fala("tréplica");
        get_microfone().liga();
        get_microfone().passa_tempo(tempo);
        get_microfone().desliga();
        log.register_log("Tréplica: " + get_nome() + " (" + get_partido() + ") - " + tempo + "s");
    }
}
