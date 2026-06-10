package debate;

/**
 * Interface EstadoDebate — define o contrato do padrão State.
 *
 * Cada estado concreto implementa o comportamento específico de uma
 * etapa do debate (Pergunta, Resposta, Réplica, Tréplica, AnaliseDR,
 * DireitoResposta). O contexto (MediarDebate) delega a execução ao
 * estado atual, transitando entre estados sem condicional explícito.
 */
public interface EstadoDebate {
    void executar(MediarDebate context);
}
