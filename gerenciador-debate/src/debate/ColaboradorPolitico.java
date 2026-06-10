package debate;

public class ColaboradorPolitico implements Prototype<ColaboradorPolitico>, SujeitoPolitico, Cloneable {
    private String nome;
    private String partido;
    private boolean inquiridor;
    protected Microfone microfone;
    protected MediadorBase mediador;
    protected GerenciaEleitor gerencia_eleitor;

    ColaboradorPolitico() {
        this.inquiridor = false;
        this.microfone = new Microfone(this);
    }

    ColaboradorPolitico(String nome, String partido) {
        this.nome = nome;
        this.partido = partido;
        this.inquiridor = false;
        this.microfone = new Microfone(this);
    }

    @Override
    public ColaboradorPolitico clone() {
        try {
            ColaboradorPolitico copia = (ColaboradorPolitico) super.clone();
            copia.microfone = new Microfone(copia); // cada clone recebe um microfone próprio
            if (copia.mediador instanceof MediarDebate mediarDebate) {
                copia.microfone.setGerenciaDR(mediarDebate.getGerenciaDR());
            }
            return copia;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Erro ao clonar ColaboradorPolitico", e);
        }
    }

    public void set_gerencia_eleitor(GerenciaEleitor gerencia) {
        this.gerencia_eleitor = gerencia;
    }

    protected void notificar_eleitores_antes_fala(String etapa) {
        if (gerencia_eleitor != null) {
            gerencia_eleitor.notificar_inicio_fala(nome, partido, etapa);
        }
    }

    public void OperacaoMediada() {
        if (mediador != null) {
            System.out.println(nome + " (" + partido + ") realizou operação mediada.");
        }
    }

    @Override
    public void solicitar_direito_resposta() {
        System.out.println(nome + " (" + partido + ") acionou o botão DR no microfone.");
        microfone.pressionarDR();
    }

    @Override
    public void realizar_defesa(int tempo, LogSistem log) {
        notificar_eleitores_antes_fala("direito de resposta");
        microfone.liga();
        microfone.passa_tempo(tempo);
        microfone.desliga();
        log.register_log("Defesa DR: " + nome + " (" + partido + ") - " + tempo + "s");
    }

    public void set_nome(String n) {
        this.nome = n;
    }

    public String get_nome() {
        return nome;
    }

    public void set_partido(String p) {
        this.partido = p;
    }

    public String get_partido() {
        return partido;
    }

    public void set_mediador(MediadorBase mediator) {
        this.mediador = mediator;
        if (mediator instanceof MediarDebate mediarDebate) {
            this.microfone.setGerenciaDR(mediarDebate.getGerenciaDR());
        }
    }

    public MediadorBase get_mediador() {
        return mediador;
    }

    public void set_inquiridor(boolean valor) {
        this.inquiridor = valor;
    }

    public boolean get_inquiridor() {
        return inquiridor;
    }

    protected Microfone get_microfone() {
        return microfone;
    }

    public boolean eh_mesmo(ColaboradorPolitico outro) {
        if (outro == null) {
            return false;
        }
        return nome.equalsIgnoreCase(outro.get_nome())
                && partido.equalsIgnoreCase(outro.get_partido());
    }
}
