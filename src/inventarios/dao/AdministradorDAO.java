
package inventarios.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import inventarios.pojo.Administrador;
import inventarios.util.Utilidades;
import inventarios.modelo.ConexionBD;
import inventarios.pojo.ResultadoOperacion;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author raudel
 */
public class AdministradorDAO {
    
    public static boolean registrarUsuario(Administrador administrador) throws SQLException{
        
        boolean registrado = false;
        
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            
            try {
                String sentencia = "INSERT INTO administrador (nombre, numeroPersonal, password, contacto)"
                        + "VALUES(?, ?, ?, ?) ;";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(sentencia);
                prepararConsulta.setString(1, administrador.getNombre());
                prepararConsulta.setInt(2, administrador.getNumeroPersonal());
                prepararConsulta.setString( 3, administrador.getPassword());
                prepararConsulta.setString(4, administrador.getContacto());
                
                int numFilas = prepararConsulta.executeUpdate();
                if(numFilas > 0){
                    registrado = true;
                }
            } catch (SQLException e) {
                
                Utilidades.mostrarAlertaSimple("Error", e.getMessage(), Alert.AlertType.ERROR);
            } finally {
                
                conexionBD.close();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error", "Por el momento no hay conexión con la base de datos...", Alert.AlertType.ERROR);
        }
        return registrado;
        
    }
    
     public static ArrayList<Administrador> consultarUsuarios() throws SQLException{

        ArrayList<Administrador> usuariosBD = null;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){

            try {                
                
               String consulta = "SELECT * from administrador";
               PreparedStatement consultaObtenerTodos = conexionBD.prepareStatement(consulta);
               ResultSet resultadoConsulta = consultaObtenerTodos.executeQuery();
               
               usuariosBD = new ArrayList<>();
               
               while(resultadoConsulta.next()){
                   
                   Administrador temp = new Administrador();
                   temp.setNombre(resultadoConsulta.getString("nombre"));
                   temp.setNumeroPersonal(resultadoConsulta.getInt("numeroPersonal"));
                   temp.setPassword(resultadoConsulta.getString("password"));
                   temp.setContacto(resultadoConsulta.getString("contacto"));

                   usuariosBD.add(temp);
               }
               
            } catch (SQLException ex) {
                ex.printStackTrace();
                
            } finally{
                conexionBD.close();
            }
            
        }
        return usuariosBD;
    }
     
    public static ResultadoOperacion eliminarUsuario(int numeroPersonal) throws SQLException{
        ResultadoOperacion respuesta = new ResultadoOperacion();
        respuesta.setError(true);
        respuesta.setFilasAfectadas(-1);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String sentencia = "DELETE from administrador WHERE(numeroPersonal = ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, numeroPersonal);
                
                int numeroFilas = prepararSentencia.executeUpdate();
                if(numeroFilas > 0){
                    respuesta.setError(false);
                    respuesta.setFilasAfectadas(numeroFilas);
                    respuesta.setMensaje("Registro de usuario eliminado correctamente.");
                }else{
                    respuesta.setMensaje("No se pudo eliminar el usuario.");
                }
            } catch (SQLException e) {
                respuesta.setMensaje(e.getMessage());
            } finally{
                conexionBD.close();
            }
        }else{
            respuesta.setMensaje("Por el momento no hay conexión con la base de datos, Intente más tarde.");
        }
        return respuesta;           
    }
    
        public static ResultadoOperacion modificarUsuario(int numPersonal, Administrador adminNuevo) throws SQLException{
        
        ResultadoOperacion respuesta = new ResultadoOperacion();
        respuesta.setError(true);
        respuesta.setFilasAfectadas(-1);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        
        if(conexionBD != null){
           
            try {

                String sentencia = "UPDATE administrador set nombre = ?, password = ?, contacto = ? WHERE (numeroPersonal = ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, adminNuevo.getNombre());
                prepararSentencia.setString(2, adminNuevo.getPassword());
                prepararSentencia.setString(3, adminNuevo.getContacto());
                prepararSentencia.setInt(4, numPersonal);

                int numeroFilas = prepararSentencia.executeUpdate();
                if(numeroFilas > 0){
                    respuesta.setError(false);
                    respuesta.setFilasAfectadas(numeroFilas);
                    respuesta.setMensaje("Administrador modificado con éxito.");
                }else{
                    respuesta.setMensaje("No se pudo modificar la información del administrador.");
                }
                
            } catch (SQLException e) {
                respuesta.setMensaje(e.getMessage());
                
            } finally{
                conexionBD.close();
            }
            
        }else{
            respuesta.setMensaje("Error en la conexión con la base de datos. Intente de nuevo más tarde.");
        }
        return respuesta;           
    }
    
    public static Administrador buscarUsuario(int numeroPersonal) throws SQLException{
        Administrador usuarioTemporal = null;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT * FROM administrador WHERE (numeroPersonal = ?)";
                PreparedStatement consultaDB = conexionBD.prepareStatement(consulta);
                consultaDB.setInt(1, numeroPersonal);
                ResultSet resultadoConsulta = consultaDB.executeQuery();
                usuarioTemporal = new Administrador();                
                if(resultadoConsulta.next()){
                    usuarioTemporal.setNombre(resultadoConsulta.getString("nombre"));
                    usuarioTemporal.setNumeroPersonal(resultadoConsulta.getInt("numeroPersonal"));
                    usuarioTemporal.setPassword(resultadoConsulta.getString("password"));
                    usuarioTemporal.setContacto(resultadoConsulta.getString("contacto"));

                }else{
                    usuarioTemporal.setNumeroPersonal(-1);
                }
            } catch (SQLException s) {
                s.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return usuarioTemporal;
    }
    
}
