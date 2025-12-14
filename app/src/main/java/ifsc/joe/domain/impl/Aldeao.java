package ifsc.joe.domain.impl;

import ifsc.joe.domain.api.Coletador;
import ifsc.joe.domain.api.ComMontaria;
import ifsc.joe.enums.Recurso;
import java.util.Set;

public class Aldeao extends Personagem implements Coletador, ComMontaria{
    public static final String NOME_IMAGEM = "aldeao";
    public static final Set<Recurso> COLETAVEIS = Set.of(Recurso.COMIDA, Recurso.OURO);
    private boolean montado;
    private final double velocidadeOriginal;

    public Aldeao(int x, int y) {
        super("Aldeao", NOME_IMAGEM, x, y);
        inicializarAtributos();
        this.montado = false;
        this.velocidadeOriginal = this.velocidade;
    }

    @Override
    public void inicializarAtributos() {
        this.vida = 25;
        this.vidaMaxima = 25;
        this.velocidade = 4;
        this.esquiva = 10;
    }

    @Override
    public boolean coletar(Recurso recurso) { //Apenas implementado para cumprir a base sem fazer a parte de coleta
        boolean podeColetar = COLETAVEIS.contains(recurso);
        if (podeColetar) {
        }
        return podeColetar;
    }

    @Override
    public void alternarMontado() {
        this.montado = !this.montado;

        if (montado) {
            this.velocidade = (int)(velocidadeOriginal * 2);
            this.nomeImagem = "aldeao_montado";
        } else {
            this.velocidade = (int)velocidadeOriginal;
            this.nomeImagem = NOME_IMAGEM;
        }

        this.icone = carregarImagem(this.nomeImagem);
    }

    @Override
    public String getNome() {
        return super.getNome() + (montado ? " (Montado)" : "");
    }
}