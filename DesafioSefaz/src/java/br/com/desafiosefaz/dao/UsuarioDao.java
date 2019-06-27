package br.com.desafiosefaz.dao;

import br.com.desafiosefaz.conexao.ConexaoBD;
import br.com.desafiosefaz.domain.Telefone;
import br.com.desafiosefaz.domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDao {

    private final TelefoneDao telefoneDao = new TelefoneDao();

    public void Salvar(Usuario usuario) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into usuario(nome_usuario,email_usuario,senha_usuario) values (?,?,?)");
        try (Connection conexao = ConexaoBD.conectar()) {
            PreparedStatement comando = conexao.prepareStatement(sql.toString());
            comando.setString(1, usuario.getNome());
            comando.setString(2, usuario.getEmail());
            comando.setString(3, usuario.getSenha());
            comando.executeUpdate();
            usuario.setCodigo(ultimoRegistro());
            conexao.close();
            for (Telefone telefone : usuario.getTelefones()) {
                telefone.setUsuario(usuario);
                telefoneDao.Salvar(telefone);
            }
        }
    }

    public void Alterar(Usuario usuario) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("update usuario set nome_usuario=?,email_usuario=?,senha_usuario=? where codigo_usuario=?");
        try (Connection conexao = ConexaoBD.conectar()) {
            PreparedStatement comando = conexao.prepareStatement(sql.toString());
            comando.setString(1, usuario.getNome());
            comando.setString(2, usuario.getEmail());
            comando.setString(3, usuario.getSenha());
            comando.setInt(4, usuario.getCodigo());
            comando.executeUpdate();
            conexao.close();
            for (Telefone telefone : usuario.getTelefones()) {
                if (telefone.getCodigo() < 1) {
                    telefone.setUsuario(usuario);
                    telefoneDao.Salvar(telefone);
                }
            }
        }
    }

    public void excluir(int codigo) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from usuario ");
        sql.append("where codigo_usuario= ? ");
        try (Connection conexao = ConexaoBD.conectar()) {
            PreparedStatement comando = conexao.prepareStatement(sql.toString());
            comando.setInt(1, codigo);
            comando.executeUpdate();
            conexao.close();
        }
    }

    public Usuario autenticar(String email, String senha) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("select * ");
        sql.append("from usuario ");
        sql.append("where email_usuario=? and senha_usuario=?");
        try (Connection conexao = ConexaoBD.conectar()) {
            PreparedStatement comando = conexao.prepareStatement(sql.toString());
            comando.setString(1, email);
            comando.setString(2, senha);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                Usuario usuario = new Usuario();
                usuario.setCodigo(resultado.getInt("codigo_usuario"));
                usuario.setNome(resultado.getString("nome_usuario"));
                usuario.setEmail(resultado.getString("email_usuario"));
                usuario.setSenha(resultado.getString("senha_usuario"));
                usuario.setTelefones(telefoneDao.Listar(resultado.getInt("codigo_usuario")));
                return usuario;
            }
            conexao.close();
        }
        return null;
    }

    public int ultimoRegistro() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from usuario order by codigo_usuario DESC LIMIT 1");
        try (Connection conexao = ConexaoBD.conectar()) {
            PreparedStatement comando = conexao.prepareStatement(sql.toString());
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                return resultado.getInt("codigo_usuario");
            }
            conexao.close();
        }
        return 0;
    }

    public ArrayList<Usuario> Listar() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from usuario order by nome_usuario");
        ArrayList<Usuario> lista;
        try (Connection conexao = ConexaoBD.conectar()) {
            PreparedStatement comando = conexao.prepareStatement(sql.toString());
            ResultSet resultado = comando.executeQuery();
            lista = new ArrayList();
            while (resultado.next()) {
                Usuario usuario = new Usuario();
                usuario.setCodigo(resultado.getInt("codigo_usuario"));
                usuario.setNome(resultado.getString("nome_usuario"));
                usuario.setEmail(resultado.getString("email_usuario"));
                usuario.setSenha(resultado.getString("senha_usuario"));
                usuario.setTelefones(telefoneDao.Listar(resultado.getInt("codigo_usuario")));
                lista.add(usuario);
            }
            conexao.close();
        }
        return lista;
    }
}
