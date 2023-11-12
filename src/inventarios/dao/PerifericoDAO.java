
package inventarios.dao;

import inventarios.modelo.ConexionBD;
import inventarios.pojo.Periferico;
import inventarios.pojo.ResultadoOperacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author diana
 */
public class PerifericoDAO {
    
    public static ResultadoOperacion registrarPeriferico(Periferico periferico) throws SQLException{
        
        ResultadoOperacion respuesta = new ResultadoOperacion();
        respuesta.setError(true);
        respuesta.setFilasAfectadas(-1);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        
        if(conexionBD != null){
           
            try {

                String sentencia = "INSERT INTO periferico (centrocomputo_idcentrocomputo, tipo, identificador, marca, estado)"
                                   + " VALUES (?, ?, ?, ?, ?)";
                
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, periferico.getIdCentroComputo());
                prepararSentencia.setString(2, periferico.getTipo());
                prepararSentencia.setString(3, periferico.getIdentificador());
                prepararSentencia.setString(4, periferico.getMarca());
                prepararSentencia.setString(5, periferico.getEstado());

                int numeroFilas = prepararSentencia.executeUpdate();
                if(numeroFilas > 0){
                    respuesta.setError(false);
                    respuesta.setFilasAfectadas(numeroFilas);
                    respuesta.setMensaje("Periférico registrado con éxito.");
                }else{
                    respuesta.setMensaje("No se pudo registrar la información del periférico.");
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
    
    public static ArrayList<Periferico> consultarPerifericos(int idCentroComputo, String tipo) throws SQLException{

        ArrayList<Periferico> perifericosBD = null;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){

            try {                
                
               String consulta = "SELECT * from periferico WHERE centrocomputo_idcentrocomputo = ? AND tipo = ?";
               PreparedStatement consultaObtenerTodos = conexionBD.prepareStatement(consulta);
               consultaObtenerTodos.setInt(1, idCentroComputo);
               consultaObtenerTodos.setString(2, tipo);
               ResultSet resultadoConsulta = consultaObtenerTodos.executeQuery();
               
               perifericosBD = new ArrayList<>();
               
               while(resultadoConsulta.next()){
                   
                   Periferico temp = new Periferico();
                   temp.setIdPeriferico(resultadoConsulta.getInt("idperiferico"));
                   temp.setIdCentroComputo(resultadoConsulta.getInt("centrocomputo_idcentrocomputo"));
                   temp.setTipo(resultadoConsulta.getString("tipo"));
                   temp.setIdentificador(resultadoConsulta.getString("identificador"));
                   temp.setMarca(resultadoConsulta.getString("marca"));
                   temp.setEstado(resultadoConsulta.getString("estado"));

                   perifericosBD.add(temp);
               }
               
            } catch (SQLException ex) {
                ex.printStackTrace();
                
            } finally{
                conexionBD.close();
            }
            
        }
        return perifericosBD;
    }
    
    public static ResultadoOperacion modificarPeriferico(int idPeriferico, Periferico perifericoNuevo) throws SQLException{
        
        ResultadoOperacion respuesta = new ResultadoOperacion();
        respuesta.setError(true);
        respuesta.setFilasAfectadas(-1);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        
        if(conexionBD != null){
           
            try {

                String sentencia = "UPDATE periferico set identificador = ?, marca = ?, estado = ?, tipo = ? WHERE (idPeriferico = ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, perifericoNuevo.getIdentificador());
                prepararSentencia.setString(2, perifericoNuevo.getMarca());
                prepararSentencia.setString(3, perifericoNuevo.getEstado());
                prepararSentencia.setString(4, perifericoNuevo.getTipo());

                prepararSentencia.setInt(5, idPeriferico);

                int numeroFilas = prepararSentencia.executeUpdate();
                if(numeroFilas > 0){
                    respuesta.setError(false);
                    respuesta.setFilasAfectadas(numeroFilas);
                    respuesta.setMensaje("Periférico modificado con éxito.");
                }else{
                    respuesta.setMensaje("No se pudo modificar la información del periférico.");
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
    
        public static ResultadoOperacion eliminarPeriferico(int idPeriferico) throws SQLException{
        ResultadoOperacion respuesta = new ResultadoOperacion();
        respuesta.setError(true);
        respuesta.setFilasAfectadas(-1);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String sentencia = "DELETE from periferico WHERE(idPeriferico = ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idPeriferico);
                
                int numeroFilas = prepararSentencia.executeUpdate();
                if(numeroFilas > 0){
                    respuesta.setError(false);
                    respuesta.setFilasAfectadas(numeroFilas);
                    respuesta.setMensaje("Registro de periferico eliminado correctamente.");
                }else{
                    respuesta.setMensaje("No se pudo eliminar la información del periférico.");
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
        
        public static boolean buscarPeriferico(String identificador) throws SQLException{
        Periferico perifericoTemporal = null;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT * FROM periferico WHERE (identificador = ?)";
                PreparedStatement consultaDB = conexionBD.prepareStatement(consulta);
                consultaDB.setString(1, identificador);
                ResultSet resultadoConsulta = consultaDB.executeQuery();
                perifericoTemporal = new Periferico();                
                if(resultadoConsulta.next()){
                    return true;
                }else{
                    return false;
                }
            } catch (SQLException s) {
                s.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return false;
    }
    
    
}
