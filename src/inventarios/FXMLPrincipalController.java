
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
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author miran
 */
public class FXMLPrincipalController implements Initializable {

    @FXML
    private Text txtTitle;
    @FXML
    private Button btnInventario;
    @FXML
    private Button btnConsulta;
    @FXML
    private Button btnBitacora;
    @FXML
    private Button btnUsuario;
    @FXML
    private Button btnCC;
    @FXML
    private MenuButton btnAdmin;
    @FXML
    private Button btnInventario1;
    @FXML
    private Button btnConsulta1;
    @FXML
    private Button btnBitacora1;
    @FXML
    private Button btnUsuario1;
    @FXML
    private Button btnCC1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarImagenBoton();
    }    

    @FXML
    private void clicBtnRegistroInventario(ActionEvent event) {
        try{
             Parent vista = FXMLLoader.load(getClass().getResource("perifericos/FXMLSeleccionRegistro.fxml"));
             Scene escenaAdmin = new Scene(vista);
             Stage escenarioNuevo = new Stage();
             escenarioNuevo.setTitle("Registro de inventarios");
             escenarioNuevo.setScene(escenaAdmin);
             escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
             escenarioNuevo.showAndWait();
        }catch(IOException io){
            Utilidades.mostrarAlertaSimple("Error", "Error al cargar.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicBtnConsultarInventario(ActionEvent event) {
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
    private void clicBtnBitacoras(ActionEvent event) {
    }

    @FXML
    private void clicBtnUsuarios(ActionEvent event) {
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
    private void clicBtnCentrosComputo(ActionEvent event) {
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
    private void cerrarSesion(ActionEvent event) {
        Stage stage = (Stage) txtTitle.getScene().getWindow();
        stage.close();
    }
    
    private void cargarImagenBoton(){
        URL linkInventario = getClass().getResource("img/inventario.png");
        URL linkBitacora = getClass().getResource("img/bitacora.png");
        URL linkCC = getClass().getResource("img/cc.png");
        URL linkConsulta = getClass().getResource("img/consulta.png");
        URL linkUser = getClass().getResource("img/user.png");
        
        Image inventario = new Image(linkInventario.toString(), 24, 24, false, true);
        Image bitacora = new Image(linkBitacora.toString(), 24, 24, false, true);
        Image cc = new Image(linkCC.toString(), 24, 24, false, true);
        Image consulta = new Image(linkConsulta.toString(), 24, 24, false, true);
        Image user = new Image(linkUser.toString(), 24, 24, false, true);
        
        btnInventario.setGraphic(new ImageView(inventario));
        btnBitacora.setGraphic(new ImageView(bitacora));
        btnCC.setGraphic(new ImageView(cc));
        btnConsulta.setGraphic(new ImageView(consulta));
        btnUsuario.setGraphic(new ImageView(user));
        btnAdmin.setGraphic(new ImageView(user));
    }
}
