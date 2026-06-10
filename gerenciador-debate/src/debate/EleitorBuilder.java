package debate;

public interface EleitorBuilder {
    EleitorBuilder nome(String nome);
    EleitorBuilder candidato(ColaboradorPolitico c);
    Eleitor build();
}
