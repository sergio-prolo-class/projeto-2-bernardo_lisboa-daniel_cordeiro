package ifsc.joe.ui;

import ifsc.joe.domain.impl.*;
import ifsc.joe.enums.*;
import ifsc.joe.domain.api.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Tela extends JPanel{

    private final Set<Personagem> personagens;
    private String tipoSelecionado = "TODOS";

    public Tela() {
        this.setBackground(Color.white);
        this.personagens = new HashSet<>();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        this.personagens.forEach(personagem -> personagem.desenhar(g, this));

        g.dispose();
    }

    public void criarPersonagem(Personagem personagem) {
        personagem.desenhar(super.getGraphics(), this);
        this.personagens.add(personagem);
        this.repaint();
    }

    public void movimentarPersonagens(Direcao direcao) {
        this.personagens.stream()
                .filter(p -> tipoSelecionado.equals("TODOS") || p.getTipo().equalsIgnoreCase(tipoSelecionado))
                .forEach(p -> p.mover(direcao, this.getWidth(), this.getHeight()));

        this.repaint();
    }

    public String atacarPersonagens() {
        StringBuilder resultado = new StringBuilder();

        // Encontra guerreiros do tipo selecionado
        var guerreiros = this.personagens.stream()
                .filter(p -> tipoSelecionado.equals("TODOS") || p.getTipo().equalsIgnoreCase(tipoSelecionado))
                .filter(p -> p instanceof Guerreiro && p.getVida() > 0)
                .toList();

        // Encontra alvos vivos
        var alvos = this.personagens.stream()
                .filter(p -> p.getVida() > 0)
                .filter(alvo -> !alvo.getTipo().equalsIgnoreCase(tipoSelecionado))
                .toList();

        if (guerreiros.isEmpty()) {
            return "Nenhum guerreiro do tipo " + tipoSelecionado + " encontrado!";
        }

        if (alvos.isEmpty()) {
            return "Nenhum alvo disponível para ataque!";
        }

        // Cada guerreiro ataca um alvo
        for (int i = 0; i < Math.min(guerreiros.size(), alvos.size()); i++) {
            Personagem guerreiro = guerreiros.get(i);
            Personagem alvo = alvos.get(i % alvos.size());

            if (guerreiro != alvo) {
                Guerreiro g = (Guerreiro) guerreiro;
                String resultadoAtaque = g.atacar(alvo);
                resultado.append(resultadoAtaque).append("\n---\n");

                // Ativa animação de ataque
                guerreiro.setAtacando(true);
            }
        }

        this.repaint();
        return resultado.toString();
    }

    public void setTipoSelecionado(String tipo) {
        this.tipoSelecionado = tipo.toUpperCase();
    }

    public String getEstatisticas() {
        long total = personagens.size();
        long aldeoes = personagens.stream().filter(p -> p instanceof Aldeao).count();
        long arqueiros = personagens.stream().filter(p -> p instanceof Arqueiro).count();
        long cavaleiros = personagens.stream().filter(p -> p instanceof Cavaleiro).count();

        return String.format("Total: %d | Aldeões: %d | Arqueiros: %d | Cavaleiros: %d",
                total, aldeoes, arqueiros, cavaleiros);
    }

    public String coletarRecursos(Recurso recurso) {
        StringBuilder resultado = new StringBuilder();

        this.personagens.stream()
                .filter(p -> tipoSelecionado.equals("TODOS") || p.getTipo().equalsIgnoreCase(tipoSelecionado))
                .filter(p -> p instanceof Coletador)
                .forEach(p -> {
                    Coletador coletador = (Coletador) p;
                    boolean coletou = coletador.coletar(recurso);
                    resultado.append(p.getNome())
                            .append(": ")
                            .append(coletou ? "Coletou " : "Não pode coletar ")
                            .append(recurso.toString().toLowerCase())
                            .append("\n");
                });

        this.repaint();
        return resultado.toString();
    }

    public void criarAldeao(int x, int y) {
        criarPersonagem(new Aldeao(x, y));
    }

    public void criarArqueiro(int x, int y) {
        criarPersonagem(new Arqueiro(x, y));
    }

    public void criarCavaleiro(int x, int y) {
        criarPersonagem(new Cavaleiro(x, y));
    }
}
