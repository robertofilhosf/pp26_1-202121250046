package debate;

public class ColaboradorPolitico {
    private String nome;
    private String partido;
    private boolean inquiridor;
    protected Microfone microfone;
    protected MediadorBase mediador;
    protected GerenciaEleitor gerencia_eleitor;

    public ColaboradorPolitico(String nome, String partido) {
        this.nome = nome;
        this.partido = partido;
        this.inquiridor = false;
        this.microfone = new Microfone();
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
