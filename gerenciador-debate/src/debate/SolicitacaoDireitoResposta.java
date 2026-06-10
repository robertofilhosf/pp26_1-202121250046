package debate;

/**
 * SolicitacaoDireitoResposta — representa uma solicitação de Direito de Resposta
 * feita por um político durante o debate.
 *
 * Armazena o político solicitante, a ordem de solicitação (para garantir
 * prioridade justa) e o status de aprovação decidido pelo GerenteDebate.
 */
public class SolicitacaoDireitoResposta {
    private final ColaboradorPolitico politico;
    private final int ordem;
    private boolean aprovado;

    public SolicitacaoDireitoResposta(ColaboradorPolitico politico, int ordem) {
        this.politico = politico;
        this.ordem = ordem;
        this.aprovado = false;
    }

    public ColaboradorPolitico get_politico() {
        return politico;
    }

    public int get_ordem() {
        return ordem;
    }

    public void set_aprovado(boolean valor) {
        this.aprovado = valor;
    }

    public boolean is_aprovado() {
        return aprovado;
    }

    @Override
    public String toString() {
        return "SolicitacaoDR[ordem=" + ordem
                + ", politico=" + politico.get_nome()
                + " (" + politico.get_partido() + ")"
                + ", aprovado=" + aprovado + "]";
    }
}
