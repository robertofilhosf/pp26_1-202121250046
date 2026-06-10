package debate;

public class ColaboradorPoliticoBuilder implements PoliticoBuilder {
    private String nome;
    private String partido;
    private MediadorBase mediador;
    private GerenciaEleitor gerencia;
    private ColaboradorPolitico prototipo;

    public ColaboradorPoliticoBuilder() {
    }

    public ColaboradorPoliticoBuilder(ColaboradorPolitico prototipo) {
        this.prototipo = prototipo;
        this.nome = prototipo.get_nome();
        this.partido = prototipo.get_partido();
        this.mediador = prototipo.get_mediador();
        this.gerencia = prototipo.gerencia_eleitor;
    }

    @Override
    public ColaboradorPoliticoBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    @Override
    public ColaboradorPoliticoBuilder partido(String partido) {
        this.partido = partido;
        return this;
    }

    @Override
    public ColaboradorPoliticoBuilder mediador(MediadorBase m) {
        this.mediador = m;
        return this;
    }

    @Override
    public ColaboradorPoliticoBuilder gerenciaEleitor(GerenciaEleitor g) {
        this.gerencia = g;
        return this;
    }

    @Override
    public ColaboradorPolitico build() {
        ColaboradorPolitico politico;
        if (prototipo != null) {
            politico = prototipo.clone();
        } else {
            politico = new ColaboradorPolitico();
        }

        if (nome != null) {
            politico.set_nome(nome);
        }
        if (partido != null) {
            politico.set_partido(partido);
        }
        if (mediador != null) {
            politico.set_mediador(mediador);
        }
        if (gerencia != null) {
            politico.set_gerencia_eleitor(gerencia);
        }
        return politico;
    }
}
