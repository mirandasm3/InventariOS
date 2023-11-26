package inventarios.dao;
import inventarios.modelo.ConexionBD;
import inventarios.pojo.Bitacora;
import inventarios.pojo.BitacoraRespuesta;
import inventarios.pojo.ResultadoOperacion;
import inventarios.util.Constantes;

import java.sql.*;
import java.util.ArrayList;

public class BitacoraDAO {

   public static BitacoraRespuesta obtenerBitacora() {
    BitacoraRespuesta respuesta = new BitacoraRespuesta();
    Connection conexion = ConexionBD.abrirConexionBD();
    respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);

    if (conexion != null) {
        try {
            String consulta = "SELECT idBitacora, fecha, descripcion, idEquipoDeComputo FROM bitacora";
            PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
            ResultSet resultado = prepararSentencia.executeQuery();
            ArrayList<Bitacora> bitacoraConsulta = new ArrayList<>();
            while (resultado.next()) {
                Bitacora bitacora = new Bitacora();
                bitacora.setIdBitacora(resultado.getInt("idBitacora"));
                bitacora.setFecha(resultado.getDate("fecha").toLocalDate());
                bitacora.setDescripcion(resultado.getString("descripcion"));

                int idEquipoDeComputo = resultado.getInt("idEquipoDeComputo");
                bitacora.setIdEquipoDeComputo(idEquipoDeComputo);

                bitacoraConsulta.add(bitacora);
            }
            respuesta.setBitacoraLista(bitacoraConsulta);
            conexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
        }
    } else {
        respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
    }

    return respuesta;
}

 public static boolean eliminarBitacora(int idBitacora) throws SQLException{
        Connection conexionBD = ConexionBD.abrirConexionBD();
        boolean respuesta = false;
        if(conexionBD != null){
            try {
                String sentencia = "DELETE from bitacora WHERE(idBitacora = ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idBitacora);
                respuesta = true;
                                
            } catch (SQLException e) {
                throw new SQLException("No se encontró el resultado");
            } finally{
                conexionBD.close();
            }
        }else{
            throw new SQLException("Por el momento no hay conexión con la base de datos, Intente más tarde.");
        }
        respuesta = false;
        return respuesta;
  }
 
  public static boolean registrarBitacora(Bitacora bitacora) {
    boolean registroExitoso = false;
    Connection conexion = ConexionBD.abrirConexionBD();
    
    if (conexion != null) {
        try {
           
            String consultaBitacora = "INSERT INTO bitacora (fecha, descripcion) VALUES (?, ?)";
            PreparedStatement prepararSentenciaBitacora = conexion.prepareStatement(consultaBitacora, Statement.RETURN_GENERATED_KEYS);

           
            Date fechaSql = Date.valueOf(bitacora.getFecha());

            prepararSentenciaBitacora.setDate(1, fechaSql);
            prepararSentenciaBitacora.setString(2, bitacora.getDescripcion());
            prepararSentenciaBitacora.executeUpdate();

           
            ResultSet generatedKeys = prepararSentenciaBitacora.getGeneratedKeys();
            int idBitacoraGenerado = -1;
            if (generatedKeys.next()) {
                idBitacoraGenerado = generatedKeys.getInt(1);
            } else {
                throw new SQLException("No se generó el ID de la Bitacora.");
            }

            
            String consultaActualizarBitacora = "UPDATE bitacora SET idEquipoDeComputo = ? WHERE idBitacora = ?";
            PreparedStatement prepararActualizarBitacora = conexion.prepareStatement(consultaActualizarBitacora);

            prepararActualizarBitacora.setInt(1, bitacora.getIdEquipoDeComputo());
            prepararActualizarBitacora.setInt(2, idBitacoraGenerado);
            prepararActualizarBitacora.executeUpdate();

            conexion.close();
            registroExitoso = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    return registroExitoso;
}

}
