package debate;

/**
 * Estado RÉPLICA — etapa em que o Inquiridor replica à resposta.
 * Transita para EstadoTreplica após executar.
 */
public class EstadoReplica implements EstadoDebate {

    @Override
    public void executar(MediarDebate context) {
        ConfiguraTempo config = context.getConfig();
        LogSistem log = context.getLog();

        System.out.println("\n[ESTADO] Réplica");

        Inquiridor inquiridor = context.get_inquiridor();
        if (inquiridor != null) {
            inquiridor.replica(config.get_temp_replica(), log);
        }

        context.setEstado(new EstadoTreplica());
    }
}
