/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventarios.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import inventarios.pojo.CentroComputo;
import inventarios.modelo.ConexionBD;
import inventarios.util.Utilidades;

/**
 *
 * @author LENOVO
 */
public class CentroComputoDAO {
    
        public static boolean registrarCentroComputo(CentroComputo cc) throws SQLException{
        
        boolean registrado = false;
        
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            
            try {
                String sentencia = "INSERT INTO centrocomputo (clave, numero)"
                        + "VALUES(?, ?) ;";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(sentencia);
                prepararConsulta.setString(1, cc.getClave());
                prepararConsulta.setInt(2, cc.getNumero());
                
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
