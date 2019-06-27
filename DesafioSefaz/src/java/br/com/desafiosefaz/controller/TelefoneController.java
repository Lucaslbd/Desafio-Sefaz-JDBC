package br.com.desafiosefaz.controller;

import br.com.desafiosefaz.dao.TelefoneDao;
import java.sql.SQLException;


public class TelefoneController {
   
    private final TelefoneDao dao = new TelefoneDao();
    
    public void excluir(int codigo) throws SQLException{
        dao.excluir(codigo);
    }
}
