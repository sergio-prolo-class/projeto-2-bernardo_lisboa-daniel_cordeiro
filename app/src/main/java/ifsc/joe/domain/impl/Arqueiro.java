package ifsc.joe.domain.impl;

public class Arqueiro extends Personagem {
    public static final String NOME_IMAGEM = "arqueiro";

    public Arqueiro(int x, int y) {
        super("Arqueiro", NOME_IMAGEM, x, y);
        inicializarAtributos();
    }

    @Override
    public void inicializarAtributos() {
        this.vida = 10;
        this.ataque = 10;
        this.velocidade = 10;
        this.esquiva = 10;
    }
}