package ifsc.joe.ui;

import ifsc.joe.domain.impl.*;
import ifsc.joe.enums.Direcao;

import javax.swing.*;
import java.util.Random;

public class PainelControles {

    private final Random sorteio;
    private Tela tela;
    private ButtonGroup grupoSelecao;

    private JPanel painelPrincipal;
    private JPanel painelTela;
    private JPanel painelLateral;
    private JButton bCriaAldeao;
    private JButton bCriaArqueiro;
    private JButton bCriaCavaleiro;
    private JRadioButton todosRadioButton;
    private JRadioButton aldeaoRadioButton;
    private JRadioButton arqueiroRadioButton;
    private JRadioButton cavaleiroRadioButton;
    private JButton atacarButton;
    private JButton buttonCima;
    private JButton buttonEsquerda;
    private JButton buttonBaixo;
    private JButton buttonDireita;
    private JLabel logo;
    private JButton btnLimpar;
    private JLabel lblEstatisticas;

    public PainelControles() {
        this.sorteio = new Random();
        configurarSelecaoTipo();
        configurarListeners();
        atualizarEstatisticas();
    }

    private void configurarSelecaoTipo() {
        grupoSelecao = new ButtonGroup();
        grupoSelecao.add(todosRadioButton);
        grupoSelecao.add(aldeaoRadioButton);
        grupoSelecao.add(arqueiroRadioButton);
        grupoSelecao.add(cavaleiroRadioButton);

        todosRadioButton.setSelected(true);
        getTela().setTipoSelecionado("TODOS");
    }

    private void configurarListeners() {
        configurarBotoesSelecao();
        configurarBotoesMovimento();
        configurarBotoesCriacao();
        configurarBotaoAtaque();
    }

    private void configurarBotoesSelecao() {
        todosRadioButton.addActionListener(e -> getTela().setTipoSelecionado("TODOS"));
        aldeaoRadioButton.addActionListener(e -> getTela().setTipoSelecionado("Aldeao"));
        arqueiroRadioButton.addActionListener(e -> getTela().setTipoSelecionado("Arqueiro"));
        cavaleiroRadioButton.addActionListener(e -> getTela().setTipoSelecionado("Cavaleiro"));
    }

    private void configurarBotoesMovimento() {
        buttonCima.addActionListener(e -> {
            getTela().movimentarPersonagens(Direcao.CIMA);
            atualizarEstatisticas();
        });

        buttonBaixo.addActionListener(e -> {
            getTela().movimentarPersonagens(Direcao.BAIXO);
            atualizarEstatisticas();
        });

        buttonEsquerda.addActionListener(e -> {
            getTela().movimentarPersonagens(Direcao.ESQUERDA);
            atualizarEstatisticas();
        });

        buttonDireita.addActionListener(e -> {
            getTela().movimentarPersonagens(Direcao.DIREITA);
            atualizarEstatisticas();
        });
    }

    private void configurarBotoesCriacao() {
        bCriaAldeao.addActionListener(e -> {
            criarPersonagemAleatorio("Aldeao");
            atualizarEstatisticas();
        });

        bCriaArqueiro.addActionListener(e -> {
            criarPersonagemAleatorio("Arqueiro");
            atualizarEstatisticas();
        });

        bCriaCavaleiro.addActionListener(e -> {
            criarPersonagemAleatorio("Cavaleiro");
            atualizarEstatisticas();
        });
    }

    private void configurarBotaoAtaque() {
        atacarButton.addActionListener(e -> {
            getTela().atacarPersonagens();
            atualizarEstatisticas();
        });
    }


    private void criarPersonagemAleatorio(String tipo) {
        final int PADDING = 50;
        int posX = sorteio.nextInt(painelTela.getWidth() - PADDING);
        int posY = sorteio.nextInt(painelTela.getHeight() - PADDING);

        switch (tipo.toUpperCase()) {
            case "ALDEAO":
                getTela().criarAldeao(posX, posY);
                break;
            case "ARQUEIRO":
                getTela().criarArqueiro(posX, posY);
                break;
            case "CAVALEIRO":
                getTela().criarCavaleiro(posX, posY);
                break;
        }
    }

    private void atualizarEstatisticas() {
        if (lblEstatisticas != null) {
            SwingUtilities.invokeLater(() -> {
                lblEstatisticas.setText(getTela().getEstatisticas());
            });
        }
    }

    private Tela getTela() {
        if (tela == null) {
            tela = (Tela) painelTela;
        }
        return tela;
    }

    public JPanel getPainelPrincipal() {
        return painelPrincipal;
    }

    private void createUIComponents() {
        this.painelTela = new Tela();

        if (btnLimpar == null) {
            btnLimpar = new JButton("Limpar Todos");
        }

        if (lblEstatisticas == null) {
            lblEstatisticas = new JLabel("Total: 0");
        }
    }
}