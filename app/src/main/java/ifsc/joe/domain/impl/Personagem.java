package ifsc.joe.domain.impl;

import ifsc.joe.enums.Direcao;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public abstract class Personagem {
    protected int vida, ataque, velocidade, esquiva;
    protected String nome;
    protected int posX, posY;
    protected boolean atacando;
    protected Image icone;
    protected String nomeImagem;

    public Personagem(String nome, String nomeImagem, int x, int y) {
        this.nome = nome;
        this.nomeImagem = nomeImagem;
        this.posX = x;
        this.posY = y;
        this.icone = carregarImagem(nomeImagem);
    }

    // Método abstrato para inicializar atributos específicos
    public abstract void inicializarAtributos();

    public void mover(Direcao direcao, int maxLargura, int maxAltura) {
        switch (direcao) {
            case CIMA     -> this.posY -= this.velocidade;
            case BAIXO    -> this.posY += this.velocidade;
            case ESQUERDA -> this.posX -= this.velocidade;
            case DIREITA  -> this.posX += this.velocidade;
        }

        this.posX = Math.min(Math.max(0, this.posX), maxLargura - this.icone.getWidth(null));
        this.posY = Math.min(Math.max(0, this.posY), maxAltura - this.icone.getHeight(null));
    }

    public void desenhar(Graphics g, JPanel painel) {
        this.icone = this.carregarImagem(nomeImagem + (atacando ? "2" : ""));
        g.drawImage(this.icone, this.posX, this.posY, painel);
    }

    public void setAtacando(boolean atacando) {
        this.atacando = atacando;
    }


    protected Image carregarImagem(String imagem) {
        return new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("./" + imagem + ".png")
        )).getImage();
    }

    public void sofrerDano(int dano) {
        this.vida = Math.max(0, this.vida - dano);
        System.out.println(this.nome + " sofreu " + dano + " de dano. Vida: " + this.vida);
    }

    public boolean estaVivo() {
        return this.vida > 0;
    }

    public String getTipo() { return this.getClass().getSimpleName(); }
    public String getNome() { return nome; }
    public int getVida() { return vida; }
}