package br.com.slv.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Interface de definição de métodos
 * para construção de DAOs na plataforma
 * JavaStandard usando JDBC Type 4
 * @author Jairo de Almeida (jairodealmeida@gmail.com)
 * @version 1.0
 */
public interface DatabaseDAO {
   
    /**
     * Metodo que definie a inicialização do DAO
     * @param driver classe do driver JDBC
     * @param url - url de configuracao
     * @param user - nome do usuario do banco de dados
     * @param pass - senha do usuario de banco de dados
     * @since 1.0
     */
    public void initialize(String driver, String url, String user, String pass) throws Exception;
    
    /**
     * Metodo que define a conexão 
     * @param commit - true = autoCommit, false = necessita de commit
     * @throws ClassNotFoundException 
     * @throws Exception 
     * @since 1.0
     */    
    public boolean connect(boolean commit) throws SQLException, ClassNotFoundException, Exception;
    
    /**
     * Metodo que define o fechamento de conexão
     * @since 1.0
     */
    public void close() throws SQLException;

    /**
     * Método que define a efetivação de uma transação
     * @since 1.0
     */
    public void commit() throws SQLException;

    /**
     * Método que define o cancelamento de uma transação
     * @since 1.0
     */
    public void rollback() throws SQLException;
    
    public PreparedStatement prepareStatement(String statement);
 
}
