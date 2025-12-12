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

        this.personagens.forEach(personagem -> {
            personagem.desenhar(g, this);
            desenharBarraVida(g, personagem);
        });

        g.dispose();
    }

    private void desenharBarraVida(Graphics g, Personagem personagem) {
        int larguraBarra = 40;
        int alturaBarra = 5;
        int margemSuperior = 5;

        int barraX = personagem.getPosX();
        int barraY = personagem.getPosY() - alturaBarra - margemSuperior;

        if (barraY < 0) {
            barraY = personagem.getPosY() + personagem.icone.getHeight(null) + 2;
        }

        double porcentagemVida = personagem.getVida() / (double) personagem.getVidaMaxima();

        int larguraPreenchida = (int) (larguraBarra * porcentagemVida);

        Color corVida;
        if (porcentagemVida >= 0.75) {
            corVida = Color.GREEN;
        } else if (porcentagemVida >= 0.25) {
            corVida = Color.YELLOW;
        } else {
            corVida = Color.RED;
        }

        Color corOriginal = g.getColor();

        g.setColor(Color.DARK_GRAY);
        g.fillRect(barraX, barraY, larguraBarra, alturaBarra);

        g.setColor(corVida);
        g.fillRect(barraX, barraY, larguraPreenchida, alturaBarra);

        g.setColor(Color.BLACK);
        g.drawRect(barraX, barraY, larguraBarra, alturaBarra);

        g.setColor(corOriginal);

        if (porcentagemVida < 1.0) {
            desenharTextoVida(g, personagem, barraX, barraY, larguraBarra);
        }

        if (personagem instanceof Arqueiro) {
            Arqueiro a = (Arqueiro) personagem;
            int flechas = a.getFlechas();

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.drawString("F: " + flechas,
                    personagem.getPosX(),
                    personagem.getPosY() - 12);
        }
    }

    private void desenharTextoVida(Graphics g, Personagem personagem, int barraX, int barraY, int larguraBarra) {
        String textoVida = personagem.getVida() + "/" + personagem.getVidaMaxima();

        FontMetrics metrics = g.getFontMetrics();
        int textoX = barraX + (larguraBarra - metrics.stringWidth(textoVida)) / 2;
        int textoY = barraY - 2;

        g.setColor(Color.BLACK);
        g.drawString(textoVida, textoX + 1, textoY + 1);

        g.setColor(Color.WHITE);
        g.drawString(textoVida, textoX, textoY);
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

        for (Personagem guerreiro : personagens) {
            if (!(guerreiro instanceof Guerreiro)) continue;
            if (!guerreiro.estaVivo()) continue;

            if (!tipoSelecionado.equals("TODOS")) {
                String tipo = guerreiro.getClass().getSimpleName();
                if (!tipo.equalsIgnoreCase(tipoSelecionado)) continue;
            }

            for (Personagem alvo : personagens) {
                if (alvo == guerreiro) continue;
                if (!alvo.estaVivo()) continue;
                if (!guerreiro.alvoNoAlcance(alvo)) continue;

                Guerreiro g = (Guerreiro) guerreiro;
                resultado.append(g.atacar(alvo)).append("\n");

                realizarAnimacaoAtaque(guerreiro);

                break;
            }
        }

        return resultado.toString();
    }

    private void realizarAnimacaoAtaque(Personagem personagem) {

        personagem.setAtacando(true);
        repaint();

        Timer timer = new Timer(500, e -> {
            personagem.setAtacando(false);
            repaint();
            ((Timer) e.getSource()).stop();
        });

        timer.setRepeats(false);
        timer.start();
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
