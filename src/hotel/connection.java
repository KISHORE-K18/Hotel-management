package hotel;
import java.sql.*;
public class connection {
    static Connection con;
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/hotel";
        String user = "root";
        String password = "";
        try {
            con = DriverManager.getConnection(url, user, password);
            return con;
        }
        catch (SQLException e) {
            System.out.println("connection error");
        }
        return null;
    }
}
