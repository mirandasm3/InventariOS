
package inventarios.usuarios;

import inventarios.dao.AdministradorDAO;
import inventarios.pojo.Administrador;
import inventarios.pojo.ResultadoOperacion;
import inventarios.util.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author raudel
 */
public class FXMLConsultaUsuariosController implements Initializable {

    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnModificar;
    @FXML
    private TableView<Administrador> tvUsuarios;
    @FXML
    private TableColumn<?, ?> clNombre;
    @FXML
    private TableColumn<?, ?> clNoPersonal;
    @FXML
    private TableColumn<?, ?> clContacto;
    
    private ObservableList<Administrador> listaUsuarios = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clNoPersonal.setCellValueFactory(new PropertyValueFactory<>("numeroPersonal"));
        clContacto.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        
        tvUsuarios.setItems(listaUsuarios);
        llenarTablaUsuarios();
    }    

    @FXML
    private void eliminarUsuario(ActionEvent event) {
         Administrador usuarioSeleccionado = tvUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado == null) {
            Utilidades.mostrarAlertaSimple("Falta selección", "Debe seleccionar un registro de la tabla", Alert.AlertType.WARNING);
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de eliminación");
            alert.setHeaderText("¿Desea eliminar el usuario seleccionado?");
            alert.setContentText("Esta acción no se puede deshacer.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
            
                AdministradorDAO usuarioDao = new AdministradorDAO();
                ResultadoOperacion resultado;
                try {
                    resultado = usuarioDao.eliminarUsuario(usuarioSeleccionado.getNumeroPersonal());
                    if (resultado.isError()) {
                        Utilidades.mostrarAlertaSimple("Error", resultado.getMensaje(), Alert.AlertType.ERROR);
                    } else {
                        Utilidades.mostrarAlertaSimple("Eliminación exitosa", "Usuario eliminado con éxito.", Alert.AlertType.INFORMATION);
                        listaUsuarios.remove(usuarioSeleccionado);
                        tvUsuarios.refresh();
                    }
                } catch (SQLException ex) {
                    Utilidades.mostrarAlertaSimple("Error", "No se pudo eliminar el registro de usuario.", Alert.AlertType.ERROR);
                }
            }else{
                alert.close();
            }
        }
    }

    @FXML
    private void modificarUsuario(ActionEvent event) {
         Administrador adminSeleccionado = tvUsuarios.getSelectionModel().getSelectedItem();
        if (adminSeleccionado == null) {
            Utilidades.mostrarAlertaSimple("Falta selección", "Debe seleccionar un registro de la tabla", Alert.AlertType.WARNING);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLModificaUsuario.fxml"));
                Parent vista = loader.load();
                FXMLModificaUsuarioController edicionController = loader.getController();
                edicionController.set(adminSeleccionado);

                Scene escenaAdmin = new Scene(vista);
                Stage escenarioNuevo = new Stage();
                escenarioNuevo.setTitle("Edición de usuario");
                escenarioNuevo.setScene(escenaAdmin);
                escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
                escenarioNuevo.showAndWait();
            } catch (IOException e) {
                Utilidades.mostrarAlertaSimple("Error", "Error al cargar la página.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void volver(ActionEvent event) {
        Stage stage = (Stage) tvUsuarios.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void llenarTablaUsuarios(){
        
         AdministradorDAO usuarioDao = new AdministradorDAO();
            try {
                List<Administrador> resultados = usuarioDao.consultarUsuarios();
                listaUsuarios.clear();
                if (resultados != null) {
                listaUsuarios.addAll(resultados);
                }else{
                    listaUsuarios.addAll(resultados);
                }
            } catch (SQLException ex) {
                Utilidades.mostrarAlertaSimple("Error", "Error en la conexión con la base de datos. Intente de nuevo más tarde.", Alert.AlertType.ERROR);
            }
    }
    
}
