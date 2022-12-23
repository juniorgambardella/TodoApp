package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.management.RuntimeErrorException;
import jdk.internal.net.http.common.Utils;
import model.Task;
import util.ConnectionFactory;

public class TaskController {
  
  public void save(Task task) {
    
    String sql = "INSERT INTO tasks (idProject, name, description, completed, notes, deadline, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    Connection connection = null;
    PreparedStatement statement = null;
    
    try {
      connection = ConnectionFactory.getConnection();
      statement = connection.prepareStatement(sql);
      statement.setInt(1, task.getIdProject());
      statement.setString(2, task.getName());
      statement.setString(3, task.getDescription());
      statement.setBoolean(4, task.isIsCompleted());
      statement.setString(5, task.getNotes());
      statement.setDate(6, new Date(task.getDeadline().getTime()));
      statement.setDate(7, new Date(task.getCreatedAt().getTime()));
      statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
      statement.execute();
      
    } catch (Exception ex) {
      throw new RuntimeException("Erro ao salvar tarefa" + ex.getMessage(), ex);
    } finally {
      ConnectionFactory.closeConnection(connection, statement);
    }    
    
  }
  
  public void update(Task task) {
    String sql = "UPDATE tasks SET idProject = ?, description = ?, notes = ?,"
        + " completed = ?, deadline = ? , createdAt = ?, updatedAt = ?, WHERE "
        + " id = ?";
    
    Connection connection = null;
    PreparedStatement statement = null;
    
    try {
      // Estabelecendo a conexão com SB
      connection = ConnectionFactory.getConnection();
      
      //Preparando a query
      statement = connection.prepareStatement(sql);
      
      // Setando os valores do statement
      statement.setInt(1, task.getIdProject());
      statement.setString(2, task.getName());
      statement.setString(3, task.getDescription());
      statement.setString(4, task.getNotes());
      statement.setBoolean(5, task.isIsCompleted());
      statement.setDate(6, new Date(task.getDeadline().getTime()));
      statement.setDate(7, new Date(task.getCreatedAt().getTime()));
      statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
      statement.setInt(9, task.getId());
      statement.execute();    
      
    } catch (Exception ex) {
      throw new RuntimeException("Erro ao atualizar tarefa" + ex.getMessage(), ex);
      
    } finally {
      
    }
    
  }
  
  public void removeById(int taskId) throws SQLException {
    
    String sql = "DELETE FROM tasks WHERE id = ?";
    
    Connection connection = null;
    PreparedStatement statement = null;
    
    try {
      
      // Criando conexão com db
      connection = ConnectionFactory.getConnection();
      
      // Preparando a query
      statement = connection.prepareStatement(sql);
      
      // Setando os valores
      statement.setInt(1, taskId);
      statement.execute();
    } catch (Exception ex) {
        throw new RuntimeException("Erro ao deletar a tarefa");
    } finally {
      ConnectionFactory.closeConnection(connection, statement);
    }
  }
  
  public List<Task> getAll(int idProject) {
    
    String sql = "SELECT + FROM tasks WHERE idProject = ?";
    
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
   
    // Lista de tarefas que será devolvida quando a chamada do método acontecer
    List<Task> tasks = new ArrayList<Task>();
    
    try {
      connection = ConnectionFactory.getConnection();
      statement = connection.prepareStatement(sql);
      statement.setInt(1, idProject);
      resultSet = statement.executeQuery();
      
      while(resultSet.next()){
        
        Task task = new Task();
        task.setId(resultSet.getInt("id"));
        task.setIdProject(resultSet.getInt("idProject"));
        task.setName(resultSet.getString("name"));
        task.setDescription(resultSet.getString("description"));
        task.setNotes(resultSet.getString("notes"));
        task.setIsCompleted(resultSet.getBoolean("completed"));
        task.setDeadline(resultSet.getDate("deadline"));
        task.setCreatedAt(resultSet.getDate("createdAt"));
        task.setUpdatedAt(resultSet.getDate("updatedAt"));
        
        tasks.add(task);
        
      }
    } catch (Exception e) {
    } finally {
    }
    
    return null;
  }
  
}