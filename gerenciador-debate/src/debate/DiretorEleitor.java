package debate;

public class DiretorEleitor {
    public Eleitor construir(EleitorBuilder builder, String nome, ColaboradorPolitico candidato) {
        return builder.nome(nome)
                      .candidato(candidato)
                      .build();
    }
}
