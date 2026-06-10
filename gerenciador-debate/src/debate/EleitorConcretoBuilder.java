package debate;

public class EleitorConcretoBuilder implements EleitorBuilder {
    private String nome;
    private ColaboradorPolitico candidato;
    private Eleitor prototipo;

    public EleitorConcretoBuilder() {
    }

    public EleitorConcretoBuilder(Eleitor prototipo) {
        this.prototipo = prototipo;
        this.nome = prototipo.get_nome();
        this.candidato = prototipo.get_candidato_preferencia();
    }

    @Override
    public EleitorConcretoBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    @Override
    public EleitorConcretoBuilder candidato(ColaboradorPolitico c) {
        this.candidato = c;
        return this;
    }

    @Override
    public Eleitor build() {
        Eleitor eleitor;
        if (prototipo != null) {
            eleitor = prototipo.clone();
        } else {
            eleitor = new Eleitor();
        }

        if (nome != null) {
            eleitor.set_nome(nome);
        }
        if (candidato != null) {
            eleitor.set_candidato_preferencia(candidato);
        }
        return eleitor;
    }
}
