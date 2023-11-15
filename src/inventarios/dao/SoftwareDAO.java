/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventarios.dao;

import inventarios.pojo.Software;
import inventarios.pojo.SoftwareListaRespuesta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import inventarios.modelo.ConexionBD;
import inventarios.pojo.SoftwareRespuesta;
import inventarios.util.Constantes;


public class SoftwareDAO {
    public static SoftwareListaRespuesta obtenerInformacionSoftware() {
        SoftwareListaRespuesta respuesta = new SoftwareListaRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT software.idsoftware, software.nombre, "
                        + "software.version, software.editor, software.tamaño, "
                        + "software.fechainstalacion, equipocomputo.identificador as equipoidentificador,"
                        + " centrocomputo.numero as numerocc, equipo_has_software.equipocomputo_idequipocomputo "
                        + "FROM software INNER JOIN equipo_has_software  "
                        + "ON software.idsoftware = equipo_has_software.software_idsoftware "
                        + "INNER JOIN equipocomputo "
                        + "ON equipo_has_software.equipocomputo_idequipocomputo = equipocomputo.idequipocomputo "
                        + "INNER JOIN centrocomputo "
                        + "ON equipocomputo.centrocomputo_idcentrocomputo = centrocomputo.idcentrocomputo;"; 
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Software> softwareConsulta = new ArrayList();
                while (resultado.next())
                {
                    Software software = new Software();
                    software.setIdSoftware(resultado.getInt("software.idsoftware"));
                    software.setNombre(resultado.getString("software.nombre"));
                    software.setVersion(resultado.getString("software.version"));
                    software.setEditor(resultado.getString("software.editor"));          
                    software.setTamano(resultado.getString("software.tamaño"));
                    software.setFechaInstalacion(resultado.getString("software.fechainstalacion"));
                    software.setIdEquipo(resultado.getInt("equipocomputo_idequipocomputo"));
                    software.setIdentificadorCC(resultado.getString("numerocc"));
                    software.setIdentificadorEquipo(resultado.getString("equipoidentificador"));
                    
                    softwareConsulta.add(software);
                }
                respuesta.setSoftwareLista(softwareConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }

    public static SoftwareRespuesta registrarSoftware(Software softwareActivo) {
        SoftwareRespuesta respuesta = new SoftwareRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        
        if(conexionBD != null){
            try{
                String consulta =  "INSERT INTO software "
                        + "(nombre, version, editor, tamaño, fechainstalacion) "
                        + "VALUES(?, ?, ?, ?, ?);";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, softwareActivo.getNombre());
                prepararSentencia.setString(2, softwareActivo.getVersion());
                prepararSentencia.setString(3, softwareActivo.getEditor());
                prepararSentencia.setString(4, softwareActivo.getTamano());
                prepararSentencia.setString(5, softwareActivo.getFechaInstalacion());
                int filasAfectas = prepararSentencia.executeUpdate();
                if(filasAfectas != 1){
                    respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                }  
            } catch (SQLException e) {
                e.printStackTrace();
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
            try {
                String consulta = "SELECT LAST_INSERT_ID();";
                
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();           
                if(resultado.next()) {
                    softwareActivo.setIdSoftware(resultado.getInt("LAST_INSERT_ID()"));

                    
                    respuesta.setSoftwareRespuesta(softwareActivo);
                }
                conexionBD.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }  
        
        return respuesta;
    }

    public static int modificarSoftware(Software softwareActivo,int idEquipoSeleccionado) {
        int respuesta = Constantes.OPERACION_EXITOSA;
        
        Connection conexionBD = ConexionBD.abrirConexionBD();
        
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE software SET nombre = ?, "
                        + "version = ?, editor = ?, tamaño = ?, "
                        + "fechainstalacion = ? WHERE idsoftware = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, softwareActivo.getNombre());
                prepararSentencia.setString(2, softwareActivo.getVersion());
                prepararSentencia.setString(3, softwareActivo.getEditor());
                prepararSentencia.setString(4, softwareActivo.getTamano());
                prepararSentencia.setString(5, softwareActivo.getFechaInstalacion());
                prepararSentencia.setInt(6, softwareActivo.getIdSoftware());
            
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

    public static int eliminarSoftware(int idSoftware) {
       int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM software WHERE idsoftware = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idSoftware);
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
            } catch (SQLException e) {
                respuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
}
