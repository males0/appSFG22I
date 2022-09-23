package db;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import java.sql.*;
import javax.swing.JOptionPane;

public class conectar {   
   public static Connection getConexion(){
       Connection cn = null;
       try {
           //cargar nuestro driver
           
           Class.forName("com.mysql.cj.jdbc.Driver");
           cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbsfg?autoReconnect=true&useSSL=false","root","1005166200");
           System.out.println("conexion establecida");
       } catch (ClassNotFoundException | SQLException e) {
           System.out.println("Error de conexion "+e);
           JOptionPane.showMessageDialog(null, "Error de conexion "+e);
       }
       return cn;
   }
   
   public static Connection getConexionMySQL57(){
       Connection cn = null;
       try {
           //cargar nuestro driver
           Class.forName("com.mysql.jdbc.Driver");
           cn = DriverManager.getConnection("jdbc:mysql://localhost/dbsfg21iia?autoReconnect=true&useSSL=false","root","");
           System.out.println("conexion establecida");
       } catch (ClassNotFoundException | SQLException e) {
           System.out.println("Error de conexion "+e);
           JOptionPane.showMessageDialog(null, "Error de conexion "+e);
       }
       return cn;
   }

    public static com.mysql.cj.jdbc.CallableStatement getConexion(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
