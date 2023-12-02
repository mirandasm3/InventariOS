
package inventarios.dao;

import inventarios.modelo.ConexionBD;
import inventarios.pojo.Equipo;
import inventarios.pojo.EquipoRespuesta;
import inventarios.pojo.ResultadoOperacion;

import inventarios.util.Constantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class EquipoDAO {
    
    public static ResultadoOperacion registrarEquipoComputo (Equipo equipoComputo) throws SQLException{
        
        ResultadoOperacion respuesta = new ResultadoOperacion();
        respuesta.setError(true);
        respuesta.setFilasAfectadas(-1);
        Connection conexionBd = ConexionBD.abrirConexionBD();
        
        if(conexionBd != null){
            
            try {
                
                String sentencia = "INSERT INTO equipocomputo (centrocomputo_idcentrocomputo,"
                        + "identificador, procesador, memoriaRAM, memoriaRAMcantidad, tarjetagrafica, "
                        + "tipoalmacenamiento, espacioalmacenamiento, ubicacionfisica, sistemaoperativo)" 
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
                
                PreparedStatement prepararSentencia = conexionBd.prepareStatement(sentencia);
                prepararSentencia.setInt(1, equipoComputo.getNombreCentroComputo());
                prepararSentencia.setString(2, equipoComputo.getIdentificador());
                prepararSentencia.setString(3, equipoComputo.getProcesador());
                prepararSentencia.setString(4, equipoComputo.getMemoriaRAM());
                prepararSentencia.setInt(5, equipoComputo.getMemoriaRAMCantidad());
                prepararSentencia.setString(6, equipoComputo.getTarjetaGrafica());
                prepararSentencia.setString(7, equipoComputo.getTipoAlmacenamiento());
                prepararSentencia.setString(8, equipoComputo.getEspacioAlmacenamiento());
                prepararSentencia.setString(9, equipoComputo.getUbicacionFisica());
                prepararSentencia.setString(10, equipoComputo.getSistemaOperativo());
                
                int numeroFilas = prepararSentencia.executeUpdate();
                if(numeroFilas > 0){
                    respuesta.setError(false);
                    respuesta.setFilasAfectadas(numeroFilas);
                    respuesta.setMensaje("Equipo de cómputo registrado con éxito.");
                }else{
                    respuesta.setMensaje("No se pudo registrar el equipo de cómputo");
                }                        
                
            } catch (Exception e) {
                respuesta.setMensaje(e.getMessage());
            }finally{
                conexionBd.close();
            }
        }else{
            respuesta.setMensaje("Error en la conexión con la base de datos. Intente de nuevo más tarde.");
        }
        return respuesta;
    }
    
    public static ArrayList<Equipo> consultarEquiposComputo (int idCentroComputo) throws SQLException{
        
        ArrayList<Equipo> equiposComputoBD = null;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                
                String consulta = "SELECT * FROM equipocomputo WHERE centrocomputo_idcentrocomputo = ?";
                PreparedStatement obtenerEquipos = conexionBD.prepareStatement(consulta);
                obtenerEquipos.setInt(1, idCentroComputo);
                ResultSet resultadoConsulta = obtenerEquipos.executeQuery();
                
                equiposComputoBD = new ArrayList<>();
                
                while(resultadoConsulta.next()){
                    Equipo equipoComputoTemp = new Equipo();
                    equipoComputoTemp.setIdEquipoComputo(resultadoConsulta.getInt("idequipocomputo"));
                    equipoComputoTemp.setNombreCentroComputo(resultadoConsulta.getInt("centrocomputo_idcentrocomputo"));
                    equipoComputoTemp.setIdentificador(resultadoConsulta.getString("identificador"));
                    equipoComputoTemp.setProcesador(resultadoConsulta.getString("procesador"));
                    equipoComputoTemp.setMemoriaRAM(resultadoConsulta.getString("memoriaRAM"));
                    equipoComputoTemp.setMemoriaRAMCantidad(resultadoConsulta.getInt("memoriaRAMcantidad"));
                    equipoComputoTemp.setTarjetaGrafica(resultadoConsulta.getString("tarjetagrafica"));
                    equipoComputoTemp.setTipoAlmacenamiento(resultadoConsulta.getString("tipoalmacenamiento"));
                    equipoComputoTemp.setEspacioAlmacenamiento(resultadoConsulta.getString("espacioalmacenamiento"));
                    equipoComputoTemp.setUbicacionFisica(resultadoConsulta.getString("ubicacionfisica"));
                    equipoComputoTemp.setSistemaOperativo(resultadoConsulta.getString("sistemaoperativo"));
                    
                    equiposComputoBD.add(equipoComputoTemp);
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }
        
        return equiposComputoBD;
    }
    
    public static ResultadoOperacion modificarEquipo(int idEquipoComputo, Equipo equipoNuevo) throws SQLException{
        ResultadoOperacion respuesta = new ResultadoOperacion();
        respuesta.setError(true);
        respuesta.setFilasAfectadas(-1);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        
        if(conexionBD != null){
            
            try {
                String sentencia = "UPDATE equipocomputo set identificador = ?, "
                        + "procesador = ?, memoriaRAM = ?, memoriaRAMcantidad = ?, tarjetagrafica = ?, "
                        + "tipoalmacenamiento = ?, espacioalmacenamiento = ?, "
                        + "ubicacionfisica = ?, sistemaoperativo = ? WHERE (idEquipoComputo = ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, equipoNuevo.getIdentificador());
                prepararSentencia.setString(2, equipoNuevo.getProcesador());
                prepararSentencia.setString(3, equipoNuevo.getMemoriaRAM());
                prepararSentencia.setInt(4, equipoNuevo.getMemoriaRAMCantidad());
                prepararSentencia.setString(5, equipoNuevo.getTarjetaGrafica());
                prepararSentencia.setString(6, equipoNuevo.getTipoAlmacenamiento());
                prepararSentencia.setString(7, equipoNuevo.getEspacioAlmacenamiento());
                prepararSentencia.setString(8, equipoNuevo.getUbicacionFisica());
                prepararSentencia.setString(9, equipoNuevo.getSistemaOperativo());
                
                prepararSentencia.setInt(10, idEquipoComputo);
                
                int numeroFilas = prepararSentencia.executeUpdate();
                if(numeroFilas > 0){
                    respuesta.setError(false);
                    respuesta.setFilasAfectadas(numeroFilas);
                    respuesta.setMensaje("Equipo de cómputo modificado con éxito");
                }else{
                    respuesta.setMensaje("No se pudo modificar la información del equipo de cómputo");
                }
                
            } catch (SQLException e) {
                respuesta.setMensaje(e.getMessage());
            } finally {
                conexionBD.close();
            }            
        }else{
            respuesta.setMensaje("Error en la conexión con la base de datos. Intente de nuevo más tarde.");
        }
        return respuesta;
    }
    
    public static ResultadoOperacion eliminarEquipo(int idEquipo) throws SQLException{
        ResultadoOperacion respuesta = new ResultadoOperacion();
        respuesta.setError(true);
        respuesta.setFilasAfectadas(-1);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String sentencia = "DELETE from equipocomputo WHERE (idEquipoComputo = ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idEquipo);
                
                int numeroFilas = prepararSentencia.executeUpdate();
                if(numeroFilas > 0){
                    respuesta.setError(false);
                    respuesta.setFilasAfectadas(numeroFilas);
                    respuesta.setMensaje("Registro de equipo de cómputo eliminado correctamente.");
                }else{
                    respuesta.setMensaje("No se pudo eliminar la información del equipo de cómputo.");
                }
            } catch (Exception e) {
                respuesta.setMensaje(e.getMessage());
            } finally {
               conexionBD.close(); 
            }
        }else{
            respuesta.setMensaje("Error en la conexión con la base de datos. Intente de nuevo más tarde.");
        }
        return respuesta;
    }
    

    public static EquipoRespuesta obtenerInformacionEquipo() {
        EquipoRespuesta respuesta = new EquipoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idequipocomputo, centrocomputo.numero, identificador, "
                        + "procesador, memoriaRAM, memoriaRAMcantidad, tarjetagrafica, tipoalmacenamiento,"
                        + " espacioalmacenamiento, ubicacionFisica, sistemaoperativo "
                        + "FROM equipocomputo "
                        + "INNER JOIN centrocomputo "
                        + "ON equipocomputo.centrocomputo_idcentrocomputo = centrocomputo.idcentrocomputo; "; 
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Equipo> equipoConsulta = new ArrayList();
                while (resultado.next())
                {
                    Equipo equipo = new Equipo();
                    equipo.setIdEquipoComputo(resultado.getInt("idequipocomputo"));
                    equipo.setNombreCentroComputo(resultado.getInt("centrocomputo.nombre"));
                    equipo.setIdentificador(resultado.getString("identificador"));
                    equipo.setProcesador(resultado.getString("procesador"));
                    equipo.setMemoriaRAM(resultado.getString("memoriaRAM"));
                    equipo.setMemoriaRAMCantidad(resultado.getInt("memoriaRAMcantidad"));
                    equipo.setTarjetaGrafica(resultado.getString("tarjetagrafica"));
                    equipo.setTipoAlmacenamiento(resultado.getString("tipoalmacenamiento"));
                    equipo.setEspacioAlmacenamiento(resultado.getString("espacioalmacenamiento"));
                    equipo.setUbicacionFisica(resultado.getString("ubicacionFisica"));
                    equipo.setSistemaOperativo(resultado.getString("sistemaoperativo"));
                    
                    equipoConsulta.add(equipo);
                    
                }
                respuesta.setEquipoLista(equipoConsulta);
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
    
}
