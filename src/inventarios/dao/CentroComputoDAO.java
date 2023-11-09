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
import java.sql.ResultSet;
import java.util.ArrayList;

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
        
    public static ArrayList<CentroComputo> consultarCCs() throws SQLException{
        
        ArrayList<CentroComputo> ccsBD = null;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){

            try {                
                
               String consulta = "SELECT * from centrocomputo";
               PreparedStatement consultaObtenerTodos = conexionBD.prepareStatement(consulta);
               ResultSet resultadoConsulta = consultaObtenerTodos.executeQuery();
               
               ccsBD = new ArrayList<>();
               
               while(resultadoConsulta.next()){
                   
                   CentroComputo temp = new CentroComputo();
                   temp.setClave(resultadoConsulta.getString("clave"));
                   temp.setNumero(resultadoConsulta.getInt("numero"));;

                   ccsBD.add(temp);
               }
               
            } catch (SQLException ex) {
                ex.printStackTrace();
                
            } finally{
                conexionBD.close();
            }
            
        }
        return ccsBD;
    }
    
}
