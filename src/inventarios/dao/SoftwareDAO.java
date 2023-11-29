package inventarios.dao;

import inventarios.pojo.Software;
import inventarios.pojo.SoftwareListaRespuesta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import inventarios.modelo.ConexionBD;
import inventarios.pojo.ResultadoOperacion;
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
                    software.setTamaño(resultado.getString("software.tamaño"));
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
    
public static ArrayList<Software> consultarSoftware() throws SQLException {
    ArrayList<Software> softwareBD = null;
    Connection conexionBD = ConexionBD.abrirConexionBD();
    
    if (conexionBD != null) {
        try {
            String consulta = "SELECT * FROM software";
            PreparedStatement consultaObtenerTodos = conexionBD.prepareStatement(consulta);
            ResultSet resultadoConsulta = consultaObtenerTodos.executeQuery();

            softwareBD = new ArrayList<>();

            while (resultadoConsulta.next()) {
                
                Software temp = new Software();
                temp.setIdSoftware(resultadoConsulta.getInt("idsoftware"));
                temp.setNombre(resultadoConsulta.getString("nombre"));
                temp.setVersion(resultadoConsulta.getString("version"));
                temp.setEditor(resultadoConsulta.getString("editor"));
                temp.setTamaño(resultadoConsulta.getString("tamaño"));

                softwareBD.add(temp);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            conexionBD.close();
        }
    }
    
    return softwareBD;
}

    public static ResultadoOperacion registrarSoftware(Software softwareActivo) throws SQLException {
        ResultadoOperacion respuesta = new ResultadoOperacion();
        respuesta.setError(true);
        respuesta.setFilasAfectadas(-1);
        Connection conexionBD = ConexionBD.abrirConexionBD();        
        
        if(conexionBD != null){
            try{
                String consulta =  "INSERT INTO software "
                        + "(nombre, version, editor, tamaño) "
                        + "VALUES(?, ?, ?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, softwareActivo.getNombre());
                prepararSentencia.setString(2, softwareActivo.getVersion());
                prepararSentencia.setString(3, softwareActivo.getEditor());
                prepararSentencia.setString(4, softwareActivo.getTamaño());
                
                int numeroFilas = prepararSentencia.executeUpdate();
                if(numeroFilas > 0){
                    respuesta.setError(false);
                    respuesta.setFilasAfectadas(numeroFilas);
                    respuesta.setMensaje("Software registrado con exito");
                }else{
                    respuesta.setMensaje("No se pudo registrar la información del software.");
                }  
            } catch (SQLException e) {
                respuesta.setMensaje(e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            respuesta.setMensaje("Error en la conexion con la base de datos. Intente de nuevo más tarde.");
        }  
        
        return respuesta;
    }

    public static ResultadoOperacion modificarSoftware(int idSoftware, Software softwareNuevo) throws SQLException {
        
        ResultadoOperacion respuesta = new ResultadoOperacion();
        respuesta.setError(true);
        respuesta.setFilasAfectadas(-1);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE software SET nombre = ?, "
                        + "version = ?, editor = ?, tamaño = ? WHERE (idSoftware = ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, softwareNuevo.getNombre());
                prepararSentencia.setString(2, softwareNuevo.getVersion());
                prepararSentencia.setString(3, softwareNuevo.getEditor());
                prepararSentencia.setString(4, softwareNuevo.getTamaño());
                
                prepararSentencia.setInt(5, idSoftware);
            
                int numeroFilas = prepararSentencia.executeUpdate();
                if(numeroFilas > 0){
                    respuesta.setError(false);
                    respuesta.setFilasAfectadas(numeroFilas);
                    respuesta.setMensaje("Software modificado con éxito.");
                }else{
                    respuesta.setMensaje("No se pudo modificar la información del software.");
                }
            }catch(SQLException e){
                respuesta.setMensaje(e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            respuesta.setMensaje("Error en la conexión con la base de datos. Intente de nuevo más tarde.");
        }
        return respuesta;
    }

    public static ResultadoOperacion eliminarSoftware(int idSoftware) throws SQLException {
       ResultadoOperacion respuesta = new ResultadoOperacion();
       respuesta.setError(true);
       respuesta.setFilasAfectadas(-1);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM software WHERE (idsoftware = ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idSoftware);
                
                int numeroFilas = prepararSentencia.executeUpdate();
                if(numeroFilas > 0){
                  respuesta.setError(false);
                    respuesta.setFilasAfectadas(numeroFilas);
                    respuesta.setMensaje("Registro de software eliminado correctamente.");  
                }else{
                    respuesta.setMensaje("No se pudo eliminar la información del periférico.");
                }
            } catch (SQLException e) {
                respuesta.setMensaje(e.getMessage());
            }finally{
                conexionBD.close();
            }
        } else {
            respuesta.setMensaje("Por el momento no hay conexión con la base de datos, Intente más tarde.");
        }
        return respuesta;
    }
    
    public static boolean buscarSoftware(String nombre, String version) throws SQLException{
        Software softwareTemporal = null;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "SELECT * FROM software WHERE (nombre = ? and version = ?)";
                PreparedStatement consultaDB = conexionBD.prepareStatement(consulta);
                consultaDB.setString(1, nombre);
                consultaDB.setString(2, version);
                ResultSet resultadoConsulta = consultaDB.executeQuery();
                softwareTemporal = new Software();                
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
