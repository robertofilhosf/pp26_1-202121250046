package debate;

public class MediarDebate implements MediadorBase {
    private Inquiridor inquiridor;
    private Inquirido inquirido;

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

        log.register_log("Início do debate: " + inquiridor.get_nome() + " x " + inquirido.get_nome());
        System.out.println("\n========== INÍCIO DO DEBATE ==========");
        System.out.println("Inquiridor: " + inquiridor.get_nome() + " (" + inquiridor.get_partido() + ")");
        System.out.println("Inquirido: " + inquirido.get_nome() + " (" + inquirido.get_partido() + ")");
        System.out.println("Duração das falas: " + config.resumo_tempos());

        inquiridor.perguntar(config.get_temp_pergunta(), log);
        inquirido.responder(config.get_temp_resposta(), log);
        inquiridor.replica(config.get_temp_replica(), log);
        inquirido.treplica(config.get_temp_treplica(), log);

        System.out.println("\n========== FIM DO DEBATE ==========");
        log.register_log("Fim do debate: " + inquiridor.get_nome() + " x " + inquirido.get_nome());
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
