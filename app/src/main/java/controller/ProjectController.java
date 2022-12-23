package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.text.html.HTMLDocument;
import model.Project;
import util.ConnectionFactory;

public class ProjectController {
  
  public void save(Project project) {
    
    String sql = "INSERT INTO projects (name, description, createdAt, updatedAt)"
        + " VALUES (?, ?, ?, ?)";
    
    Connection connection = null;
    PreparedStatement statement = null;
    
    try {
      
      // Criando conexão com DB
      connection = ConnectionFactory.getConnection();
      
      // Executando a query (PreparedStatment)
      statement = connection.prepareStatement(sql);
      
      statement.setString(1, project.getName());
      statement.setString(2, project.getDescription());
      statement.setDate(3, new Date (project.getCreatedAt().getTime()));
      statement.setDate(4, new Date (project.getUpdatedAt().getTime()));
    } catch (SQLException ex) {
      throw new RuntimeException("Erro ao salvar projeto", ex);
    } finally {
      ConnectionFactory.closeConnection(connection, statement);
    }
    
  }
  
  public void update (Project project) {
    
    String sql = "UPDATE projects SET name = ?, description = ?, "
        + "createdAt = ?, updatedAt = ?, WHERE id = ?";
    
    Connection connection = null;
    PreparedStatement statement = null;
    
    try {
      connection = ConnectionFactory.getConnection();
      statement = connection.prepareStatement(sql);
      
      statement.setString(1, project.getName());
      statement.setString(2, project.getDescription());
      statement.setDate(3, new Date(project.getCreatedAt().getTime()));
      statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
      statement.setInt(5, project.getId());
      
      statement.execute();
      
    } catch (SQLException ex) {
      throw new RuntimeException("Erro ao atualizar projeto", ex);
    } finally {
      ConnectionFactory.closeConnection(connection, statement);
    }
    
  }
  
  public List<Project> getAll() {
    String sql = "SELECT * FROM projects";
    
    List<Project> projects = new ArrayList<>();
    
    return null;
  }
  
  
  
}
