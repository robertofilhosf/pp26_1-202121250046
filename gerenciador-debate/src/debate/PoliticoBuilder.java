package debate;

/**
 * Builder para construção passo a passo de ColaboradorPolitico.
 *
 * Padrão Builder — separa a construção de um objeto complexo da sua
 * representação, permitindo que o mesmo processo de construção crie
 * diferentes representações. Aqui o PoliticoBuilder monta um
 * ColaboradorPolitico definindo nome, partido, mediador e gerência
 * de eleitor de forma encadeada (fluent API).
 */
public class PoliticoBuilder {
    private String nome;
    private String partido;
    private MediadorBase mediador;
    private GerenciaEleitor gerenciaEleitor;

    // Protótipo base para clonagem (Prototype + Builder)
    private ColaboradorPolitico prototipo;

    public PoliticoBuilder() {
        // construção do zero, sem protótipo
    }

    /**
     * Constrói a partir de um protótipo existente (Prototype).
     * Clona o protótipo e permite modificar campos antes de finalizar.
     */
    public PoliticoBuilder(ColaboradorPolitico prototipo) {
        this.prototipo = prototipo;
        // pré-preenche com os dados do protótipo
        this.nome = prototipo.get_nome();
        this.partido = prototipo.get_partido();
        this.mediador = prototipo.get_mediador();
        this.gerenciaEleitor = prototipo.gerencia_eleitor;
    }

    public PoliticoBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public PoliticoBuilder comPartido(String partido) {
        this.partido = partido;
        return this;
    }

    public PoliticoBuilder comMediador(MediadorBase mediador) {
        this.mediador = mediador;
        return this;
    }

    public PoliticoBuilder comGerenciaEleitor(GerenciaEleitor gerenciaEleitor) {
        this.gerenciaEleitor = gerenciaEleitor;
        return this;
    }

    /**
     * Constrói e retorna o ColaboradorPolitico.
     * Se houver um protótipo, clona-o e aplica as alterações.
     * Caso contrário, cria um novo objeto do zero.
     */
    public ColaboradorPolitico build() {
        ColaboradorPolitico politico;
        if (prototipo != null) {
            // Prototype: clona o objeto base
            politico = prototipo.clone();
        } else {
            politico = new ColaboradorPolitico();
        }

        if (nome != null) {
            politico.set_nome(nome);
        }
        if (partido != null) {
            politico.set_partido(partido);
        }
        if (mediador != null) {
            politico.set_mediador(mediador);
        }
        if (gerenciaEleitor != null) {
            politico.set_gerencia_eleitor(gerenciaEleitor);
        }
        return politico;
    }
}
