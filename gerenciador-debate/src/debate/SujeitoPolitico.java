package debate;

/**
 * Interface SujeitoPolitico — representa o sujeito observável do padrão Observer
 * no contexto político. Um sujeito político pode notificar eleitores (observadores)
 * sobre eventos durante o debate.
 */
public interface SujeitoPolitico {
    void solicitar_direito_resposta();
    void realizar_defesa(int tempo, LogSistem log);
}
