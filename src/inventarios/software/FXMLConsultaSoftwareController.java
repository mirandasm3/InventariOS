
package inventarios.software;

import inventarios.InventariOS;
import inventarios.interfaz.INotificacionOperacion;
import inventarios.dao.EquipoHasSoftwareDAO;
import inventarios.dao.SoftwareDAO;
import inventarios.pojo.ResultadoOperacion;
import inventarios.pojo.Software;
import inventarios.pojo.SoftwareListaRespuesta;
import inventarios.util.Constantes;
import inventarios.util.Utilidades;
import inventarios.util.SingletonSoftware;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class FXMLConsultaSoftwareController implements Initializable {
    @FXML
    private TableColumn clNombre;
    @FXML
    private TableColumn clVersion;
    @FXML
    private TableColumn clEditor;
    @FXML
    private TableColumn clTamaño;
    @FXML
    private TextField tfBusqueda;
    @FXML
    private TableView<Software> tvSoftware;
    
    ObservableList<Software> listaSoftwares = FXCollections.observableArrayList();
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        clNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clVersion.setCellValueFactory(new PropertyValueFactory<>("version"));
        clEditor.setCellValueFactory(new PropertyValueFactory<>("editor"));
        clTamaño.setCellValueFactory(new PropertyValueFactory<>("tamaño"));
        cargarTablaSoftware();
        tvSoftware.setItems(listaSoftwares);        
    }
    
    private void cargarTablaSoftware() {
        SoftwareDAO sDao = new SoftwareDAO();
        try {
            List<Software> resultados = sDao.consultarSoftware();
            listaSoftwares.clear();
            if(resultados != null){
                listaSoftwares.addAll(resultados);
            }else{
                listaSoftwares.addAll(resultados);
            }
        } catch (SQLException e) {
            Utilidades.mostrarAlertaSimple("Error", "Error en la conexión con la base de datos. Intente de nuevo más tarde.", Alert.AlertType.ERROR);
        }
    }    
    
    @FXML
    private void modificarSoftware(ActionEvent event) {
        Software softwareSeleccionado = tvSoftware.getSelectionModel().getSelectedItem();
        if(softwareSeleccionado == null){
            Utilidades.mostrarAlertaSimple("Falta selección", "Debe seleccionar un registro de la tabla", Alert.AlertType.WARNING);
        }else{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLModificaSoftware.fxml"));
                Parent vista = loader.load();
                FXMLModificaSoftwareController edicionController = loader.getController();
                edicionController.setSoftware(softwareSeleccionado);
                
                Scene escenaAdmin = new Scene(vista);
                Stage escenarioNuevo = new Stage();
                escenarioNuevo.setTitle("Edición de Software");
                escenarioNuevo.setScene(escenaAdmin);
                escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
                escenarioNuevo.showAndWait();
            } catch (IOException e) {
                Utilidades.mostrarAlertaSimple("Error", "Error al cargar la página.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void eliminarSoftware(ActionEvent event) {
        Software softwareSeleccionado = tvSoftware.getSelectionModel().getSelectedItem();
        if(softwareSeleccionado == null){
            Utilidades.mostrarAlertaSimple("Falta selección", "Debe seleccionar un registro de la tabla.", Alert.AlertType.WARNING);
        }else{
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de eliminación");
            alert.setHeaderText("¿Desea eliminar el software seleccionado?");
            alert.setContentText("Esta acción no se puede deshacer.");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
            
                SoftwareDAO sDao = new SoftwareDAO();
                ResultadoOperacion resultado;
                try {
                    resultado = sDao.eliminarSoftware(softwareSeleccionado.getNombre());
                    if(resultado.isError()){
                        Utilidades.mostrarAlertaSimple("Error", "Error en la eliminación.", Alert.AlertType.ERROR);
                    }else{
                        Utilidades.mostrarAlertaSimple("Eliminación exitosa", "Software eliminado con éxito.", Alert.AlertType.INFORMATION);
                        listaSoftwares.remove(softwareSeleccionado);
                        tvSoftware.refresh();
                    }
                } catch (SQLException e) {
                    Utilidades.mostrarAlertaSimple("Error", "No se pudo eliminar el registro de software.", Alert.AlertType.ERROR);
                }
            }else{
                alert.close();
            }
        }
    }

    @FXML
    private void volver(ActionEvent event) {
        Stage stage = (Stage) tfBusqueda.getScene().getWindow();
        stage.close();
    }
}