package debate;

/**
 * GerenteDebateAutomatico — implementação "restritiva" do GerenteDebate.
 *
 * Aprova apenas o PRIMEIRO Direito de Resposta da fila (o político
 * que pressionou o botão DR primeiro). Os demais são recusados.
 * Simula um gerente mais controlador do tempo de debate.
 */
public class GerenteDebateAutomatico implements GerenteDebate {

    @Override
    public void avaliarSolicitacao(SolicitacaoDireitoResposta sol) {
        // Aprovação de cada item é gerenciada em avaliarFila
        // Este método aprova individualmente (uso avulso)
        sol.set_aprovado(true);
        System.out.println("[Gerente Auto] DR aprovado para: "
                + sol.get_politico().get_nome()
                + " (ordem #" + sol.get_ordem() + ")");
    }

    @Override
    public void avaliarFila(GerenciaDireitoResposta gerenciaDR) {
        System.out.println("[Gerente Automático] Avaliando fila de DR — aprovando apenas o primeiro.");
        boolean primeiro = true;
        for (SolicitacaoDireitoResposta sol : gerenciaDR.getFila()) {
            if (primeiro) {
                sol.set_aprovado(true);
                System.out.println("[Gerente Auto] DR APROVADO: "
                        + sol.get_politico().get_nome()
                        + " (ordem #" + sol.get_ordem() + ")");
                primeiro = false;
            } else {
                sol.set_aprovado(false);
                System.out.println("[Gerente Auto] DR RECUSADO: "
                        + sol.get_politico().get_nome()
                        + " (ordem #" + sol.get_ordem() + ")");
            }
        }
    }

    @Override
    public boolean autorizarDireitoResposta(ColaboradorPolitico politico) {
        // Autoriza individualmente (sempre true nesta lógica)
        return true;
    }
}
