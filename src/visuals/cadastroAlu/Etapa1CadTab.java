package visuals.cadastroAlu;

import modulos.Memoria;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class Etapa1CadTab {

    //Ele mostra a lista de confirmação após salvar
    private DefaultListModel<Memoria> model = new DefaultListModel<>();
    private JList<Memoria> listaUI = new JList<>(model);
    JScrollPane scroll = new JScrollPane(listaUI);

    public class CamposCadastro {
        public JFormattedTextField imei;
        public JFormattedTextField ns;
    }


    public void abrirTelaDeCadastro(JFrame frame, JPanel painelInicial) {
        // Remove tudo que está no frame (para trocar o conteúdo)
        frame.getContentPane().removeAll();

        CardLayout card = new CardLayout();
        Memoria me = new Memoria();

        painelInicial.add(scroll);

        //painelInicial.add(new JLabel("Cadastre ou pesquise um tablet"));

        painelInicial.add(new JScrollPane(listaUI));

        //criação do campos
        CamposCadastro campos = new CamposCadastro();

        // Cria o painel principal que usará o CardLayout
        JPanel container = new JPanel(card);

        // --- Tela de cadastro ---
        //4 linhas e 2 colunas → total de 8 células (4 × 2 = 8)
        //10, 10 → espaçamento horizontal e vertical entre as células
        JPanel telaCadastro = new JPanel(new GridLayout(10, 2, 10, 10));
//----------------------------------------------------------------

// Imei (Integer)
        telaCadastro.add(new JLabel("Imei"));
        NumberFormatter fImei = new NumberFormatter(NumberFormat.getIntegerInstance());
        fImei.setValueClass(Integer.class);
        fImei.setAllowsInvalid(false);
        fImei.setMinimum(0);
        campos.imei = new JFormattedTextField(fImei);
        telaCadastro.add(campos.imei);

// Ns (Integer)
        telaCadastro.add(new JLabel("Ns"));
        NumberFormatter fNs = new NumberFormatter(NumberFormat.getNumberInstance());
        fNs.setValueClass(Integer.class);
        fNs.setAllowsInvalid(false);
        fNs.setMinimum(0.0);
        campos.ns = new JFormattedTextField(fNs);
        telaCadastro.add(campos.ns);

//----------------------------------------------------------------

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> card.show(container, "principal"));
        telaCadastro.add(btnVoltar, "voltar");

        JButton btnAvancar = new JButton("Avançar");

        //JButton btnSalvar = new JButton("Salvar");
        /*btnSalvar.addActionListener(e -> {
            me.setQuantidade((Integer) campos.imei.getValue());
            me.setCustoProducao((Double) campos.ns.getValue());
            Memoria me2 = new Memoria(
                    1,                      // id
                    me.getNome(),              // nome
                    me.getQuantidade(),        // quantidade
                    me.getListaIngredientes(), // lista de ingredientes
                    me.getCustoProducao(),     // custo de produção
                    me.getCustoServico(),      // custo de serviço
                    me.getLucro(),             // lucro
                    me.getPrecoTotal(),        // precoTotal (pode calcular depois)
                    me.getValidade()           // validade
            );

            //lista de confirmação é criada
            me.setLista(me2);

            //lista de confirmação é adicionada a tela
            //model.addElement(me2);

            card.show(container, "principal");
        });
        */

        Etapa2CadChp cad = new Etapa2CadChp();
        telaCadastro.add(btnAvancar, "avancar");
        btnAvancar.addActionListener(clique -> cad.abrirTelaDeCadastro2(frame, painelInicial));
        // Adiciona as telas ao CardLayout
        container.add(telaCadastro, "cadastro");
        container.add(painelInicial, "principal");

        // Adiciona o container ao frame
        frame.add(container);

        // Atualiza a tela
        frame.revalidate();
        frame.repaint();

        // Mostra a tela de cadastro primeiro
        card.show(container, "cadastro");
        me.exibirLista();

    }

}
