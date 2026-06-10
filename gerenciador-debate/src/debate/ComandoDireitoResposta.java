package debate;

/**
 * Interface ComandoDireitoResposta — contrato do padrão Command
 * para a ação de solicitar Direito de Resposta.
 *
 * Desacopla o acionamento (pressionar botão DR no microfone) da
 * lógica concreta de registro na GerenciaDireitoResposta.
 */
public interface ComandoDireitoResposta {
    void executar();
}
