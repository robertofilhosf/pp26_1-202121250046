package debate;

import java.util.ArrayList;
import java.util.List;

public class GerenciaEleitor {
    private final List<Eleitor> eleitores;
    private int quant_eleitores;
    private final DiretorEleitor diretor;

    public GerenciaEleitor() {
        this.eleitores = new ArrayList<>();
        this.quant_eleitores = 0;
        this.diretor = new DiretorEleitor();
    }

    /**
     * Cadastra um eleitor utilizando o EleitorBuilder (padrão Builder).
     */
    public boolean cadastrar_eleitor(String nomeEleitor, String nomeCandidato, String partidoCandidato,
            GerenciaPolitico gerenciaPolitico) {
        ColaboradorPolitico candidato = gerenciaPolitico.obter_politico(nomeCandidato, partidoCandidato);
        if (candidato == null) {
            return false;
        }
        for (Eleitor eleitor : eleitores) {
            if (eleitor.get_nome().equalsIgnoreCase(nomeEleitor)) {
                return false;
            }
        }
        EleitorBuilder builder = new EleitorConcretoBuilder();
        Eleitor eleitor = diretor.construir(builder, nomeEleitor, candidato);
        eleitores.add(eleitor);
        quant_eleitores++;
        return true;
    }

    /**
     * Cadastra um eleitor a partir de um protótipo existente (Prototype + Builder).
     * Clona o protótipo e atribui um novo nome e candidato.
     */
    public boolean cadastrar_eleitor_de_prototipo(Eleitor prototipo, String nomeEleitor,
            String nomeCandidato, String partidoCandidato, GerenciaPolitico gerenciaPolitico) {
        ColaboradorPolitico candidato = gerenciaPolitico.obter_politico(nomeCandidato, partidoCandidato);
        if (candidato == null) {
            return false;
        }
        for (Eleitor e : eleitores) {
            if (e.get_nome().equalsIgnoreCase(nomeEleitor)) {
                return false;
            }
        }
        EleitorBuilder builder = new EleitorConcretoBuilder(prototipo);
        Eleitor eleitor = diretor.construir(builder, nomeEleitor, candidato);
        eleitores.add(eleitor);
        quant_eleitores++;
        return true;
    }

    public Eleitor clonar_eleitor(Eleitor original) {
        if (original == null) {
            return null;
        }
        return original.clone();
    }

    public void notificar_inicio_fala(String nomeCandidato, String partidoCandidato, String etapa) {
        String detalhe = "Candidato " + nomeCandidato + " (" + partidoCandidato + ") está falando (" + etapa + ")";
        for (Eleitor eleitor : eleitores) {
            if (eleitor.acompanha(nomeCandidato, partidoCandidato)) {
                eleitor.receber_notificacao("SEU CANDIDATO ESTÁ FALANDO — " + detalhe);
            }
        }
    }

    public int get_quant_eleitores() {
        return quant_eleitores;
    }

    public List<Eleitor> get_eleitores() {
        return eleitores;
    }
}
