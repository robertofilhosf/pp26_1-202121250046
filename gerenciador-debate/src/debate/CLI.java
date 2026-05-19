package debate;

import java.util.Scanner;

public class CLI {
    private final FachadaDebate f;
    private final Scanner scanner;

    public CLI() {
        this.f = FachadaDebate.get_instance();
        this.scanner = new Scanner(System.in);
    }

    public void RealizarOperacao() {
        boolean executando = true;
        while (executando) {
            exibirCabecalho();
            exibirMenu();
            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1" -> configurarTempos();
                case "2" -> cadastrarPolitico();
                case "3" -> f.sorteio_inquiridor();
                case "4" -> escolherInquirido();
                case "5" -> f.executa_debate(f.get_config(), f.get_log());
                case "6" -> f.acessar_log();
                case "7" -> cadastrarEleitor();
                case "0" -> {
                    System.out.println("Encerrando...");
                    executando = false;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private void exibirCabecalho() {
        System.out.println("=== Gerenciador de Debate Político (CLI) ===");
        System.out.println("Tempos do debate " + f.get_config().get_texto_configuracao_menu());
        System.out.println();
    }

    private void exibirMenu() {
        System.out.println("Menu:");
        System.out.println("  1 - Configurar tempos do debate " + f.get_config().get_texto_configuracao_menu());
        System.out.println("  2 - Cadastrar político");
        System.out.println("  3 - Sortear inquiridor");
        System.out.println("  4 - Escolher inquirido");
        System.out.println("  5 - Executar debate");
        System.out.println("  6 - Acessar log");
        System.out.println("  7 - Cadastrar eleitor para notificações");
        System.out.println("  0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void configurarTempos() {
        ConfiguraTempo atual = f.get_config();
        System.out.println("\nTempos atuais: " + atual.resumo_tempos());
        System.out.println("Informe a duração de cada etapa em segundos (mínimo 1).");
        System.out.println("Pressione Enter para manter o valor atual.\n");

        int pergunta = lerInteiroComPadrao("Tempo da pergunta (s)", atual.get_temp_pergunta());
        int resposta = lerInteiroComPadrao("Tempo da resposta (s)", atual.get_temp_resposta());
        int replica = lerInteiroComPadrao("Tempo da réplica (s)", atual.get_temp_replica());
        int treplica = lerInteiroComPadrao("Tempo da tréplica (s)", atual.get_temp_treplica());
        f.configuracao(pergunta, resposta, replica, treplica);
    }

    private void cadastrarPolitico() {
        System.out.print("Nome do político: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Partido: ");
        String partido = scanner.nextLine().trim();
        f.cadastrar_politicos(nome, partido, f.get_mediador());
    }

    private void cadastrarEleitor() {
        System.out.print("Nome do eleitor: ");
        String nomeEleitor = scanner.nextLine().trim();
        System.out.print("Nome do candidato de preferência: ");
        String nomeCandidato = scanner.nextLine().trim();
        System.out.print("Partido do candidato: ");
        String partidoCandidato = scanner.nextLine().trim();
        f.cadastrar_eleitor(nomeEleitor, nomeCandidato, partidoCandidato);
    }

    private void escolherInquirido() {
        System.out.print("Nome do inquirido: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Partido do inquirido: ");
        String partido = scanner.nextLine().trim();
        f.escolher_inquirido(nome, partido);
    }

    private int lerInteiroComPadrao(String rotulo, int valorAtual) {
        while (true) {
            System.out.print(rotulo + " [" + valorAtual + "]: ");
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                return valorAtual;
            }
            try {
                int valor = Integer.parseInt(entrada);
                if (valor < 1) {
                    System.out.println("Use pelo menos 1 segundo.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Informe um número inteiro.");
            }
        }
    }

    public static void main(String[] args) {
        CLI cli = new CLI();
        cli.RealizarOperacao();
    }
}
