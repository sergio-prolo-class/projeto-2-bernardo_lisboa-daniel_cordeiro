package ifsc.joe.domain.impl;

public class Aldeao extends Personagem {
    public static final String NOME_IMAGEM = "aldeao";

    public Aldeao(int x, int y) {
        super("Aldeao", NOME_IMAGEM, x, y);
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