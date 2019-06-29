package br.com.desafiosefaz.controller;

import br.com.desafiosefaz.dao.TelefoneDao;
import br.com.desafiosefaz.dao.UsuarioDao;
import br.com.desafiosefaz.domain.Telefone;
import br.com.desafiosefaz.domain.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioControler {

    private final UsuarioDao dao = new UsuarioDao();
    private final TelefoneDao telefoneDao = new TelefoneDao();

    public void salvar(Usuario usuario) throws SQLException {
        int codigo = dao.Salvar(usuario);
        usuario.setCodigo(codigo);
        for (Telefone telefone : usuario.getTelefones()) {
            telefone.setUsuario(usuario);
            telefoneDao.Salvar(telefone);
        }
    }

    public void alterar(Usuario usuario) throws SQLException {
        dao.Alterar(usuario);
        telefoneDao.excluir(usuario.getCodigo());
        for (Telefone telefone : usuario.getTelefones()) {
            telefone.setUsuario(usuario);
            telefoneDao.Salvar(telefone);
        }
    }

    public void excluir(int codigo) throws SQLException {
        dao.excluir(codigo);
    }

    public Usuario autenticar(String email, String senha) throws SQLException {
        Usuario usuario = dao.autenticar(email, senha);
        usuario.setTelefones(telefoneDao.Listar(usuario.getCodigo()));
        return usuario;
    }

    public ArrayList<Usuario> listar() throws SQLException {
        ArrayList<Usuario> lista = dao.Listar();
        for (Usuario u : lista) {
            u.setTelefones(telefoneDao.Listar(u.getCodigo()));
        }
        return lista;
    }
}
