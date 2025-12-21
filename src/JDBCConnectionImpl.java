import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCConnectionImpl implements JDBCConnection {
    private Connection conn;
    private String url;
    private String username;
    private String password;
    
    public JDBCConnectionImpl() {
        this.conn = null;
        // Default connection parameters - update these for your MySQL setup
        this.url = "jdbc:mysql://localhost:3306/mazegame_db";
        this.username = "root";
        this.password = "mysql123"; // Update with your MySQL password
    }
    
    public JDBCConnectionImpl(String url, String username, String password) {
        this.conn = null;
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
    public Connection getConn() {
        return conn;
    }
    
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    @Override
    public void startConnection() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Database connection established successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection");
            e.printStackTrace();
        }
    }
    
    @Override
    public void endConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection");
            e.printStackTrace();
        }
    }
    
    @Override
    public void executeUpdate(String sql, Object[] params) {
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            // Set parameters
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error executing update: " + sql);
            e.printStackTrace();
        }
    }
    
    @Override
    public ResultSet executeQuery(String sql, Object[] params) {
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            // Set parameters
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            
            return stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error executing query: " + sql);
            e.printStackTrace();
            return null;
        }
    }
}
