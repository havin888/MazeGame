import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface JDBCConnection {
    void startConnection();
    void endConnection();
    void executeUpdate(String sql, Object[] params);
    ResultSet executeQuery(String sql, Object[] params);
}
