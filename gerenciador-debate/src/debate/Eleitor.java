package debate;

public class Eleitor implements ObservadorEleitor {
    private final String nome;
    private final ColaboradorPolitico candidato_preferencia;

    public Eleitor(String nome, ColaboradorPolitico candidato_preferencia) {
        this.nome = nome;
        this.candidato_preferencia = candidato_preferencia;
    }

    @Override
    public void receber_notificacao(String mensagem) {
        System.out.println("[Eleitor " + nome + "] " + mensagem);
    }

    public String get_nome() {
        return nome;
    }

    public ColaboradorPolitico get_candidato_preferencia() {
        return candidato_preferencia;
    }

    public boolean acompanha(String nomeCandidato, String partidoCandidato) {
        return candidato_preferencia.get_nome().equalsIgnoreCase(nomeCandidato)
                && candidato_preferencia.get_partido().equalsIgnoreCase(partidoCandidato);
    }
}
