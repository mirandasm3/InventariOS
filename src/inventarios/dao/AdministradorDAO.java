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
    
}
