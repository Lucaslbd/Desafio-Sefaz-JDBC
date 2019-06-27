package br.com.desafiosefaz.bean;

import br.com.desafiosefaz.controller.TelefoneController;
import br.com.desafiosefaz.controller.UsuarioControler;
import br.com.desafiosefaz.domain.Telefone;
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
public class UsuarioBean {

    private Usuario usuario;
    private int acao;
    @ManagedProperty(value = "#{MBAutenticacao}")
    private MBAutenticacao MBAutenticacao;
    private ArrayList<Usuario> lista;
    private ArrayList<Usuario> listaFiltrada;
    private ArrayList<Telefone> listaTel = new ArrayList<>();
    private Telefone telefone = new Telefone();;

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public ArrayList<Telefone> getListaTel() {
        return listaTel;
    }

    public void setListaTel(ArrayList<Telefone> listaTel) {
        this.listaTel = listaTel;
    }

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    //Inicio da implementação------------------------------------------------------------------------------------
    private final UsuarioControler ctl = new UsuarioControler();
    private final TelefoneController ctlTel = new TelefoneController();

    public void salvar() {
        try {
            if (listaTel.size() > 0) {
                usuario.setTelefones(listaTel);
                ctl.salvar(usuario);
                JSFUtil.mensagemSucesso("Usuário registrado com sucesso");
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } else {
                JSFUtil.mensagemAviso("Adicione telefones a lista");
            }
        } catch (SQLException | IOException ex) {
            JSFUtil.mensagemErro("Erro ao registrar usuário" + ex);
        }
    }

    public void alterar() {
        try {
            if (listaTel.size() > 0) {
                usuario.setTelefones(listaTel);
                ctl.alterar(usuario);
                JSFUtil.mensagemSucesso("Usuário alterado com sucesso");
                FacesContext.getCurrentInstance().getExternalContext().redirect("Principal.xhtml");
            } else {
                JSFUtil.mensagemAviso("Adicione telefones a lista");
            }
        } catch (SQLException | IOException ex) {
            JSFUtil.mensagemErro("Erro ao alterar usuário" + ex);
        }
    }

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

    public void carregarCadastro() {        
        if (acao == 1) {
            usuario = new Usuario();
        } else {
            usuario = MBAutenticacao.getUsuario();
            listaTel = usuario.getTelefones();
        }
    }

    public void removerTel(int index, int codigo) {
        listaTel.remove(index);
        if (codigo > 0) {
            try {
                ctlTel.excluir(codigo);
            } catch (SQLException ex) {
                JSFUtil.mensagemErro("Erro ao remover telefone" + ex);
            }
        }
    }

    public void addTel() {
        if (telefone.getNumero().equals("") || telefone.getNumero().length() < 8 || telefone.getDdd() == 0) {
            JSFUtil.mensagemAviso("Preencha o telefone");
        } else {
            if (telefone.getNumero().length() == 8) {
                telefone.setTipo("Residencial");
            } else {
                telefone.setTipo("Celular");
            }
            listaTel.add(telefone);
            telefone = new Telefone();
        }
    }
}
