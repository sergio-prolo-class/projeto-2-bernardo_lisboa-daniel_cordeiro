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
        this.ataque = 10;
        this.velocidade = 10;
        this.esquiva = 10;
    }

    @Override
    public void alternarMontado() {
        this.montado = !this.montado;

        if (montado) {
            // Monta: volta à velocidade normal
            this.velocidade = (int)velocidadeOriginal;
            this.nomeImagem = NOME_IMAGEM;
        } else {
            // Desmonta: velocidade REDUZ pela metade (2 → 1)
            this.velocidade = (int)(velocidadeOriginal / 2);
            this.nomeImagem = "cavaleiro_desmontado";
        }

        this.icone = carregarImagem(this.nomeImagem);

        System.out.println("Cavaleiro " + (montado ? "montou" : "desmontou") +
                ". Velocidade: " + this.velocidade);
    }

    @Override
    public String atacar(Personagem alvo) {
        if (!estaVivo()) {
            return "Cavaleiro não pode atacar porque está morto!";
        }

        int dano = this.ataque;
        // Bônus de ataque quando montado
        if (montado) {
            dano += 2;
        }

        alvo.sofrerDano(dano);

        // Alterna estado para animação
        this.atacando = true;

        return String.format(
                "Cavaleiro atacou causando %d de dano em %s%s%n" +
                        "%s agora tem %d de vida",
                dano, alvo.getNome(),
                montado ? " (bônus por estar montado)" : "",
                alvo.getNome(), alvo.getVida()
        );
    }

    @Override
    public String getNome() {
        return super.getNome() + (montado ? " (Montado)" : " (Desmontado)");
    }

    public boolean estaMontado() {
        return montado;
    }
}