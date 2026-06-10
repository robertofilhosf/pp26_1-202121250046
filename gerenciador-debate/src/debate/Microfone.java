package debate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Microfone {
    private boolean MicroAtivo;
    private GerenciaDireitoResposta gerenciaDR;
    private ColaboradorPolitico politico;

    public Microfone() {
        this.MicroAtivo = false;
    }

    public Microfone(ColaboradorPolitico politico) {
        this.politico = politico;
        this.MicroAtivo = false;
    }

    public void setGerenciaDR(GerenciaDireitoResposta gerenciaDR) {
        this.gerenciaDR = gerenciaDR;
    }

    public void setPolitico(ColaboradorPolitico politico) {
        this.politico = politico;
    }

    public void pressionarDR() {
        if (gerenciaDR != null && politico != null) {
            ComandoDireitoResposta cmd = new SolicitarDireitoResposta(politico, gerenciaDR);
            cmd.executar();
        } else {
            System.out.println("[Microfone] Não foi possível registrar a solicitação de DR.");
        }
    }

    public void liga() {
        this.MicroAtivo = true;
        System.out.println("Microfone ligado.");
    }

    public void desliga() {
        this.MicroAtivo = false;
        System.out.println("Microfone desligado.");
    }

    public void passa_tempo(int tempo) {
        if (tempo <= 0) {
            System.out.println("Tempo não configurado para esta etapa.");
            return;
        }

        boolean podeSolicitarDR = (gerenciaDR != null && gerenciaDR.isRecebendoSolicitacoes());
        List<ColaboradorPolitico> candidatosDR = new ArrayList<>();

        if (podeSolicitarDR) {
            List<ColaboradorPolitico> todos = FachadaDebate.get_instance().get_gerenciador().get_politicos();
            for (ColaboradorPolitico p : todos) {
                if (!p.eh_mesmo(this.politico)) {
                    candidatosDR.add(p);
                }
            }
        }

        System.out.println("Aguardando " + tempo + " segundo(s) de fala...");

        if (podeSolicitarDR && !candidatosDR.isEmpty()) {
            System.out.println("\n[DR] >>> Para solicitar Direito de Resposta, digite o número do político e pressione ENTER:");
            for (int i = 0; i < candidatosDR.size(); i++) {
                ColaboradorPolitico p = candidatosDR.get(i);
                System.out.println("     [" + (i + 1) + "] " + p.get_nome() + " (" + p.get_partido() + ")");
            }
            System.out.println();
        }

        for (int restante = tempo; restante > 0; restante--) {
            System.out.println("  Tempo restante: " + restante + "s");

            if (podeSolicitarDR && !candidatosDR.isEmpty()) {
                try {
                    // Espera 1 segundo verificando a entrada padrão a cada 100ms
                    for (int ms = 0; ms < 1000; ms += 100) {
                        if (System.in.available() > 0) {
                            byte[] buffer = new byte[System.in.available()];
                            int lidos = System.in.read(buffer);
                            String entrada = new String(buffer, 0, lidos);
                            String[] linhas = entrada.split("\\r?\\n");
                            for (String linha : linhas) {
                                linha = linha.trim();
                                if (linha.isEmpty()) continue;
                                try {
                                    int opcao = Integer.parseInt(linha);
                                    if (opcao >= 1 && opcao <= candidatosDR.size()) {
                                        ColaboradorPolitico escolhido = candidatosDR.get(opcao - 1);
                                        System.out.println("\n[DR] Solicitando DR para " + escolhido.get_nome() + "...");
                                        escolhido.solicitar_direito_resposta();
                                    } else {
                                        System.out.println("\n[DR] Opção inválida: " + opcao);
                                    }
                                } catch (NumberFormatException nfe) {
                                    ColaboradorPolitico encontrado = null;
                                    for (ColaboradorPolitico p : candidatosDR) {
                                        if (p.get_nome().equalsIgnoreCase(linha)) {
                                            encontrado = p;
                                            break;
                                        }
                                    }
                                    if (encontrado != null) {
                                        System.out.println("\n[DR] Solicitando DR para " + encontrado.get_nome() + "...");
                                        encontrado.solicitar_direito_resposta();
                                    } else {
                                        System.out.println("\n[DR] Entrada inválida (digite o número ou nome): " + linha);
                                    }
                                }
                            }
                        }
                        Thread.sleep(100);
                    }
                } catch (IOException e) {
                    System.out.println("Erro ao ler entrada para DR: " + e.getMessage());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Contagem de tempo interrompida.");
                    return;
                }
            } else {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Contagem de tempo interrompida.");
                    return;
                }
            }
        }
        System.out.println("  Tempo esgotado.");
    }

    public boolean isMicroAtivo() {
        return MicroAtivo;
    }
}
