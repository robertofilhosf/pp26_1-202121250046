public class PublicacaoImpBD implements Implementador {
    @Override
    public void getDados(String tipo) {
        System.out.println("PublicacaoImpBD.getDados(" + tipo + ") chamado");
    }
}
