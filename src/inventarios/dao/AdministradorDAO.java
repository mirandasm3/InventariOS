/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 * @author LENOVO
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
    
}
