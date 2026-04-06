package modulos;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class Memoria {
    private Integer id;
    private String nome;
    private Integer quantidade;
    private String listaIngredientes;
    private Double custoProducao;
    private Double custoServico;
    private Double lucro;
    private Double precoTotal;
    private Date validade;


    public String exibirLista(){
        return nome + " | Quantidade: " + quantidade + " | Preço: " + precoTotal;
    }
    @Override
    public String toString() {
        return nome + " | Quantidade: " + quantidade + " | Preço: " + precoTotal;
    }
    private ArrayList<Memoria> lista = new ArrayList<>();
    public ArrayList<Memoria> getLista() {
        return lista;
    }
    public void setLista(Memoria objeto) {
        lista.add(objeto);
    }


    public Memoria() {}
    //Construtor
    public Memoria(Integer id, String nome,
                   Integer quantidade, String listaIngredientes,
                   Double custoProducao, Double custoServico,
                   Double lucro, Double precoTotal, Date validade) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.listaIngredientes = listaIngredientes;
        this.custoProducao = custoProducao;
        this.custoServico = custoServico;
        this.lucro = lucro;
        this.precoTotal = precoTotal;
        this.validade = validade;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getListaIngredientes() {
        return listaIngredientes;
    }
    public void setListaIngredientes(String listaIngredientes) {
        this.listaIngredientes = listaIngredientes;
    }

    public Double getCustoProducao() {
        return custoProducao;
    }
    public void setCustoProducao(Double custoProducao) {
        this.custoProducao = custoProducao;
    }

    public Double getCustoServico() {
        return custoServico;
    }
    public void setCustoServico(Double custoServico) {
        this.custoServico = custoServico;
    }

    public Double getLucro() {
        return lucro;
    }
    public void setLucro(Double lucro) {
        this.lucro = lucro;
    }

    public Double getPrecoTotal() {
        return precoTotal;
    }
    public void setPrecoTotal(Double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public Date getValidade() {
        return validade;
    }
    public void setValidade(Date validade) {
        this.validade = validade;
    }


}
