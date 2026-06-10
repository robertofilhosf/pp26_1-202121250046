package debate;

/**
 * Interface GerenteDebate — define o contrato do gerente do debate.
 *
 * O gerente é responsável por avaliar e decidir sobre as solicitações
 * de Direito de Resposta. Duas implementações concretas existem:
 *   - GerenteDebatePadrao: sempre aprova todos os DRs (liberal)
 *   - GerenteDebateAutomatico: aprova apenas o primeiro DR (restritivo)
 */
public interface GerenteDebate {
    void avaliarSolicitacao(SolicitacaoDireitoResposta sol);
    void avaliarFila(GerenciaDireitoResposta gerenciaDR);
    boolean autorizarDireitoResposta(ColaboradorPolitico politico);
}
