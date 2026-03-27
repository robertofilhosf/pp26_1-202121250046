public class Livro extends Publicacao {

    public Livro(Implementador imp) {
        super(imp);
    }

    public void getISBN() {
        System.out.println("Livro.getISBN() chamado");
    }

    @Override
    public void getTitulo() {
        System.out.println("Livro.getTitulo() chamado");
    }

    @Override
    public void getAutor(int id) {
        System.out.println("Livro.getAutor(" + id + ") chamado");
    }
}
