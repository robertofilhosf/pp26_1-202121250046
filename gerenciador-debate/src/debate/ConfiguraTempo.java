package debate;

public class ConfiguraTempo {
    public static final int TEMPO_PADRAO_SEGUNDOS = 15;

    private int tempo_pergunta;
    private int tempo_resposta;
    private int tempo_replica;
    private int tempo_treplica;
    private int tempo_direito_resposta;

    public ConfiguraTempo() {
        this.tempo_pergunta = TEMPO_PADRAO_SEGUNDOS;
        this.tempo_resposta = TEMPO_PADRAO_SEGUNDOS;
        this.tempo_replica = TEMPO_PADRAO_SEGUNDOS;
        this.tempo_treplica = TEMPO_PADRAO_SEGUNDOS;
        this.tempo_direito_resposta = 60; // 60 segundos por padrão (1 minuto)
    }

    public boolean tempos_configurados() {
        return tempo_pergunta > 0 && tempo_resposta > 0 && tempo_replica > 0 && tempo_treplica > 0 && tempo_direito_resposta > 0;
    }

    public String resumo_tempos() {
        return "pergunta=" + tempo_pergunta + "s, resposta=" + tempo_resposta + "s, réplica="
                + tempo_replica + "s, tréplica=" + tempo_treplica + "s, direito de resposta=" + tempo_direito_resposta + "s";
    }

    public String get_texto_configuracao_menu() {
        if (mesmo_tempo_em_todas_etapas()) {
            return "(configurado para " + tempo_pergunta + "s por etapa)";
        }
        return "(configurado: pergunta " + tempo_pergunta + "s, resposta " + tempo_resposta
                + "s, réplica " + tempo_replica + "s, tréplica " + tempo_treplica + "s, DR " + tempo_direito_resposta + "s)";
    }

    private boolean mesmo_tempo_em_todas_etapas() {
        return tempo_pergunta == tempo_resposta
                && tempo_resposta == tempo_replica
                && tempo_replica == tempo_treplica
                && tempo_treplica == tempo_direito_resposta;
    }

    public void set_temp_pergunta(int t) {
        this.tempo_pergunta = t;
    }

    public int get_temp_pergunta() {
        return tempo_pergunta;
    }

    public void set_temp_resposta(int t) {
        this.tempo_resposta = t;
    }

    public int get_temp_resposta() {
        return tempo_resposta;
    }

    public void set_temp_replica(int t) {
        this.tempo_replica = t;
    }

    public int get_temp_replica() {
        return tempo_replica;
    }

    public void set_temp_treplica(int t) {
        this.tempo_treplica = t;
    }

    public int get_temp_treplica() {
        return tempo_treplica;
    }

    public void set_temp_direito_resposta(int t) {
        this.tempo_direito_resposta = t;
    }

    public int get_temp_direito_resposta() {
        return tempo_direito_resposta;
    }
}
