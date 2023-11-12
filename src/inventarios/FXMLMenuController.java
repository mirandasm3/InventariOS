
package inventarios;

import inventarios.util.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author diana
 */
public class FXMLMenuController implements Initializable {

    @FXML
    private Text txtTitle;
    @FXML
    private MenuButton btnAdmin;
    @FXML
    private TabPane tabpane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        Stage stage = (Stage) txtTitle.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clicBtnConsultarPerifericos(ActionEvent event) {
        try{
            Parent vista = FXMLLoader.load(getClass().getResource("perifericos/FXMLConsultaPerifericos.fxml"));
            Scene escenaAdmin = new Scene(vista);
            Stage escenarioNuevo = new Stage();
            escenarioNuevo.setTitle("Consulta de periféricos");
            escenarioNuevo.setScene(escenaAdmin);
            escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
            escenarioNuevo.showAndWait();
        }catch(IOException io){
            Utilidades.mostrarAlertaSimple("Error", "Error al cargar.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicBtnConsultarCC(ActionEvent event) {
        try{
            Parent vista = FXMLLoader.load(getClass().getResource("centroscomputo/FXMLConsultaCC.fxml"));
            Scene escenaAdmin = new Scene(vista);
            Stage escenarioNuevo = new Stage();
            escenarioNuevo.setTitle("Consulta de centros de cómputo");
            escenarioNuevo.setScene(escenaAdmin);
            escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
            escenarioNuevo.showAndWait();
        }catch(IOException io){
            Utilidades.mostrarAlertaSimple("Error", "Error al cargar.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicBtnConsultarUsuarios(ActionEvent event) {
       try{
            Parent vista = FXMLLoader.load(getClass().getResource("usuarios/FXMLConsultaUsuarios.fxml"));
            Scene escenaAdmin = new Scene(vista);
            Stage escenarioNuevo = new Stage();
            escenarioNuevo.setTitle("Consulta de usurios");
            escenarioNuevo.setScene(escenaAdmin);
            escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
            escenarioNuevo.showAndWait();
        }catch(IOException io){
            Utilidades.mostrarAlertaSimple("Error", "Error al cargar.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicBtnConsultarSoftware(ActionEvent event) {
        
    }

    @FXML
    private void clicBtnConsultarEquipo(ActionEvent event) {
        
    }

    @FXML
    private void clicBtnRegistrarEquipo(ActionEvent event) {
        
    }

    @FXML
    private void clicBtnRegistrarSoftware(ActionEvent event) {
        
    }

    @FXML
    private void clicBtnRegistrarPerifericos(ActionEvent event) {
        try{
            Parent vista = FXMLLoader.load(getClass().getResource("perifericos/FXMLRegistroPerifericos.fxml"));
            Scene escenaAdmin = new Scene(vista);
            Stage escenarioNuevo = new Stage();
            escenarioNuevo.setTitle("Registro de periféricos");
            escenarioNuevo.setScene(escenaAdmin);
            escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
            escenarioNuevo.showAndWait();
        }catch(IOException io){
            Utilidades.mostrarAlertaSimple("Error", "Error al cargar.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicBtnRegistrarCC(ActionEvent event) {
        try{
            Parent vista = FXMLLoader.load(getClass().getResource("centroscomputo/FXMLRegistraCC.fxml"));
            Scene escenaAdmin = new Scene(vista);
            Stage escenarioNuevo = new Stage();
            escenarioNuevo.setTitle("Registrar CC");
            escenarioNuevo.setScene(escenaAdmin);
            escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
            escenarioNuevo.showAndWait();
        }catch(IOException io){
            Utilidades.mostrarAlertaSimple("Error", "Error al cargar.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicBtnRegistrarUsuario(ActionEvent event) {
        try{
            Parent vista = FXMLLoader.load(getClass().getResource("usuarios/FXMLRegistraUsuario.fxml"));
            Scene escenaAdmin = new Scene(vista);
            Stage escenarioNuevo = new Stage();
            escenarioNuevo.setTitle("Registrar Usurio");
            escenarioNuevo.setScene(escenaAdmin);
            escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
            escenarioNuevo.showAndWait();
        }catch(IOException io){
            Utilidades.mostrarAlertaSimple("Error", "Error al cargar.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicBtnRegistrarBitacora(ActionEvent event) {
        
    }

    @FXML
    private void clicBtnConsultarBitacora(ActionEvent event) {
        
    }
    
}
