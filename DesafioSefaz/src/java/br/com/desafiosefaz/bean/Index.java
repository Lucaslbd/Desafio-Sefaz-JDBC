package br.com.desafiosefaz.bean;

import br.com.desafiosefaz.controller.UsuarioControler;
import br.com.desafiosefaz.domain.Telefone;
import br.com.desafiosefaz.domain.Usuario;
import br.com.desafiosefaz.util.JSFUtil;
import java.io.IOException;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "indexBean")
@ViewScoped
public class Index {

    private Usuario usuario = new Usuario();
    private String tipo;

    public Index() {
        Telefone telefone = new Telefone();
        telefone.setTipo("Celular");
        usuario.getTelefones().add(telefone);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    //Metodos-------------------------------------------------------------------------------------------------
    private final UsuarioControler ctl = new UsuarioControler();

    public void salvar() {
        if (usuario.getNome().isEmpty()) {
            JSFUtil.mensagemAviso("Preencha o campo nome");
        } else if (usuario.getEmail().isEmpty()) {
            JSFUtil.mensagemAviso("Preencha o campo e-mail");
        } else if (usuario.getSenha().isEmpty()) {
            JSFUtil.mensagemAviso("Preencha o campo senha");
        } else {
            boolean teste = true;            
            for (Telefone telefone : usuario.getTelefones()) {
                if (telefone.getNumero().isEmpty()) {
                    teste = false;                    
                }
            }
            if (teste) {
                try {
                    ctl.salvar(usuario);
                    JSFUtil.mensagemSucesso("Usuário registrado com sucesso");
                    FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
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
        usuario.getTelefones().add(telefone);
    }

    public void remover() {
        if (usuario.getTelefones().size() > 1) {
            usuario.getTelefones().remove(usuario.getTelefones().size() - 1);
        }
    }
}
