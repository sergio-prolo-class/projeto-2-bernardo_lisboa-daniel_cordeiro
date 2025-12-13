package ifsc.joe.domain.impl;

import ifsc.joe.enums.Direcao;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Random;

public abstract class Personagem {
    protected int vida, ataque, esquiva;
    protected double velocidade;
    protected String nome;
    protected int posX, posY;
    protected boolean atacando;
    protected Image icone;
    protected String nomeImagem;
    protected int vidaMaxima;
    protected int alcanceAtaque;
    protected float alpha = 1.0f;
    protected boolean morrendo = false;
    private String mensagemEsquiva;
    private long mensagemExpiraEm = 0;

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
        if (!estaVivo()) {
            return;
        }

        switch (direcao) {
            case CIMA -> this.posY -= this.velocidade;
            case BAIXO -> this.posY += this.velocidade;
            case ESQUERDA -> this.posX -= this.velocidade;
            case DIREITA -> this.posX += this.velocidade;
        }

        this.posX = Math.min(Math.max(0, this.posX), maxLargura - this.icone.getWidth(null));
        this.posY = Math.min(Math.max(0, this.posY), maxAltura - this.icone.getHeight(null));
    }

    public void desenhar(Graphics g, JPanel painel) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        String imagemParaUsar = atacando ? nomeImagem + "2" : nomeImagem;
        Image imagemAtual = carregarImagem(imagemParaUsar);
        g2d.drawImage(imagemAtual, this.posX, this.posY, painel);
        g2d.dispose();
    }

    public void setAtacando(boolean atacando) {
        this.atacando = atacando;
    }

    protected Image carregarImagem(String imagem) {
        return new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("./" + imagem + ".png"))).getImage();
    }

    public void sofrerDano(int dano) {
        Random random = new Random();
        int aleatorio = random.nextInt(101);
        if (aleatorio <= this.esquiva) {
            mostrarMensagem("Esquivou!");
            return;
        }
        this.vida = Math.max(0, this.vida - dano);
        if (this.vida == 0 && !morrendo) {
            iniciarFadeOut();
        }
    }

    public void iniciarFadeOut() {
        morrendo = true;
        Timer timer = new Timer(50, e -> {
            alpha -= 0.05f;
            if (alpha <= 0f) {
                alpha = 0f;
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }

    public void mostrarMensagem(String m) {
        this.mensagemEsquiva = m;
        this.mensagemExpiraEm = System.currentTimeMillis() + 1000;
    }

    public double calcularDistancia(Personagem outro) {
        int dx = this.posX - outro.getPosX();
        int dy = this.posY - outro.getPosY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public boolean alvoNoAlcance(Personagem alvo) {
        double distancia = calcularDistancia(alvo);
        return distancia <= this.alcanceAtaque;
    }

    public Image getIcone() {
        return this.icone;
    }

    public int getAlcanceAtaque() {
        return this.alcanceAtaque;
    }
    public boolean estaVivo() {
        return this.vida > 0;
    }

    public String getTipo() {
        return this.getClass().getSimpleName();
    }

    public String getNome() {
        return nome;
    }

    public int getVida() {
        return vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public float getAlpha() {
        return alpha;
    }

    public String getMensagemEsquiva() {
        if (System.currentTimeMillis() > mensagemExpiraEm) {
            mensagemEsquiva = null;
        }
        return mensagemEsquiva;
    }
}