/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package inventarios.usuarios;

import inventarios.dao.AdministradorDAO;
import inventarios.pojo.Administrador;
import inventarios.pojo.ResultadoOperacion;
import inventarios.util.Utilidades;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LENOVO
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
    private TableColumn<?, ?> clPassword;
    @FXML
    private TableColumn<?, ?> clContacto;
    
    private ObservableList<Administrador> listaUsuarios = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clNoPersonal.setCellValueFactory(new PropertyValueFactory<>("numeroPersonal"));
        clPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
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
            alert.setHeaderText("¿Desea eliminar el periférico seleccionado?");
            alert.setContentText("Esta acción no se puede deshacer.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
            
                AdministradorDAO usuarioDao = new AdministradorDAO();
                ResultadoOperacion resultado;
                try {
                    resultado = usuarioDao.eliminarUsuario(usuarioSeleccionado.getNumeroPersonal());
                    if (resultado.isError()) {
                        Utilidades.mostrarAlertaSimple("Error", "Error en la eliminación.", Alert.AlertType.ERROR);
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
    }

    @FXML
    private void volver(ActionEvent event) {
        Stage stage = (Stage) tvUsuarios.getScene().getWindow();
        stage.close();
    }
    
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
