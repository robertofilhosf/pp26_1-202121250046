package debate;

/**
 * GerenteDebatePadrao — implementação "liberal" do GerenteDebate.
 *
 * Aprova TODOS os Direitos de Resposta solicitados, respeitando
 * apenas a ordem de chegada definida pela fila.
 */
public class GerenteDebatePadrao implements GerenteDebate {

    @Override
    public void avaliarSolicitacao(SolicitacaoDireitoResposta sol) {
        // Política: aprova todos
        sol.set_aprovado(true);
        System.out.println("[Gerente] DR aprovado para: "
                + sol.get_politico().get_nome()
                + " (ordem #" + sol.get_ordem() + ")");
    }

    @Override
    public void avaliarFila(GerenciaDireitoResposta gerenciaDR) {
        System.out.println("[Gerente Padrão] Avaliando fila de DR — aprovando todos.");
        for (SolicitacaoDireitoResposta sol : gerenciaDR.getFila()) {
            avaliarSolicitacao(sol);
        }
    }

    @Override
    public boolean autorizarDireitoResposta(ColaboradorPolitico politico) {
        // Sempre autoriza
        return true;
    }
}
