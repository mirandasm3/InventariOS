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
import java.util.Optional;
import javafx.scene.control.ButtonType;

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
        
        if(nombre.isEmpty() || validarCaracteresNombre(nombre) == false || password.isEmpty() || validarCaracteres(password) == false
                || contacto.isEmpty() || validarCaracteresContacto(contacto) == false || numeroPersonal.isEmpty() || validarCaracteresEnteros(numeroPersonal) == false){
            
            Utilidades.mostrarAlertaSimple("Error", "No puede haber campos vacíos o caracteres inválidos", Alert.AlertType.ERROR);
            
        }else{
            
             try{
                 int numeroEntero = Integer.parseInt(numeroPersonal);
                 Administrador usuarioBuscar = new AdministradorDAO().buscarUsuario(numeroEntero);
                 
                 if(usuarioBuscar.getNumeroPersonal() < 0){
                 
                        
                        Administrador administrador = new Administrador(nombre, numeroEntero, password, contacto);


                        if(AdministradorDAO.registrarUsuario(administrador) == false){
                            Utilidades.mostrarAlertaSimple("Error", "No se ha podido registrar el Usuario", Alert.AlertType.ERROR);

                        }else{
                            Utilidades.mostrarAlertaSimple("Exito", "Usuario registrado con exito", Alert.AlertType.INFORMATION);
                            cerrarVentana();
                        }
                 }else{
                      Utilidades.mostrarAlertaSimple("Error", "El Numero de empleado que intenta registrar, ya se encuentra en la base de datos", Alert.AlertType.ERROR);                   
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
     
     private boolean validarCaracteresNombre(String cadena){
        return cadena.matches("^[a-zA-Z ]+$");
    }
       
     private boolean validarCaracteresEnteros(String cadena){
        
        return cadena.matches("\\d+");
    }
     
     private boolean validarCaracteresContacto(String cadena){
        boolean valido;
        
        if(cadena.endsWith("@uv.com.mx")){
            valido = true;
       }else{
            valido = false;
        }
        
        return valido;
     }
     
    private void cerrarVentana(){
               
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("¿Desea cancelar el registro?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Stage escenarioRegistro = (Stage) tfContacto.getScene().getWindow();
                escenarioRegistro.close();
            }else{
                
            }
 
   }
    
}
