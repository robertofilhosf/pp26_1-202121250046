package debate;

/**
 * Estado DIREITO DE RESPOSTA — executa as defesas dos políticos cujas
 * solicitações de DR foram aprovadas, respeitando a ordem de solicitação.
 *
 * Durante este estado, novas solicitações de DR ficam bloqueadas para evitar
 * ciclos infinitos. Ao finalizar todas as defesas, limpa a fila e transita
 * de volta para EstadoPergunta, retomando o fluxo normal do debate.
 */
public class EstadoDireitoResposta implements EstadoDebate {

    @Override
    public void executar(MediarDebate context) {
        ConfiguraTempo config = context.getConfig();
        LogSistem log = context.getLog();
        GerenciaDireitoResposta gerenciaDR = context.getGerenciaDR();
        GerenciaEleitor gerenciaEleitor = context.getGerenciaEleitor();

        System.out.println("\n[ESTADO] Direito de Resposta — executando defesas");

        // DR bloqueado durante defesas (fecharSolicitacoes já foi chamado em EstadoTreplica)
        // garante que nenhuma nova solicitação entre

        while (gerenciaDR.possuiSolicitacoes()) {
            SolicitacaoDireitoResposta sol = gerenciaDR.proximaSolicitacao();
            if (sol == null || !sol.is_aprovado()) {
                continue;
            }

            ColaboradorPolitico politico = sol.get_politico();

            // Notifica eleitores sobre a fala por DR
            if (gerenciaEleitor != null) {
                gerenciaEleitor.notificar_inicio_fala(
                        politico.get_nome(),
                        politico.get_partido(),
                        "Direito de Resposta");
            }

            // Cria e executa a defesa via DefesaDireitoResposta
            DefesaDireitoResposta defesa = new DefesaDireitoResposta(politico);
            defesa.executar(config.get_temp_direito_resposta(), log);
        }

        gerenciaDR.limpar();

        System.out.println("\n[ESTADO] Defesas de DR concluídas. Retomando debate normal.");
        log.register_log("DireitoResposta: todas as defesas executadas. Retomando fluxo normal.");

        context.setEstado(new EstadoPergunta());
    }
}
