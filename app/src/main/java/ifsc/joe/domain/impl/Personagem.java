package ifsc.joe.domain.impl;

import ifsc.joe.enums.Direcao;

import java.awt.*;

public abstract class Personagem {

    protected int vida, ataque, velocidade, esquiva;
    protected String nome;
    protected int posX, posY;
    protected boolean atacando;
    protected Image icone;

    public void mover(Direcao direcao, int maxLargura, int maxAltura) {
        switch (direcao) {
            case CIMA     -> this.posY -= 10;
            case BAIXO    -> this.posY += 10;
            case ESQUERDA -> this.posX -= 10;
            case DIREITA  -> this.posX += 10;
        }

        //Não deixa a imagem ser desenhada fora dos limites do JPanel pai
        this.posX = Math.min(Math.max(0, this.posX), maxLargura - this.icone.getWidth(null));
        this.posY = Math.min(Math.max(0, this.posY), maxAltura - this.icone.getHeight(null));
    }
}


