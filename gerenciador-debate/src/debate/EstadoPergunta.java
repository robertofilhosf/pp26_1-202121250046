package debate;

/**
 * Estado PERGUNTA — etapa em que o Inquiridor realiza sua pergunta.
 *
 * Após executar, abre solicitações de DR no microfone/gerência e
 * transita para EstadoResposta.
 */
public class EstadoPergunta implements EstadoDebate {

    @Override
    public void executar(MediarDebate context) {
        ConfiguraTempo config = context.getConfig();
        LogSistem log = context.getLog();

        System.out.println("\n[ESTADO] Pergunta");
        context.getGerenciaDR().abrirSolicitacoes();

        Inquiridor inquiridor = context.get_inquiridor();
        if (inquiridor != null) {
            inquiridor.perguntar(config.get_temp_pergunta(), log);
        }

        context.setEstado(new EstadoResposta());
    }
}
