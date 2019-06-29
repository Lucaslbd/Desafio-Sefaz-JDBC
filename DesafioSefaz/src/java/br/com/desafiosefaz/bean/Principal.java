package br.com.desafiosefaz.bean;

import br.com.desafiosefaz.controller.UsuarioControler;
import br.com.desafiosefaz.domain.Usuario;
import br.com.desafiosefaz.util.JSFUtil;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "MBUsuario")
@ViewScoped
public class Principal {

    private int acao;
    @ManagedProperty(value = "#{MBAutenticacao}")
    private MBAutenticacao MBAutenticacao;
    private ArrayList<Usuario> lista;
    private ArrayList<Usuario> listaFiltrada;    

    public ArrayList<Usuario> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Usuario> lista) {
        this.lista = lista;
    }

    public ArrayList<Usuario> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(ArrayList<Usuario> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    public MBAutenticacao getMBAutenticacao() {
        return MBAutenticacao;
    }

    public void setMBAutenticacao(MBAutenticacao MBAutenticacao) {
        this.MBAutenticacao = MBAutenticacao;
    }

    public int getAcao() {
        return acao;
    }

    public void setAcao(int acao) {
        this.acao = acao;
    }

    //Inicio da implementação------------------------------------------------------------------------------------
    private final UsuarioControler ctl = new UsuarioControler();   

    public void excluir() {
        try {
            ctl.excluir(MBAutenticacao.getUsuario().getCodigo());
            MBAutenticacao.setEmail("");
            MBAutenticacao.setSenha("");
            MBAutenticacao.setUsuario(null);
            JSFUtil.mensagemSucesso("Conta removida com sucesso");
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (SQLException | IOException ex) {
            JSFUtil.mensagemErro("Erro ao remover conta" + ex);
        }
    }

    @PostConstruct
    public void listar() {
        try {
            lista = ctl.listar();
        } catch (SQLException ex) {
            JSFUtil.mensagemErro("Erro ao remover conta" + ex);
        }
    }

}
