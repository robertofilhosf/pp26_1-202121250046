package debate;

public class Eleitor implements ObservadorEleitor, Prototype<Eleitor>, Cloneable {
    private String nome;
    private ColaboradorPolitico candidato_preferencia;

    Eleitor() {
    }

    Eleitor(String nome, ColaboradorPolitico candidato_preferencia) {
        this.nome = nome;
        this.candidato_preferencia = candidato_preferencia;
    }

    @Override
    public Eleitor clone() {
        try {
            return (Eleitor) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Erro ao clonar Eleitor", e);
        }
    }

    @Override
    public void receber_notificacao(String mensagem) {
        System.out.println("[Eleitor " + nome + "] " + mensagem);
    }

    public void set_nome(String nome) {
        this.nome = nome;
    }

    public String get_nome() {
        return nome;
    }

    public void set_candidato_preferencia(ColaboradorPolitico candidato) {
        this.candidato_preferencia = candidato;
    }

    public ColaboradorPolitico get_candidato_preferencia() {
        return candidato_preferencia;
    }

    public boolean acompanha(String nomeCandidato, String partidoCandidato) {
        return candidato_preferencia.get_nome().equalsIgnoreCase(nomeCandidato)
                && candidato_preferencia.get_partido().equalsIgnoreCase(partidoCandidato);
    }
}
