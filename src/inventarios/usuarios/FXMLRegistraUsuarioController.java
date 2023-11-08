/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package inventarios.usuarios;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import inventarios.dao.AdministradorDAO;
import inventarios.pojo.Administrador;
import inventarios.util.Utilidades;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class FXMLRegistraUsuarioController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfNumPersonal;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfContacto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBtnRegistrar(ActionEvent event) {
        String nombre = tfNombre.getText();
        String numeroPersonal = tfNumPersonal.getText();
        String password = tfPassword.getText();
        String contacto = tfContacto.getText();
        
        if(nombre.isEmpty() || validarCaracteres(nombre) == false || password.isEmpty() || validarCaracteres(password) == false
                || contacto.isEmpty() || validarCaracteres(password) == false){
            
            Utilidades.mostrarAlertaSimple("Error", "No puede haber campos vacíos o caracteres inválidos", Alert.AlertType.ERROR);
            
        }else if(numeroPersonal.isEmpty() || validarCaracteresEnteros(numeroPersonal) == false){
            
            Utilidades.mostrarAlertaSimple("Error", "No puede haber campos vacíos o caracteres inválidos", Alert.AlertType.ERROR);
           
        }else{
            
             try{
                    int numeroEntero = Integer.parseInt(numeroPersonal);
                    Administrador administrador = new Administrador(nombre, numeroEntero, password, contacto);
                    
                    
                    if(AdministradorDAO.registrarUsuario(administrador) == false){
                        Utilidades.mostrarAlertaSimple("Error", "No se ha podido registrar el Usuario", Alert.AlertType.ERROR);
                    
                    }else{
                        Utilidades.mostrarAlertaSimple("Exito", "Usuario registrado con exito", Alert.AlertType.INFORMATION);
                        cerrarVentana();
                    }
                    
                }catch(SQLException e){
                    Utilidades.mostrarAlertaSimple("Error", "Error en la conexión con la base de datos. Intente de nuevo más tarde", Alert.AlertType.NONE);
                }
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        cerrarVentana();
    }
    
     private boolean validarCaracteres(String cadena){
        
        return cadena.matches("-?([a-zA-Z_0-9]*)?");
    }
    
     private boolean validarCaracteresEnteros(String cadena){
        
        return cadena.matches("\\d+");
    }
     
    private void cerrarVentana(){
       
        Stage escenarioRegistro = (Stage) tfContacto.getScene().getWindow();
        escenarioRegistro.close(); 
   }
    
}
