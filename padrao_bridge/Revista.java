public class Revista extends Publicacao {

    public Revista(Implementador imp) {
        super(imp);
    }

    public void getArtigo() {
        System.out.println("Revista.getArtigo() chamado");
    }

    @Override
    public void getTitulo() {
        System.out.println("Revista.getTitulo() chamado");
    }

    @Override
    public void getAutor(int id) {
        System.out.println("Revista.getAutor(" + id + ") chamado");
    }
}
