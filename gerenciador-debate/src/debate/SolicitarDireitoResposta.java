package debate;

/**
 * SolicitarDireitoResposta — implementação concreta do ComandoDireitoResposta.
 *
 * Ao ser executado, registra o político solicitante na GerenciaDireitoResposta.
 * É criado pelo Microfone quando o botão DR é pressionado.
 */
public class SolicitarDireitoResposta implements ComandoDireitoResposta {
    private final ColaboradorPolitico politico;
    private final GerenciaDireitoResposta gerenciaDR;

    public SolicitarDireitoResposta(ColaboradorPolitico politico, GerenciaDireitoResposta gerenciaDR) {
        this.politico = politico;
        this.gerenciaDR = gerenciaDR;
    }

    @Override
    public void executar() {
        gerenciaDR.solicitarDR(politico);
    }
}
