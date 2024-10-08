package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDAO {

    private final Connection conexao;

    public UsuariosDAO(Connection conexao) {
        this.conexao = conexao;
    }

   
    public boolean inserir(Usuarios usuario) {
        String sql = "INSERT INTO usuarios (CPF, nome, email, data_nascimento, endereco) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, usuario.getCpf());
            ps.setString(2, usuario.getNome());
            ps.setString(3, usuario.getEmail());
            ps.setDate(4, usuario.getDataNascimento());
            ps.setString(5, usuario.getEndereco());
            ps.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public List<Usuarios> consultar() {
        List<Usuarios> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuarios usuario = new Usuarios();
                usuario.setCpf(rs.getString("CPF"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setDataNascimento(rs.getDate("data_nascimento"));
                usuario.setEndereco(rs.getString("endereco"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

   
    public boolean atualizar(Usuarios usuario) {
        String sql = "UPDATE usuarios SET nome = ?, email = ?, data_nascimento = ?, endereco = ? WHERE CPF = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setDate(3, usuario.getDataNascimento());
            ps.setString(4, usuario.getEndereco());
            ps.setString(5, usuario.getCpf()); 
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void excluir(String cpf) { 
        String sql = "DELETE FROM usuarios WHERE CPF = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
