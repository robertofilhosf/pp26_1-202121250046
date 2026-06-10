package debate;

/**
 * Interface Prototype — define o contrato de clonagem genérico.
 * Padrão Prototype: permite criar novos objetos copiando instâncias existentes,
 * evitando dependências das classes concretas no código cliente.
 *
 * @param <T> tipo do objeto clonado
 */
public interface Prototype<T> {
    T clone();
}
