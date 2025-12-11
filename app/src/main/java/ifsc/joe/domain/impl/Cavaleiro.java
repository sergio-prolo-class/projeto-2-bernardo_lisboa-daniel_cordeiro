package ifsc.joe.domain.impl;

public class Cavaleiro extends Personagem {
    public static final String NOME_IMAGEM = "cavaleiro";

    public Cavaleiro(int x, int y) {
        super("Cavaleiro", NOME_IMAGEM, x, y);
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