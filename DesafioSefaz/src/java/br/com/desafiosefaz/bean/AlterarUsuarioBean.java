package br.com.desafiosefaz.bean;

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

@ManagedBean(name = "MBAlterarUsuario")
@ViewScoped
public class AlterarUsuarioBean {

    private Usuario usuario;
    private ArrayList<Telefone> listaTel;
    @ManagedProperty(value = "#{MBAutenticacao}")
    private MBAutenticacao MBAutenticacao;
    private String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public MBAutenticacao getMBAutenticacao() {
        return MBAutenticacao;
    }

    public void setMBAutenticacao(MBAutenticacao MBAutenticacao) {
        this.MBAutenticacao = MBAutenticacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ArrayList<Telefone> getListaTel() {
        return listaTel;
    }

    public void setListaTel(ArrayList<Telefone> listaTel) {
        this.listaTel = listaTel;
    }

    //Aqui começa os metodos------------------------------------------------------------------------------------
    private final UsuarioControler ctl = new UsuarioControler();

    public void alterar() {
        if (usuario.getNome().isEmpty()) {
            JSFUtil.mensagemAviso("Preencha o campo nome");
        } else if (usuario.getEmail().isEmpty()) {
            JSFUtil.mensagemAviso("Preencha o campo e-mail");
        } else if (usuario.getSenha().isEmpty()) {
            JSFUtil.mensagemAviso("Preencha o campo senha");
        } else {
            boolean teste = true;
            for (Telefone telefone : listaTel) {
                if (telefone.getNumero().isEmpty()) {
                    teste = false;
                }
            }
            if (teste) {
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
                    JSFUtil.mensagemErro("Erro ao registrar usuário" + ex);
                }
            } else {
                JSFUtil.mensagemAviso("Preencha o campo telefone");
            }
        }
    }

    public void add() {
        Telefone telefone = new Telefone();
        telefone.setTipo(tipo);
        listaTel.add(telefone);
    }

    public void remover() {
        if (listaTel.size() > 1) {
            listaTel.remove(listaTel.size() - 1);
        }
    }

    @PostConstruct
    public void iniciar() {
        usuario = MBAutenticacao.getUsuario();
        listaTel = usuario.getTelefones();
    }
}
