/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventarios.dao;

import inventarios.modelo.ConexionBD;
import inventarios.pojo.Equipo;
import inventarios.pojo.EquipoRespuesta;

import inventarios.util.Constantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class EquipoDAO {

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
                    equipo.setNombreCentroComputo(resultado.getInt("centrocomputo.numero"));
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
