package debate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GerenciaPolitico {
    private final List<ColaboradorPolitico> politicos;
    private int quant_politicos;
    private final Random random;

    public GerenciaPolitico() {
        this.politicos = new ArrayList<>();
        this.quant_politicos = 0;
        this.random = new Random();
    }

    /**
     * Cria um político utilizando o PoliticoBuilder (padrão Builder).
     */
    public void criar_politico(String nome, String partido, MediadorBase mediador, GerenciaEleitor gerenciaEleitor) {
        ColaboradorPolitico politico = new PoliticoBuilder()
                .comNome(nome)
                .comPartido(partido)
                .comMediador(mediador)
                .comGerenciaEleitor(gerenciaEleitor)
                .build();
        politicos.add(politico);
        quant_politicos++;
    }

    /**
     * Cria um político a partir de um protótipo existente (Prototype + Builder).
     * Clona o protótipo e aplica novo nome e partido.
     */
    public void criar_politico_de_prototipo(ColaboradorPolitico prototipo, String nome, String partido) {
        ColaboradorPolitico politico = new PoliticoBuilder(prototipo)
                .comNome(nome)
                .comPartido(partido)
                .build();
        politicos.add(politico);
        quant_politicos++;
    }

    public ColaboradorPolitico obter_politico(String nome, String partido) {
        for (ColaboradorPolitico politico : politicos) {
            if (politico.get_nome().equalsIgnoreCase(nome)
                    && politico.get_partido().equalsIgnoreCase(partido)) {
                return politico;
            }
        }
        return null;
    }

    public ColaboradorPolitico sortear_politico() {
        if (politicos.isEmpty()) {
            return null;
        }

        List<ColaboradorPolitico> elegiveis = new ArrayList<>();
        for (ColaboradorPolitico politico : politicos) {
            if (!politico.get_inquiridor()) {
                elegiveis.add(politico);
            }
        }

        if (elegiveis.isEmpty()) {
            for (ColaboradorPolitico politico : politicos) {
                politico.set_inquiridor(false);
            }
            elegiveis.addAll(politicos);
        }

        ColaboradorPolitico sorteado = elegiveis.get(random.nextInt(elegiveis.size()));
        sorteado.set_inquiridor(true);
        return sorteado;
    }

    public int get_quant_politicos() {
        return quant_politicos;
    }

    public List<ColaboradorPolitico> get_politicos() {
        return politicos;
    }
}
