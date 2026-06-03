package debate;

/**
 * Builder para construção passo a passo de Eleitor.
 *
 * Padrão Builder — permite montar um Eleitor definindo nome e candidato
 * de preferência de forma encadeada (fluent API).
 * Pode também trabalhar com um protótipo (Prototype) para clonar um
 * eleitor existente e modificar campos específicos.
 */
public class EleitorBuilder {
    private String nome;
    private ColaboradorPolitico candidatoPreferencia;

    // Protótipo base para clonagem (Prototype + Builder)
    private Eleitor prototipo;

    public EleitorBuilder() {
        // construção do zero
    }

    /**
     * Constrói a partir de um protótipo existente (Prototype).
     * Clona o eleitor e permite modificar campos antes de finalizar.
     */
    public EleitorBuilder(Eleitor prototipo) {
        this.prototipo = prototipo;
        this.nome = prototipo.get_nome();
        this.candidatoPreferencia = prototipo.get_candidato_preferencia();
    }

    public EleitorBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public EleitorBuilder comCandidatoPreferencia(ColaboradorPolitico candidato) {
        this.candidatoPreferencia = candidato;
        return this;
    }

    /**
     * Constrói e retorna o Eleitor.
     * Se houver um protótipo, clona-o e aplica as alterações.
     * Caso contrário, cria um novo objeto do zero.
     */
    public Eleitor build() {
        Eleitor eleitor;
        if (prototipo != null) {
            // Prototype: clona o objeto base
            eleitor = prototipo.clone();
        } else {
            eleitor = new Eleitor();
        }

        if (nome != null) {
            eleitor.set_nome(nome);
        }
        if (candidatoPreferencia != null) {
            eleitor.set_candidato_preferencia(candidatoPreferencia);
        }
        return eleitor;
    }
}
