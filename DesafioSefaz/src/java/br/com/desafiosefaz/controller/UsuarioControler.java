package br.com.desafiosefaz.controller;

import br.com.desafiosefaz.dao.UsuarioDao;
import br.com.desafiosefaz.domain.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioControler {

    private final UsuarioDao dao = new UsuarioDao();

    public void salvar(Usuario usuario) throws SQLException {
        dao.Salvar(usuario);
    }
    
     public void alterar(Usuario usuario) throws SQLException{
        dao.Alterar(usuario);
    }
     
     public void excluir(int codigo)throws SQLException{
         dao.excluir(codigo);
     }
    
    public Usuario autenticar(String email, String senha)throws SQLException{
       return dao.autenticar(email, senha);
    }  
    
    public ArrayList<Usuario>listar()throws SQLException{
        return dao.Listar();
    }
}
