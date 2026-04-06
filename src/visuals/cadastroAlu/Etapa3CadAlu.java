package visuals.cadastroAlu;

import modulos.Memoria;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import javax.swing.text.DateFormatter;
import javax.swing.text.NumberFormatter;

public class Etapa3CadAlu {

    //Ele mostra a lista de confirmação após salvar
    private DefaultListModel<Memoria> model = new DefaultListModel<>();
    private JList<Memoria> listaUI = new JList<>(model);
    JScrollPane scroll = new JScrollPane(listaUI);

    public class CamposCadastro {
        public JFormattedTextField eol;
        public JTextField nome;
        public JFormattedTextField dataDeNasc;
        public JTextField turma;
        public JFormattedTextField tel1;
        public JFormattedTextField tel2;
    }


    public void abrirTelaDeCadastro3(JFrame frame, JPanel painelInicial) {
        // Remove tudo que está no frame (para trocar o conteúdo)
        frame.getContentPane().removeAll();

        CardLayout card = new CardLayout();
        Memoria me = new Memoria();

        painelInicial.add(scroll);

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

// Eol (Integer)
        telaCadastro.add(new JLabel("Eol"));
        NumberFormatter fEol = new NumberFormatter(NumberFormat.getIntegerInstance());
        fEol.setValueClass(Integer.class);
        fEol.setAllowsInvalid(false);
        fEol.setMinimum(0);
        campos.eol = new JFormattedTextField(fEol);
        telaCadastro.add(campos.eol);

// Nome
        telaCadastro.add(new JLabel("Nome"));
        campos.nome = new JTextField();
        telaCadastro.add(campos.nome);

// data de nascimento
        telaCadastro.add(new JLabel("Data de Nascimento"));
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        DateFormatter fdataDeNasc = new DateFormatter(formatoData);
        campos.dataDeNasc = new JFormattedTextField(fdataDeNasc);
        telaCadastro.add(campos.dataDeNasc);

// turma
        telaCadastro.add(new JLabel("Turma"));
        campos.turma = new JTextField();
        telaCadastro.add(campos.turma);

// Tel 1 (Integer)
        telaCadastro.add(new JLabel("Telefone 1"));
        NumberFormatter fTel1 = new NumberFormatter(NumberFormat.getIntegerInstance());
        fTel1.setValueClass(Integer.class);
        fTel1.setAllowsInvalid(false);
        fTel1.setMinimum(0);
        campos.tel1 = new JFormattedTextField(fTel1);
        telaCadastro.add(campos.tel1);

// Tel 2 (Integer)
        telaCadastro.add(new JLabel("Eol"));
        NumberFormatter fTel2 = new NumberFormatter(NumberFormat.getIntegerInstance());
        fTel2.setValueClass(Integer.class);
        fTel2.setAllowsInvalid(false);
        fTel2.setMinimum(0);
        campos.tel2 = new JFormattedTextField(fTel2);
        telaCadastro.add(campos.tel2);

        /*
// imei (Integer)
        telaCadastro.add(new JLabel("Imei"));
        NumberFormatter fImei = new NumberFormatter(NumberFormat.getIntegerInstance());
        fImei.setValueClass(Integer.class);
        fImei.setAllowsInvalid(false);
        fImei.setMinimum(0);
        campos.imei = new JFormattedTextField(fImei);
        telaCadastro.add(campos.imei);

// iccid (integer
        telaCadastro.add(new JLabel("Iccid"));
        NumberFormatter fIccid = new NumberFormatter(NumberFormat.getIntegerInstance());
        fIccid.setValueClass(Integer.class);
        fIccid.setAllowsInvalid(false);
        fIccid.setMinimum(0);
        campos.iccid = new JFormattedTextField(fIccid);
        telaCadastro.add(campos.iccid);
        */
//----------------------------------------------------------------

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> card.show(container, "principal"));
        telaCadastro.add(btnVoltar, "voltar");

/*
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            me.setNome(campos.nome.getText());
            me.setQuantidade((Integer) campos.quantidade.getValue());
            me.setListaIngredientes(campos.listaIngredientes.getText());
            me.setCustoProducao((Double) campos.custoProducao.getValue());
            me.setCustoServico((Double) campos.custoServico.getValue());
            me.setLucro((Double) campos.lucro.getValue());
            me.setPrecoTotal((Double) campos.precoTotal.getValue());
            me.setValidade((Date) campos.validade.getValue());
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
        JButton btnAvancar = new JButton("Avançar");
        telaCadastro.add(btnAvancar, "avancar");


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
