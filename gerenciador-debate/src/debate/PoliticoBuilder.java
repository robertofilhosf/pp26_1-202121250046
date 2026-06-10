package debate;

public interface PoliticoBuilder {
    PoliticoBuilder nome(String nome);
    PoliticoBuilder partido(String partido);
    PoliticoBuilder mediador(MediadorBase m);
    PoliticoBuilder gerenciaEleitor(GerenciaEleitor g);
    ColaboradorPolitico build();
}
