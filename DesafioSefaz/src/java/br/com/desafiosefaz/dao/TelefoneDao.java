package br.com.desafiosefaz.dao;

import br.com.desafiosefaz.conexao.ConexaoBD;
import br.com.desafiosefaz.domain.Telefone;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class TelefoneDao {
    
    public void Salvar(Telefone telefone) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into telefone(ddd,numero,tipo,usuario) values (?,?,?,?)");
        try (Connection conexao = ConexaoBD.conectar()) {
            PreparedStatement comando = conexao.prepareStatement(sql.toString());
            comando.setInt(1, telefone.getDdd());
            comando.setString(2, telefone.getNumero());
            comando.setString(3, telefone.getTipo());
            comando.setInt(4, telefone.getUsuario().getCodigo());
            comando.executeUpdate();
            conexao.close();
        }
    }
    
    public void excluir(int codigo) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from telefone ");
        sql.append("where usuario= ? ");
        try (Connection conexao = ConexaoBD.conectar()) {
            PreparedStatement comando = conexao.prepareStatement(sql.toString());
            comando.setInt(1, codigo);
            comando.executeUpdate();
            conexao.close();
        }
    }
    
    public ArrayList<Telefone> Listar(int codigo) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from telefone where usuario = ").append(codigo).append("");
        ArrayList<Telefone> lista;
        try (Connection conexao = ConexaoBD.conectar()) {
            PreparedStatement comando = conexao.prepareStatement(sql.toString());
            ResultSet resultado = comando.executeQuery();
            lista = new ArrayList();
            while (resultado.next()) {
                Telefone telefone = new Telefone();
                telefone.setCodigo(resultado.getInt("codigo_telefone"));
                telefone.setDdd(resultado.getInt("ddd"));
                telefone.setNumero(resultado.getString("numero"));
                telefone.setTipo(resultado.getString("tipo"));
                lista.add(telefone);
            }
            conexao.close();
        }
        return lista;
    }
}
