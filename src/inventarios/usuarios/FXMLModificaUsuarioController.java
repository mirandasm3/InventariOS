
package inventarios.usuarios;

import inventarios.dao.AdministradorDAO;
import inventarios.pojo.Administrador;
import inventarios.pojo.CentroComputo;
import inventarios.pojo.ResultadoOperacion;
import inventarios.util.Utilidades;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author diana
 */
public class FXMLModificaUsuarioController implements Initializable {
    
    Administrador administrador = new Administrador();
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfNumPersonal;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private TextField tfContacto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    

    @FXML
    private void clicBtnModificar(ActionEvent event) {
        Administrador adminNuevo = new Administrador();
        
        String nombre = tfNombre.getText();
        String password = tfPassword.getText();
        String contacto = tfContacto.getText();
        String numPersonal = tfNumPersonal.getText();
        int numeroEntero = Integer.parseInt(numPersonal);
        
        adminNuevo.setNombre(nombre);
        adminNuevo.setPassword(password);
        adminNuevo.setContacto(contacto);
        
        if(nombre.isEmpty() || validarCaracteresNombre(nombre) == false || password.isEmpty() || validarCaracteres(password) == false
                || contacto.isEmpty() || validarCaracteresContacto(contacto) == false){
            
            Utilidades.mostrarAlertaSimple("Campos vacíos", "No puede haber campos vacíos.", Alert.AlertType.WARNING);
            
        }else {
            AdministradorDAO aDao = new AdministradorDAO();
            try {
                ResultadoOperacion resultado = aDao.modificarUsuario(numeroEntero, adminNuevo);
                if (resultado.isError()) {
                    Utilidades.mostrarAlertaSimple("Error", "Error en el registro de actualización.", Alert.AlertType.ERROR);
                } else {
                    Utilidades.mostrarAlertaSimple("Actualización exitosa", "Administrador actualizado con éxito.\n"+" Actualice la tabla para"
                            + "visualizar los cambios.", Alert.AlertType.INFORMATION);
                    Stage stage = (Stage) tfContacto.getScene().getWindow();
                    stage.close();
                }
            } catch (SQLException ex) {
                Utilidades.mostrarAlertaSimple("Error", "Error en la conexión con la base de datos. Intente de nuevo más tarde.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Desea cancelar la operación?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage escenarioRegistro = (Stage) tfContacto.getScene().getWindow();
            escenarioRegistro.close();
        }else{
            alert.close();
        }
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
        
        if(cadena.endsWith("@uv.mx")){
            valido = true;
       }else{
            valido = false;
        }
        
        return valido;
    }

    void set(Administrador adminSeleccionado) {
        this.administrador = adminSeleccionado;
        
        String oldNombre = administrador.getNombre();
        String oldPassword = administrador.getPassword();
        String oldContacto = administrador.getContacto();
        int oldNum = administrador.getNumeroPersonal();
        String str = String.valueOf(oldNum);
        tfNombre.setText(oldNombre);
        tfPassword.setText(oldPassword);
        tfContacto.setText(oldContacto);
        tfNumPersonal.setText(str);
    }

}
