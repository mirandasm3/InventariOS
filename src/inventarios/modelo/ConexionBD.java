
package inventarios.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author raudel
 */
public class ConexionBD {
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String bd = "inventarios";
    private static String ip = "127.0.0.1";
    private static String puerto = "3306";
    private static String urlConexion = "jdbc:mysql://"+ip+":"+puerto+"/"+bd+"?allowPublicKeyRetrieval=true&useSSL=false";
    private static String usuario = "root";
    private static String password = "2282879736Lana";
    public static Connection abrirConexionBD(){
        Connection conexionBD = null;
        try {//a
            Class.forName(driver);
            conexionBD = DriverManager.getConnection(urlConexion, usuario, password);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
            ex.getMessage();
        }
        return conexionBD;
        }
}