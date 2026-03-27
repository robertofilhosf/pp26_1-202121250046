public abstract class Publicacao {
    protected Implementador imp;

    public Publicacao(Implementador imp) {
        this.imp = imp;
    }

    public void obterDados(String tipo) {
        System.out.println("Publicacao.obterDados(" + tipo + ") chamado");
        imp.getDados(tipo);
    }

    public abstract void getTitulo();
    public abstract void getAutor(int id);
}
