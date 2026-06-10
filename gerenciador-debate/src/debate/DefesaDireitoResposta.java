package debate;

/**
 * DefesaDireitoResposta — encapsula a ação de defesa de um político
 * durante o Direito de Resposta.
 *
 * Abre o microfone do político, aguarda o tempo concedido e fecha.
 * É criada e executada pelo EstadoDireitoResposta para cada solicitação aprovada.
 */
public class DefesaDireitoResposta {
    private final ColaboradorPolitico politico;

    public DefesaDireitoResposta(ColaboradorPolitico politico) {
        this.politico = politico;
    }

    public void executar(int tempo, LogSistem log) {
        System.out.println("\n--- Defesa (DR) de " + politico.get_nome()
                + " (" + politico.get_partido() + ") ---");
        politico.realizar_defesa(tempo, log);
    }

    public ColaboradorPolitico get_politico() {
        return politico;
    }
}
