
package inventarios;

import inventarios.dao.AdministradorDAO;
import inventarios.pojo.Administrador;
import inventarios.util.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author diana
 */
public class FXMLInicioSesionController implements Initializable {

    @FXML
    private PasswordField pfPassword;
    @FXML
    private TextField tfUsuario;
    @FXML
    private Label lbUsuarioError;
    @FXML
    private Label lbPasswordError;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void btnIniciarSesion(ActionEvent event) {
        String usuario = tfUsuario.getText();
        String password = pfPassword.getText();

        boolean valido = true;
        lbUsuarioError.setText("");
        lbPasswordError.setText("");
        
        if(usuario.isEmpty()){
            valido = false;
            lbUsuarioError.setText("Número de personal es requerido.");
            if(password.isEmpty()){
                valido = false;
                lbPasswordError.setText("Contraseña es requerida.");
            }
        } 
        if(valido){
            int noPersonal =  Integer.parseInt(usuario);
            verificarCredencialesUsuario(noPersonal, password);
        }
    }
    
    private void verificarCredencialesUsuario(int noPersonal, String password){
        try {
            AdministradorDAO aDao = new AdministradorDAO();
            Administrador adminInicio = aDao.buscarUsuarioInicio(noPersonal, password);
            int usuarioBD = adminInicio.getNumeroPersonal();
            String passwordBD = adminInicio.getPassword();
            if(noPersonal == usuarioBD && password.equals(passwordBD)){
               irPantallaPrincipal(adminInicio.getNombre());
            } else{
                Utilidades.mostrarAlertaSimple("Credenciales incorrectas", "El número de personal y/o contraseña"
                        + " son incorrectos, favor de verificar.", Alert.AlertType.WARNING);
            }
        } catch (NullPointerException e){
           Utilidades.mostrarAlertaSimple("Error de conexión", e.getMessage(), Alert.AlertType.ERROR);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void irPantallaPrincipal(String nombre){

        try {
            Utilidades.mostrarAlertaSimple("Bienvenido(a)", "Credenciales correctas, Bienvenido(a) al sistema "+nombre, Alert.AlertType.INFORMATION);
            Parent vista = FXMLLoader.load(getClass().getResource("FXMLMenu.fxml"));
            Scene escenaPrincipal = new Scene(vista);
            Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
            escenarioBase.setTitle("Menú Principal");
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}
    

