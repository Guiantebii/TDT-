package visuals.cadastroAlu;

import modulos.Memoria;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Etapa4CadDev {

    //Ele mostra a lista de confirmação após salvar
    private DefaultListModel<Memoria> model = new DefaultListModel<>();
    private JList<Memoria> listaUI = new JList<>(model);
    JScrollPane scroll = new JScrollPane(listaUI);

    public class CamposCadastro {
        public JTextField nome;
        public JFormattedTextField quantidade;
        public JTextField listaIngredientes;
        public JFormattedTextField custoProducao;
        public JFormattedTextField custoServico;
        public JFormattedTextField lucro;
        public JFormattedTextField precoTotal;
        public JFormattedTextField validade;
    }


    public void abrirTelaDeCadastro2(JFrame frame, JPanel painelInicial) {
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
        // Nome
        telaCadastro.add(new JLabel("Nome:"));
        campos.nome = new JTextField();
        telaCadastro.add(campos.nome);

// Quantidade (Integer)
        telaCadastro.add(new JLabel("Quantidade:"));
        NumberFormatter fQuant = new NumberFormatter(NumberFormat.getIntegerInstance());
        fQuant.setValueClass(Integer.class);
        fQuant.setAllowsInvalid(false);
        fQuant.setMinimum(0);
        campos.quantidade = new JFormattedTextField(fQuant);
        telaCadastro.add(campos.quantidade);

// Lista de Ingredientes
        telaCadastro.add(new JLabel("Lista de ingredientes:"));
        campos.listaIngredientes = new JTextField();
        telaCadastro.add(campos.listaIngredientes);

// Custo de produção (Double)
        telaCadastro.add(new JLabel("Custo de produção: "));
        NumberFormatter fCustoProd = new NumberFormatter(NumberFormat.getNumberInstance());
        fCustoProd.setValueClass(Double.class);
        fCustoProd.setAllowsInvalid(false);
        fCustoProd.setMinimum(0.0);
        campos.custoProducao = new JFormattedTextField(fCustoProd);
        telaCadastro.add(campos.custoProducao);

// Custo de serviço (Double)
        telaCadastro.add(new JLabel("Custo de serviço: "));
        NumberFormatter fCustoServ = new NumberFormatter(NumberFormat.getNumberInstance());
        fCustoServ.setValueClass(Double.class);
        fCustoServ.setAllowsInvalid(false);
        fCustoServ.setMinimum(0.0);
        campos.custoServico = new JFormattedTextField(fCustoServ);
        telaCadastro.add(campos.custoServico);

// Lucro (Double)
        telaCadastro.add(new JLabel("Lucro: "));
        NumberFormatter lucro = new NumberFormatter(NumberFormat.getNumberInstance());
        lucro.setValueClass(Double.class);
        lucro.setAllowsInvalid(false);
        lucro.setMinimum(0.0);
        campos.lucro = new JFormattedTextField(lucro);
        telaCadastro.add(campos.lucro);

// precoTotal (Double)
        telaCadastro.add(new JLabel("Preço Total: "));
        NumberFormatter precoTotal = new NumberFormatter(NumberFormat.getNumberInstance());
        precoTotal.setValueClass(Double.class);
        precoTotal.setAllowsInvalid(false);
        precoTotal.setMinimum(0.0);
        campos.precoTotal = new JFormattedTextField(precoTotal);
        telaCadastro.add(campos.precoTotal);

// Validade (Date)
        telaCadastro.add(new JLabel("Validade:"));
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        DateFormatter dfValidade = new DateFormatter(formatoData);
        campos.validade = new JFormattedTextField(dfValidade);
        telaCadastro.add(campos.validade);

//----------------------------------------------------------------

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> card.show(container, "principal"));
        telaCadastro.add(btnVoltar, "voltar");


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

        telaCadastro.add(btnSalvar);


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
