package debate;

public class GUI {
    private final FachadaDebate f;

    public GUI() {
        this.f = FachadaDebate.get_instance();
    }

    public void RealizarOperacao() {
        System.out.println("Interface gráfica não implementada. Utilize a CLI.");
    }
}
