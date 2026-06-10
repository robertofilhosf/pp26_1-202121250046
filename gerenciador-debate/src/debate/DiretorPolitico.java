package debate;

public class DiretorPolitico {
    public ColaboradorPolitico construir(PoliticoBuilder builder, String nome, String partido, MediadorBase mediador, GerenciaEleitor gerenciaEleitor) {
        return builder.nome(nome)
                      .partido(partido)
                      .mediador(mediador)
                      .gerenciaEleitor(gerenciaEleitor)
                      .build();
    }

    public ColaboradorPolitico construirDePrototipo(PoliticoBuilder builder, String nome, String partido) {
        return builder.nome(nome)
                      .partido(partido)
                      .build();
    }
}
