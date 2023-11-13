
package inventarios.centroscomputo;

import inventarios.dao.CentroComputoDAO;
import inventarios.pojo.CentroComputo;
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
public class FXMLConsultaCCController implements Initializable {

    @FXML
    private TableView<CentroComputo> tvCC;
    @FXML
    private TableColumn<?, ?> clClave;
    @FXML
    private TableColumn<?, ?> clNumero;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    
    private ObservableList<CentroComputo> listaCCs = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clClave.setCellValueFactory(new PropertyValueFactory<>("clave"));
        clNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        
        tvCC.setItems(listaCCs);
        llenarTablaCCs();
    }    

    @FXML
    private void clicBtnVolver(ActionEvent event) {
        Stage stage = (Stage) btnEliminar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clicBtnModificarCC(ActionEvent event) {
        CentroComputo ccSeleccionado = tvCC.getSelectionModel().getSelectedItem();
        if (ccSeleccionado == null) {
            Utilidades.mostrarAlertaSimple("Falta selección", "Debe seleccionar un registro de la tabla", Alert.AlertType.WARNING);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLModificaCC.fxml"));
                Parent vista = loader.load();
                FXMLModificaCCController edicionController = loader.getController();
                edicionController.set(ccSeleccionado);

                Scene escenaAdmin = new Scene(vista);
                Stage escenarioNuevo = new Stage();
                escenarioNuevo.setTitle("Edición de centro de cómputo");
                escenarioNuevo.setScene(escenaAdmin);
                escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
                escenarioNuevo.showAndWait();
            } catch (IOException e) {
                Utilidades.mostrarAlertaSimple("Error", "Error al cargar la página.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void clicBtnEliminarCC(ActionEvent event) {
         CentroComputo ccSeleccionado = tvCC.getSelectionModel().getSelectedItem();
        if (ccSeleccionado == null) {
            Utilidades.mostrarAlertaSimple("Falta selección", "Debe seleccionar un registro de la tabla", Alert.AlertType.WARNING);
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de eliminación");
            alert.setHeaderText("¿Desea eliminar el centro de cómputo seleccionado?");
            alert.setContentText("Esta acción no se puede deshacer.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
            
                CentroComputoDAO ccDao = new CentroComputoDAO();
                ResultadoOperacion resultado;
                try {
                    resultado = ccDao.eliminarCC(ccSeleccionado.getClave());
                    if (resultado.isError()) {
                        Utilidades.mostrarAlertaSimple("Error", "No es posible eliminar el centro de cómputo, ya que "
                                + "existen registros relacionados que dependen de él.", Alert.AlertType.ERROR);
                    } else {
                        Utilidades.mostrarAlertaSimple("Eliminación exitosa", "Centro de cómputo eliminado con éxito.", Alert.AlertType.INFORMATION);
                        listaCCs.remove(ccSeleccionado);
                        tvCC.refresh();
                    }
                } catch (SQLException ex) {
                    Utilidades.mostrarAlertaSimple("Error", "No se pudo eliminar el registro de CC.", Alert.AlertType.ERROR);
                }
            }else{
                alert.close();
            }
        }
    }
    
    @FXML
     private void llenarTablaCCs(){
        
         CentroComputoDAO ccDao = new CentroComputoDAO();
            try {
                List<CentroComputo> resultados = ccDao.consultarCCs();
                listaCCs.clear();
                if (resultados != null) {
                listaCCs.addAll(resultados);
                }else{
                    listaCCs.addAll(resultados);
                }
            } catch (SQLException ex) {
                Utilidades.mostrarAlertaSimple("Error", "Error en la conexión con la base de datos. Intente de nuevo más tarde.", Alert.AlertType.ERROR);
            }
    }  
}
