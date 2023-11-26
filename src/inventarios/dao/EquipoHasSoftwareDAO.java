/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventarios.dao;

import inventarios.modelo.ConexionBD;
import inventarios.pojo.EquipoHasSoftwareRespuesta;
import inventarios.util.Constantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class EquipoHasSoftwareDAO {


    public static EquipoHasSoftwareRespuesta registrarSoftware_Equipo(int idSoftware, int idEquipoSeleccionado) {
       EquipoHasSoftwareRespuesta respuesta = null;
       Connection conexionBD = ConexionBD.abrirConexionBD();
        
        if(conexionBD != null){
            try{
                String consulta =  "INSERT INTO equipo_has_software "
                        + "(software_idsoftware, equipocomputo_idequipocomputo) "
                        + "VALUES(?, ?);";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idSoftware);
                prepararSentencia.setInt(2, idEquipoSeleccionado);
                int filasAfectas = prepararSentencia.executeUpdate();
                if(filasAfectas != 1){
                    respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                }  
            } catch (SQLException e) {
                e.printStackTrace();
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }    
        }  
       return respuesta;
    }

    
    public static int eliminarSoftware_Equipo(int idSoftware, int idEquipoSeleccionado) {
       int respuesta = Constantes.OPERACION_EXITOSA;
       Connection conexionBD = ConexionBD.abrirConexionBD();
        
        if(conexionBD != null){
            try{
                String consulta =  "DELETE FROM equipo_has_software "
                        + "WHERE equipocomputo_idequipocomputo = ? "
                        + "AND software_idsoftware = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idSoftware);
                prepararSentencia.setInt(2, idEquipoSeleccionado);
                int filasAfectas = prepararSentencia.executeUpdate();
                if(filasAfectas != 1){
                    respuesta = Constantes.ERROR_CONSULTA;
                }  
            } catch (SQLException e) {
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }    
        }  
       return respuesta;
    }
    public static int modificarRelacionEquipoSoftware(int idSoftware, int idEquipoSeleccionadoPrevio, int idEquipoSeleccionadoNuevo){
        int respuesta = Constantes.OPERACION_EXITOSA;
        
        Connection conexionBD = ConexionBD.abrirConexionBD();
        
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE equipo_has_software "
                        + "SET equipocomputo_idequipocomputo = ? "
                        + "WHERE software_idsoftware = ? AND equipocomputo_idequipocomputo = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idEquipoSeleccionadoNuevo);
                prepararSentencia.setInt(2, idSoftware);
                prepararSentencia.setInt(3, idEquipoSeleccionadoPrevio);
            
                int filasAfectadas = prepararSentencia.executeUpdate();
                if(filasAfectadas != 1){
                    respuesta = Constantes.ERROR_CONSULTA;
                }
                
                conexionBD.close();
            }catch(SQLException e){
                e.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
}
