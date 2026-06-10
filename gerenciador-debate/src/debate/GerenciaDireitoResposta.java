package debate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * GerenciaDireitoResposta — gerencia a fila de solicitações de Direito de Resposta.
 *
 * Controla quando solicitações podem ser aceitas (janela aberta/fechada),
 * garante a ordem FIFO de atendimento e expõe a fila para inspeção pelo
 * EstadoAnaliseDR sem consumi-la prematuramente.
 */
public class GerenciaDireitoResposta {
    private final Queue<SolicitacaoDireitoResposta> fila;
    private int contadorSolicitacoes;
    private boolean recebendoSolicitacoes;

    public GerenciaDireitoResposta() {
        this.fila = new LinkedList<>();
        this.contadorSolicitacoes = 0;
        this.recebendoSolicitacoes = false;
    }

    /**
     * Registra uma solicitação de DR de um político.
     * Só aceita se a janela estiver aberta e o político ainda não tiver solicitado.
     */
    public void solicitarDR(ColaboradorPolitico politico) {
        if (!recebendoSolicitacoes) {
            System.out.println("[DR] Solicitações encerradas. "
                    + politico.get_nome() + " não pode solicitar DR agora.");
            return;
        }
        // Evita duplicatas do mesmo político na fila
        for (SolicitacaoDireitoResposta sol : fila) {
            if (sol.get_politico().eh_mesmo(politico)) {
                System.out.println("[DR] " + politico.get_nome()
                        + " já possui solicitação de DR registrada.");
                return;
            }
        }
        contadorSolicitacoes++;
        SolicitacaoDireitoResposta sol = new SolicitacaoDireitoResposta(politico, contadorSolicitacoes);
        fila.add(sol);
        System.out.println("[DR] Solicitação registrada: " + politico.get_nome()
                + " (" + politico.get_partido() + ") — ordem #" + contadorSolicitacoes);
    }

    public boolean possuiSolicitacoes() {
        return !fila.isEmpty();
    }

    /**
     * Retira e retorna o próximo da fila (FIFO).
     */
    public SolicitacaoDireitoResposta proximaSolicitacao() {
        return fila.poll();
    }

    /**
     * Retorna uma visão da fila sem consumir elementos (para inspeção pelo AnaliseDR).
     */
    public List<SolicitacaoDireitoResposta> getFila() {
        return new ArrayList<>(fila);
    }

    /** Abre a janela de solicitações (início do ciclo). */
    public void abrirSolicitacoes() {
        this.recebendoSolicitacoes = true;
        System.out.println("[DR] Janela de solicitações ABERTA.");
    }

    /** Fecha a janela de solicitações (após tréplica). */
    public void fecharSolicitacoes() {
        this.recebendoSolicitacoes = false;
        System.out.println("[DR] Janela de solicitações FECHADA.");
    }

    /** Reseta a fila e o contador para o próximo ciclo. */
    public void limpar() {
        fila.clear();
        contadorSolicitacoes = 0;
        System.out.println("[DR] Fila de solicitações limpa.");
    }

    public boolean isRecebendoSolicitacoes() {
        return recebendoSolicitacoes;
    }
}
