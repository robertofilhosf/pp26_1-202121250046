public class Cliente {
    public static void main(String[] args) {

        Implementador bd = new PublicacaoImpBD();
        Implementador xml = new PublicacaoImpXML();

        Publicacao livro = new Livro(bd);
        Publicacao revista = new Revista(xml);

        System.out.println("=== Livro com BD ===");
        livro.obterDados("Livro");
        livro.getTitulo();
        livro.getAutor(1);

        System.out.println("\n=== Revista com XML ===");
        revista.obterDados("Revista");
        revista.getTitulo();
        revista.getAutor(2);
    }
}
