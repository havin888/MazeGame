import java.sql.Connection;
import java.sql.PreparedStatement;

public interface JDBCConnection {
    void startConnection();
    void endConnection();
    void executeUpdate(String sql, Object[] params);
    void executeQuery(String sql, Object[] params);
}
