package debate;

/**
 * Estado RESPOSTA — etapa em que o Inquirido responde à pergunta.
 * Transita para EstadoReplica após executar.
 */
public class EstadoResposta implements EstadoDebate {

    @Override
    public void executar(MediarDebate context) {
        ConfiguraTempo config = context.getConfig();
        LogSistem log = context.getLog();

        System.out.println("\n[ESTADO] Resposta");

        Inquirido inquirido = context.get_inquirido();
        if (inquirido != null) {
            inquirido.responder(config.get_temp_resposta(), log);
        }

        context.setEstado(new EstadoReplica());
    }
}
