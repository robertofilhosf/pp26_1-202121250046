package debate;

/**
 * Estado ANÁLISE DR — avalia as solicitações de Direito de Resposta acumuladas
 * durante o ciclo pergunta-resposta-réplica-tréplica.
 *
 * Delega ao GerenteDebate a decisão de aprovar ou não cada solicitação.
 * Se houver solicitações aprovadas, transita para EstadoDireitoResposta.
 * Caso contrário, o debate continua normalmente (retorna para EstadoPergunta).
 */
public class EstadoAnaliseDR implements EstadoDebate {

    @Override
    public void executar(MediarDebate context) {
        LogSistem log = context.getLog();
        GerenteDebate gerente = context.getGerente();
        GerenciaDireitoResposta gerenciaDR = context.getGerenciaDR();

        System.out.println("\n[ESTADO] Análise de Direito de Resposta");

        if (!gerenciaDR.possuiSolicitacoes()) {
            System.out.println("  Nenhuma solicitação de DR registrada. Debate continua.");
            log.register_log("AnaliseDR: nenhuma solicitação pendente.");
            gerenciaDR.limpar();
            context.setEstado(new EstadoPergunta());
            return;
        }

        // Gerente avalia toda a fila de solicitações
        gerente.avaliarFila(gerenciaDR);

        boolean algumAprovado = false;
        // Verifica se ao menos um DR foi aprovado
        GerenciaDireitoResposta copia = gerenciaDR;
        // Percorre verificando aprovações sem consumir a fila
        for (SolicitacaoDireitoResposta sol : gerenciaDR.getFila()) {
            if (sol.is_aprovado()) {
                algumAprovado = true;
                break;
            }
        }

        if (algumAprovado) {
            System.out.println("  Direito(s) de Resposta aprovado(s). Iniciando defesas.");
            log.register_log("AnaliseDR: DR(s) aprovado(s). Transitando para DireitoResposta.");
            context.setEstado(new EstadoDireitoResposta());
        } else {
            System.out.println("  Nenhum DR aprovado. Debate continua normalmente.");
            log.register_log("AnaliseDR: nenhum DR aprovado. Retornando ao fluxo normal.");
            gerenciaDR.limpar();
            context.setEstado(new EstadoPergunta());
        }
    }
}
