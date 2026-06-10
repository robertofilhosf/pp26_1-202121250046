package debate;

/**
 * Estado TRÉPLICA — etapa em que o Inquirido treuplica a réplica.
 * Ao finalizar, fecha as solicitações de DR e transita para EstadoAnaliseDR.
 */
public class EstadoTreplica implements EstadoDebate {

    @Override
    public void executar(MediarDebate context) {
        ConfiguraTempo config = context.getConfig();
        LogSistem log = context.getLog();

        System.out.println("\n[ESTADO] Tréplica");

        Inquirido inquirido = context.get_inquirido();
        if (inquirido != null) {
            inquirido.treplica(config.get_temp_treplica(), log);
        }

        // Fecha janela de solicitações de DR após ciclo completo
        context.getGerenciaDR().fecharSolicitacoes();

        context.setEstado(new EstadoAnaliseDR());
    }
}
