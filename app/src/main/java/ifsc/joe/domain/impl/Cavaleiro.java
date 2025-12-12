package ifsc.joe.domain.impl;

import ifsc.joe.domain.api.ComMontaria;
import ifsc.joe.domain.api.Guerreiro;

public class Cavaleiro extends Personagem implements  ComMontaria, Guerreiro {
    public static final String NOME_IMAGEM = "cavaleiro";
    private boolean montado;
    private double velocidadeOriginal;

    public Cavaleiro(int x, int y) {
        super("Cavaleiro", NOME_IMAGEM, x, y);
        inicializarAtributos();
        this.montado = true;
        this.velocidadeOriginal = this.velocidade;
    }

    @Override
    public void inicializarAtributos() {
        this.vida = 10;
        this.vidaMaxima = 10;
        this.ataque = 10;
        this.velocidade = 10;
        this.esquiva = 10;
        this.alcanceAtaque = 15;
    }

    @Override
    public void alternarMontado() {
        this.montado = !this.montado;

        if (montado) {
            this.velocidade = (int)velocidadeOriginal;
            this.nomeImagem = NOME_IMAGEM;
        } else {
            this.velocidade = (int)(velocidadeOriginal / 2);
            this.nomeImagem = "cavaleiro_desmontado";
        }

        this.icone = carregarImagem(this.nomeImagem);
    }

    @Override
    public String atacar(Personagem alvo) {

        if (!estaVivo()) {
            return "Cavaleiro não pode atacar porque está morto!";
        }

        if (alvo == this) {
            return "Cavaleiro não pode atacar a si mesmo!";
        }

        if (!alvo.estaVivo()) {
            return "Não pode atacar " + alvo.getNome() + " porque já está morto!";
        }

        if (!alvoNoAlcance(alvo)) {
            double distancia = calcularDistancia(alvo);
            return String.format("%s está longe demais para ataque corpo a corpo! " +
                            "Distância: %.1f, Alcance: %d",
                    alvo.getNome(), distancia, alcanceAtaque);
        }

        int dano = this.ataque;

        alvo.sofrerDano(dano);

        return String.format("%s atacou corpo a corpo (%dpx) causando %d de dano em %s%s%n" +
                        "%s tem %d de vida restante",
                getNome(), alcanceAtaque, dano, alvo.getNome(),
                montado ? " (bônus montado)" : "",
                alvo.getNome(), alvo.getVida());
    }

    @Override
    public String getNome() {
        return super.getNome() + (montado ? " (Montado)" : " (Desmontado)");
    }

    public boolean estaMontado() {
        return montado;
    }
}