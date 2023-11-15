package inventarios.dao;
import inventarios.modelo.ConexionBD;
import inventarios.pojo.Bitacora;
import inventarios.pojo.BitacoraRespuesta;
import inventarios.util.Constantes;

import java.sql.*;
import java.util.ArrayList;

public class BitacoraDAO {

    public static BitacoraRespuesta obtenerBitacora() {
        BitacoraRespuesta respuesta = new BitacoraRespuesta();
        Connection conexion = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);

        if (conexion != null){
            try{
                String consulta = "SELECT * FROM bitacora";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Bitacora> bitacoraConsulta = new ArrayList<>();
                while (resultado.next()){
                    Bitacora bitacora = new Bitacora();
                    bitacora.setFecha(resultado.getDate("fecha").toLocalDate());
                    bitacora.setDescripcion(resultado.getString("descripcion"));
                    bitacoraConsulta.add(bitacora);
                }
                respuesta.setBitacoraLista(bitacoraConsulta);
                conexion.close();
            }catch (SQLException ex){
                ex.printStackTrace();
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }

        return respuesta;
    }

    public static boolean registrarBitacora(Bitacora bitacora) {
        boolean registroExitoso = false;
        Connection conexion = ConexionBD.abrirConexionBD();
        if (conexion != null) {
            try {
                String consulta = "INSERT INTO bitacora (fecha, descripcion) VALUES (?, ?)";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);

                // Convertir java.time.LocalDate a java.sql.Date
                Date fechaSql = Date.valueOf(bitacora.getFecha());

                prepararSentencia.setDate(1, fechaSql);
                prepararSentencia.setString(2, bitacora.getDescripcion());
                prepararSentencia.executeUpdate();
                conexion.close();
                registroExitoso = true;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return registroExitoso;
    }
}
