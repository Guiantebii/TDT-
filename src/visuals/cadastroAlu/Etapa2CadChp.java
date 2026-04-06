package visuals.cadastroAlu;

import modulos.Memoria;

import visuals.cadastroAlu.Etapa3CadAlu;
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class Etapa2CadChp {

    //Ele mostra a lista de confirmação após salvar
    private DefaultListModel<Memoria> model = new DefaultListModel<>();
    private JList<Memoria> listaUI = new JList<>(model);
    JScrollPane scroll = new JScrollPane(listaUI);

    public class CamposCadastro {
        public JFormattedTextField iccid;
        public JFormattedTextField pin;
        public JFormattedTextField pin2;
        public JFormattedTextField puk;
        public JFormattedTextField puk2;
    }


    public void abrirTelaDeCadastro2(JFrame frame, JPanel painelInicial) {
        // Remove tudo que está no frame (para trocar o conteúdo)
        frame.getContentPane().removeAll();

        CardLayout card = new CardLayout();
        Memoria me = new Memoria();

        painelInicial.add(scroll);

        painelInicial.add(new JLabel("Cadastre ou pesquise um tablet"));

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

// Iccid (Integer)
        telaCadastro.add(new JLabel("Iccid"));
        NumberFormatter fIccid = new NumberFormatter(NumberFormat.getIntegerInstance());
        fIccid.setValueClass(Integer.class);
        fIccid.setAllowsInvalid(false);
        fIccid.setMinimum(0);
        campos.iccid = new JFormattedTextField(fIccid);
        telaCadastro.add(campos.iccid);

// PIN (Integer)
        telaCadastro.add(new JLabel("Pin"));
        NumberFormatter fPin = new NumberFormatter(NumberFormat.getNumberInstance());
        fPin.setValueClass(Integer.class);
        fPin.setAllowsInvalid(false);
        fPin.setMinimum(0.0);
        campos.pin = new JFormattedTextField(fPin);
        telaCadastro.add(campos.pin);

// PIN2 (Integer)
        telaCadastro.add(new JLabel("Pin2"));
        NumberFormatter fPin2 = new NumberFormatter(NumberFormat.getNumberInstance());
        fPin2.setValueClass(Integer.class);
        fPin2.setAllowsInvalid(false);
        fPin2.setMinimum(0.0);
        campos.pin2 = new JFormattedTextField(fPin2);
        telaCadastro.add(campos.pin2);

// PUK (Integer)
        telaCadastro.add(new JLabel("Puk"));
        NumberFormatter fPuk = new NumberFormatter(NumberFormat.getNumberInstance());
        fPuk.setValueClass(Integer.class);
        fPuk.setAllowsInvalid(false);
        fPuk.setMinimum(0.0);
        campos.puk = new JFormattedTextField(fPuk);
        telaCadastro.add(campos.puk);

// PUK2 (Integer)
        telaCadastro.add(new JLabel("Puk2"));
        NumberFormatter fPuk2 = new NumberFormatter(NumberFormat.getNumberInstance());
        fPuk2.setValueClass(Integer.class);
        fPuk2.setAllowsInvalid(false);
        fPuk2.setMinimum(0.0);
        campos.puk2 = new JFormattedTextField(fPuk2);
        telaCadastro.add(campos.puk2);


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

        Etapa3CadAlu cad = new Etapa3CadAlu();
        telaCadastro.add(btnAvancar, "avancar");
        btnAvancar.addActionListener(clique -> cad.abrirTelaDeCadastro3(frame, painelInicial));
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
