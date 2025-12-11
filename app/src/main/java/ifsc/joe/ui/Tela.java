package ifsc.joe.ui;

import ifsc.joe.domain.impl.*;
import ifsc.joe.enums.Direcao;

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

    public void atacarPersonagens() {
        this.personagens.stream()
                .filter(p -> tipoSelecionado.equals("TODOS") || p.getTipo().equalsIgnoreCase(tipoSelecionado))
                .forEach(Personagem::atacar);

        this.repaint();
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
