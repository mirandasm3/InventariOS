
package inventarios.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import inventarios.pojo.CentroComputo;
import inventarios.modelo.ConexionBD;
import inventarios.pojo.ResultadoOperacion;
import inventarios.util.Utilidades;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author raudel
 */
public class CentroComputoDAO {
    
        public static boolean registrarCentroComputo(CentroComputo cc) throws SQLException{
        
        boolean registrado = false;
        
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            
            try {
                String sentencia = "INSERT INTO centrocomputo (clave, numero)"
                        + "VALUES(?, ?) ;";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(sentencia);
                prepararConsulta.setString(1, cc.getClave());
                prepararConsulta.setInt(2, cc.getNumero());
                
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
        
    public static ArrayList<CentroComputo> consultarCCs() throws SQLException{
        
        ArrayList<CentroComputo> ccsBD = null;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){

            try {                
                
               String consulta = "SELECT * from centrocomputo";
               PreparedStatement consultaObtenerTodos = conexionBD.prepareStatement(consulta);
               ResultSet resultadoConsulta = consultaObtenerTodos.executeQuery();
               
               ccsBD = new ArrayList<>();
               
               while(resultadoConsulta.next()){
                   
                   CentroComputo temp = new CentroComputo();
                   temp.setClave(resultadoConsulta.getString("clave"));
                   temp.setNumero(resultadoConsulta.getInt("numero"));;
                   temp.setIdCC(resultadoConsulta.getInt("idcentrocomputo"));

                   ccsBD.add(temp);
               }
               
            } catch (SQLException ex) {
                ex.printStackTrace();
                
            } finally{
                conexionBD.close();
            }
            
        }
        return ccsBD;
    }
    
      public static ResultadoOperacion eliminarCC(String clave) throws SQLException{
        ResultadoOperacion respuesta = new ResultadoOperacion();
        respuesta.setError(true);
        respuesta.setFilasAfectadas(-1);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String sentencia = "DELETE from centrocomputo WHERE(clave = ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, clave);
                
                int numeroFilas = prepararSentencia.executeUpdate();
                if(numeroFilas > 0){
                    respuesta.setError(false);
                    respuesta.setFilasAfectadas(numeroFilas);
                    respuesta.setMensaje("Registro de centro de cómputo eliminado correctamente.");
                }else{
                    respuesta.setMensaje("No se pudo eliminar el centro de cómputo.");
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
      
       public static CentroComputo buscarCC(String clave) throws SQLException{
        CentroComputo ccTemporal = null;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT * FROM centrocomputo WHERE (clave = ?)";
                PreparedStatement consultaDB = conexionBD.prepareStatement(consulta);
                consultaDB.setString(1, clave);
                ResultSet resultadoConsulta = consultaDB.executeQuery();
                ccTemporal = new CentroComputo();                
                if(resultadoConsulta.next()){
                    ccTemporal.setClave(resultadoConsulta.getString("clave"));
                    ccTemporal.setNumero(resultadoConsulta.getInt("numero"));
                    ccTemporal.setIdCC(resultadoConsulta.getInt("idcentrocomputo"));

                }else{
                    ccTemporal.setNumero(-1);
                }
            } catch (SQLException s) {
                s.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return ccTemporal;
    }
    
}
