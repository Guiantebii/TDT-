package visuals;

import visuals.cadastroAlu.Etapa1CadTab;

import javax.swing.*;
import java.awt.*;

public class Menu {

    Etapa1CadTab cad = new Etapa1CadTab();
    JFrame frame = new JFrame("Controle de Tablets");
    JPanel painelInicial = new JPanel();

    public void telaInicial() {

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Painel principal com BorderLayout
        JPanel painelInicial = new JPanel(new BorderLayout());

        // Card central com borda e fundo branco
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Topo do card: botão + e título
        JPanel topoCard = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton botaoMais = new JButton("+");
        botaoMais.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel titulo = new JLabel("Adicionar Aluno");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        topoCard.add(botaoMais);
        topoCard.add(titulo);
        card.add(topoCard, BorderLayout.NORTH);

        // Conteúdo principal do card: tabela + ícones lado a lado
        JPanel conteudo = new JPanel(new BorderLayout());

        // Painel da tabela (esquerda)
        JPanel areaTabela = new JPanel();
        areaTabela.setLayout(new BoxLayout(areaTabela, BoxLayout.Y_AXIS));

        // Cabeçalho da tabela
        JPanel cabecalho = new JPanel(new GridLayout(1, 4));
        cabecalho.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        cabecalho.add(criarCelula("NomeAlu"));
        cabecalho.add(criarCelula("Iccid"));
        cabecalho.add(criarCelula("Imei"));
        cabecalho.add(criarCelula("dataEntrega"));
        areaTabela.add(cabecalho);

        // Exemplo inicial de 3 linhas
        for (int i = 0; i < 3; i++) {
            areaTabela.add(criarLinhaTabela("NomeAlu", "Iccid", "Imei", "dataEntrega"));
        }

        conteudo.add(areaTabela, BorderLayout.CENTER);

        // Painel dos ícones (direita), com layout vertical
        JPanel painelIcones = new JPanel();
        painelIcones.setLayout(new BoxLayout(painelIcones, BoxLayout.Y_AXIS));
        painelIcones.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // espaçamento à esquerda

        // Ícones alinhados em linhas verticais, um grupo por linha
        for (int i = 0; i < 3; i++) {
            JPanel linhaIcones = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));

            JButton ver = new JButton("👁");
            JButton editar = new JButton("✏");
            JButton excluir = new JButton("🗑");

            // Visual limpo dos botões
            JButton[] botoes = {ver, editar, excluir};
            for (JButton b : botoes) {
                b.setBorderPainted(false);
                b.setContentAreaFilled(false);
                b.setFocusPainted(false);
            }

            linhaIcones.add(ver);
            linhaIcones.add(editar);
            linhaIcones.add(excluir);

            painelIcones.add(linhaIcones);
        }

        conteudo.add(painelIcones, BorderLayout.EAST);

        card.add(conteudo, BorderLayout.CENTER);

        // Centraliza o card no painel principal
        JPanel container = new JPanel(new GridBagLayout());
        container.add(card);
        painelInicial.add(container, BorderLayout.CENTER);

        // Ação do botão "+": adiciona nova linha e novo grupo de ícones
        botaoMais.addActionListener(e -> {
            areaTabela.add(criarLinhaTabela("NovoNome", "NovoIccid", "NovoImei", "NovaData"));
            JPanel linhaIcones = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            JButton ver = new JButton("👁");
            JButton editar = new JButton("✏");
            JButton excluir = new JButton("🗑");
            JButton[] botoes = {ver, editar, excluir};
            for (JButton b : botoes) {
                b.setBorderPainted(false);
                b.setContentAreaFilled(false);
                b.setFocusPainted(false);
            }
            linhaIcones.add(ver);
            linhaIcones.add(editar);
            linhaIcones.add(excluir);
            painelIcones.add(linhaIcones);

            // Atualiza a interface
            areaTabela.revalidate();
            areaTabela.repaint();
            painelIcones.revalidate();
            painelIcones.repaint();
        });

        frame.add(painelInicial);
        frame.setVisible(true);
    }

    // Cria uma linha da tabela (sem ícones)
    private JPanel criarLinhaTabela(String nome, String iccid, String imei, String data) {
        JPanel linha = new JPanel(new GridLayout(1, 4));
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        linha.add(criarCelula(nome));
        linha.add(criarCelula(iccid));
        linha.add(criarCelula(imei));
        linha.add(criarCelula(data));
        return linha;
    }

    // Cria célula com borda para a tabela
    private JLabel criarCelula(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return label;
    }


    /*
    public void telaInicial() {
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ícone "+"
        ImageIcon iconeMais = new ImageIcon("C:\\Users\\josee\\Documents\\Projeto POO\\Icones\\iconeMais.png");
        Image imgMais = iconeMais.getImage();
        Image imgScaleMais = imgMais.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon iconeRedimensionadoMais = new ImageIcon(imgScaleMais);

        JPanel painelInicial = new JPanel();
        painelInicial.setLayout(null);

        // 🔽 PAINEL QUE VAI GUARDAR OS BLOCOS
        JPanel painelLista = new JPanel();
        painelLista.setLayout(new BoxLayout(painelLista, BoxLayout.Y_AXIS));

        //cabeçalho
        JPanel cabecalho = new JPanel(new GridLayout(1, 4));
        cabecalho.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        cabecalho.add(criarCelula("Nome"));
        cabecalho.add(criarCelula("Iccid"));
        cabecalho.add(criarCelula("Imei"));
        cabecalho.add(criarCelula("Entrega"));

        painelLista.add(cabecalho);

        JScrollPane scroll = new JScrollPane(painelLista);
        scroll.setBounds(20, 50, 350, 150);
        painelInicial.add(scroll);

        // 🔽 BOTÃO "+"
        JButton botaoMais = new JButton(iconeRedimensionadoMais);
        botaoMais.setBounds(20, 10, 30, 30);

        botaoMais.setBorderPainted(false);
        botaoMais.setContentAreaFilled(false);
        botaoMais.setFocusPainted(false);

        painelInicial.add(botaoMais);

        // 🔽 AÇÃO DO BOTÃO
        botaoMais.addActionListener(e -> {
            JPanel novoBloco = criarBloco("João", "123", "456", "01/01");
            painelLista.add(novoBloco);

            painelLista.revalidate();
            painelLista.repaint();
        });

        // texto
        JLabel labelTexto = new JLabel("Adicionar Aluno");
        labelTexto.setBounds(60, 10, 250, 30);
        painelInicial.add(labelTexto);

        frame.add(painelInicial);

        menuCriar();
        frame.setVisible(true);
    }

    private JPanel criarBloco(String nome, String iccid, String imei, String data) {
        JPanel bloco = new JPanel(new GridLayout(1, 4));
        bloco.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        bloco.add(criarCelula(nome));
        bloco.add(criarCelula(iccid));
        bloco.add(criarCelula(imei));
        bloco.add(criarCelula(data));

        return bloco;
    }

    private JLabel criarCelula(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);

        label.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 🔥 divisória
        return label;
    }
*/

//---------------------------------------------------------------------------------
    public void menuCriar() {
        // Cria a barra de menu
        JMenuBar menuBar = new JMenuBar();
        // Define a barra de menu no JFrame
        frame.setJMenuBar(menuBar);

        JMenu menuAlunos = new JMenu("Alunos");
            JMenuItem cadastrarAlu = new JMenuItem("Cadastrar");
            JMenuItem visualizarAlu = new JMenuItem("Visualizar");
            JMenuItem alterarAlu = new JMenuItem("Alterar");
            JMenuItem excluirAlu = new JMenuItem("Excluir");
        menuBar.add(menuAlunos);
            menuAlunos.add(cadastrarAlu);
                cadastrarAlu.addActionListener(clique -> cad.abrirTelaDeCadastro(frame, painelInicial));
            menuAlunos.add(visualizarAlu);
            menuAlunos.add(alterarAlu);
            menuAlunos.add(excluirAlu);

        JMenu menuTablets = new JMenu("Tablets");
            JMenuItem cadastrarTab= new JMenuItem("Cadastrar");
            JMenuItem visualizarTab = new JMenuItem("Visualizar");
            JMenuItem alterarTab = new JMenuItem("Alterar");
            JMenuItem excluirTab = new JMenuItem("Excluir");
        menuBar.add(menuTablets);
            menuTablets.add(cadastrarTab);
                //cadastrarTab.addActionListener(clique -> cad.abrirTelaDeCadastroTab(frame, painelInicial));
        menuTablets.add(visualizarTab);
        menuTablets.add(alterarTab);
        menuTablets.add(excluirTab);

        JMenu menuChips = new JMenu("Chips");
            JMenuItem cadastrarChp = new JMenuItem("Cadastrar");
            JMenuItem visualizarChp = new JMenuItem("Visualizar");
            JMenuItem alterarChp = new JMenuItem("Alterar");
            JMenuItem excluirChp = new JMenuItem("Excluir");
        menuBar.add(menuChips);
            menuChips.add(cadastrarChp);
        //cadastrarTab.addActionListener(clique -> cad.abrirTelaDeCadastroChp(frame, painelInicial));
        menuChips.add(visualizarChp);
        menuChips.add(alterarChp);
        menuChips.add(excluirChp);

        JMenu menuDevolucao = new JMenu("Devolucao");
            JMenuItem cadastrarDev = new JMenuItem("Cadastrar");
            JMenuItem visualizarDev = new JMenuItem("Visualizar");
            JMenuItem alterarDev = new JMenuItem("Alterar");
            JMenuItem excluirDev = new JMenuItem("Excluir");
        menuBar.add(menuDevolucao);
            menuDevolucao.add(cadastrarDev);
        //cadastrarTab.addActionListener(clique -> cad.abrirTelaDeCadastroTab(frame, painelInicial));
        menuDevolucao.add(visualizarDev);
        menuDevolucao.add(alterarDev);
        menuDevolucao.add(excluirDev);

        JMenu menuManutencao = new JMenu("Manutencao");
            JMenuItem cadastrarMtn = new JMenuItem("Cadastrar");
            JMenuItem visualizarMtn = new JMenuItem("Visualizar");
            JMenuItem alterarMtn = new JMenuItem("Alterar");
            JMenuItem excluirMtn = new JMenuItem("Excluir");
        menuBar.add(menuManutencao);
            menuManutencao.add(cadastrarMtn);
        //cadastrarTab.addActionListener(clique -> cad.abrirTelaDeCadastroTab(frame, painelInicial));
        menuManutencao.add(visualizarMtn);
        menuManutencao.add(alterarMtn);
        menuManutencao.add(excluirMtn);

    }
}