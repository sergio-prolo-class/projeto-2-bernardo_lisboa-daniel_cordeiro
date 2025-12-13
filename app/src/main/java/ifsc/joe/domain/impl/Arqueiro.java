package ifsc.joe.domain.impl;

import ifsc.joe.domain.api.Coletador;
import ifsc.joe.domain.api.Guerreiro;
import ifsc.joe.enums.Recurso;
import java.util.Set;

public class Arqueiro extends Personagem implements Coletador, Guerreiro {
    public static final String NOME_IMAGEM = "arqueiro";
    public static final Set<Recurso> COLETAVEIS = Set.of(Recurso.COMIDA, Recurso.MADEIRA);
    private int flechas;
    private int madeiraColetada;

    public Arqueiro(int x, int y) {
        super("Arqueiro", NOME_IMAGEM, x, y);
        inicializarAtributos();
        this.flechas = 10;
        this.madeiraColetada = 0;
    }

    @Override
    public void inicializarAtributos() {
        this.vida = 35;
        this.vidaMaxima = 35;
        this.ataque = 2;
        this.velocidade = 5;
        this.esquiva = 25;
        this.alcanceAtaque = 150;
    }

    @Override
    public boolean coletar(Recurso recurso) {
        if (!COLETAVEIS.contains(recurso)) {
            return false;
        }

        if (recurso == Recurso.MADEIRA) {
            madeiraColetada++;
        }

        return true;
    }

    @Override
    public String atacar(Personagem alvo) {
        if (!estaVivo()) {
            return getNome() + " não pode atacar porque está morto!";
        }

        if (alvo == this) {
            return getNome() + " não pode atacar a si mesmo!";
        }

        if (!alvo.estaVivo()) {
            return "Não pode atacar " + alvo.getNome() + " porque já está morto!";
        }

        if (flechas <= 0) {
            return getNome() + " não tem flechas para atacar!";
        }

        if (!alvoNoAlcance(alvo)) {
            double distancia = calcularDistancia(alvo);
            return String.format("%s está muito longe! Distância: %.1f, Alcance: %d",
                    alvo.getNome(), distancia, alcanceAtaque);
        }

        flechas--;
        int dano = this.ataque;
        alvo.sofrerDano(dano);

        return String.format("%s atirou uma flecha de longe (%dpx) causando %d de dano em %s%n" +
                        "Flechas restantes: %d | %s tem %d de vida",
                getNome(), alcanceAtaque, dano, alvo.getNome(), flechas,
                alvo.getNome(), alvo.getVida());
    }

    public String produzirFlechas() {
        if (madeiraColetada <= 0) {
            return "Arqueiro não tem madeira para produzir flechas!";
        }

        madeiraColetada--;
        flechas += 10;
        return String.format(
                "Arqueiro produziu 10 flechas com madeira.%n" +
                        "Flechas: %d | Madeira restante: %d",
                flechas, madeiraColetada
        );
    }

    public String recarregarFlechas(int quantidade) {
        if (quantidade <= 0) {
            return "Quantidade inválida de flechas!";
        }

        flechas += quantidade;
        return String.format("Arqueiro recarregou %d flechas. Total: %d", quantidade, flechas);
    }

    @Override
    public String getNome() {
        return super.getNome() + " [" + flechas + " flechas]";
    }

    public int getFlechas() {
        return flechas;
    }

    public int getMadeiraColetada() {
        return madeiraColetada;
    }
}