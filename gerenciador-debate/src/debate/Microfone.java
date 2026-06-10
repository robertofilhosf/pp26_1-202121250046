package debate;

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
        System.out.println("Aguardando " + tempo + " segundo(s) de fala...");
        for (int restante = tempo; restante > 0; restante--) {
            System.out.println("  Tempo restante: " + restante + "s");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Contagem de tempo interrompida.");
                return;
            }
        }
        System.out.println("  Tempo esgotado.");
    }

    public boolean isMicroAtivo() {
        return MicroAtivo;
    }
}
