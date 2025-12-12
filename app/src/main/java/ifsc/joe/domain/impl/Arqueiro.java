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
        this.vida = 10;
        this.ataque = 10;
        this.velocidade = 10;
        this.esquiva = 10;
    }

    @Override
    public boolean coletar(Recurso recurso) {
        if (!COLETAVEIS.contains(recurso)) {
            System.out.println("Arqueiro não pode coletar: " + recurso);
            return false;
        }

        if (recurso == Recurso.MADEIRA) {
            madeiraColetada++;
            System.out.println("Arqueiro coletou madeira. Total: " + madeiraColetada);
        } else {
            System.out.println("Arqueiro coletou comida");
        }

        return true;
    }

    @Override
    public String atacar(Personagem alvo) {
        if (flechas <= 0) {
            return "Arqueiro não tem flechas para atacar!";
        }

        if (!estaVivo()) {
            return "Arqueiro não pode atacar porque está morto!";
        }

        flechas--;
        int dano = this.ataque;
        alvo.sofrerDano(dano);

        // Alterna estado para animação
        this.atacando = true;

        return String.format(
                "Arqueiro atirou uma flecha causando %d de dano em %s%n" +
                        "Flechas restantes: %d%n" +
                        "%s agora tem %d de vida",
                dano, alvo.getNome(), flechas, alvo.getNome(), alvo.getVida()
        );
    }

    // Métodos específicos do Arqueiro
    public String produzirFlechas() {
        if (madeiraColetada <= 0) {
            return "Arqueiro não tem madeira para produzir flechas!";
        }

        madeiraColetada--;
        flechas += 10; // Cada madeira produz 10 flechas
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