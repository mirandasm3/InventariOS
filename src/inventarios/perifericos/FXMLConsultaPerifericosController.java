
package inventarios.perifericos;

import inventarios.dao.PerifericoDAO;
import inventarios.pojo.Periferico;
import inventarios.pojo.ResultadoOperacion;
import inventarios.util.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author miran
 */
public class FXMLConsultaPerifericosController implements Initializable {

    @FXML
    private TableView<Periferico> tvPeriferico;
    @FXML
    private TableColumn clIdentificador;
    @FXML
    private TableColumn clTipo;
    @FXML
    private TableColumn clMarca;
    @FXML
    private TableColumn clEstado;
    @FXML
    private ComboBox<String> cbCC;
    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    private ObservableList<String> listaCC;
    private ObservableList<String> listaTipo;
    private ObservableList<Periferico> listaPerifericos = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarCombos();
        
        clIdentificador.setCellValueFactory(new PropertyValueFactory<>("identificador"));
        clTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        clMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        clEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        
        tvPeriferico.setItems(listaPerifericos);
        
    }

    private void llenarCombos(){
        ArrayList<String> estados = new ArrayList<String>();
        estados.add("CC2");

        listaCC = FXCollections.observableArrayList(estados);
        
        cbCC.setItems(listaCC);
        
        ArrayList<String> tipos = new ArrayList<String>();
        tipos.add("Mouse");
        tipos.add("Teclado");
        tipos.add("Monitor");
        tipos.add("Audífonos");
        listaTipo = FXCollections.observableArrayList(tipos);
        
        cbTipo.setItems(listaTipo);
    }

    @FXML
    private void consultarPeriferico(ActionEvent event) {
        String tipo = cbTipo.getValue();
        String cc = cbCC.getValue();
        if(tipo == null || tipo.isEmpty() || cc == null || cc.isEmpty()){
            Utilidades.mostrarAlertaSimple("Campos vacíos", "Debe seleccionar ambos filtros.", Alert.AlertType.WARNING);
        }else {
            PerifericoDAO pDao = new PerifericoDAO();
            try {
                List<Periferico> resultados = pDao.consultarPerifericos(1, tipo);
                listaPerifericos.clear();
                if (resultados != null) {
                listaPerifericos.addAll(resultados);
                }else{
                    listaPerifericos.addAll(resultados);
                }
            } catch (SQLException ex) {
                Utilidades.mostrarAlertaSimple("Error", "Error en la conexión con la base de datos. Intente de nuevo más tarde.", Alert.AlertType.ERROR);
            }
        }
    }
     
    @FXML
    private void volver(ActionEvent event) {
        Stage stage = (Stage) cbCC.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void modificarPeriferico(ActionEvent event) {
        Periferico perifericoSeleccionado = tvPeriferico.getSelectionModel().getSelectedItem();
        if (perifericoSeleccionado == null) {
            Utilidades.mostrarAlertaSimple("Falta selección", "Debe seleccionar un registro de la tabla", Alert.AlertType.WARNING);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLModificaPeriferico.fxml"));
                Parent vista = loader.load();
                FXMLModificaPerifericoController edicionController = loader.getController();
                edicionController.setPeriferico(perifericoSeleccionado);

                Scene escenaAdmin = new Scene(vista);
                Stage escenarioNuevo = new Stage();
                escenarioNuevo.setTitle("Edición de periférico");
                escenarioNuevo.setScene(escenaAdmin);
                escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
                escenarioNuevo.showAndWait();
            } catch (IOException e) {
                Utilidades.mostrarAlertaSimple("Error", "Error al cargar la página.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void eliminarPeriferico(ActionEvent event) {
        Periferico perifericoSeleccionado = tvPeriferico.getSelectionModel().getSelectedItem();
        if (perifericoSeleccionado == null) {
            Utilidades.mostrarAlertaSimple("Falta selección", "Debe seleccionar un registro de la tabla", Alert.AlertType.WARNING);
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de eliminación");
            alert.setHeaderText("¿Desea eliminar el periférico seleccionado?");
            alert.setContentText("Esta acción no se puede deshacer.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
            
                PerifericoDAO pDao = new PerifericoDAO();
                ResultadoOperacion resultado;
                try {
                    resultado = pDao.eliminarPeriferico(perifericoSeleccionado.getIdPeriferico());
                    if (resultado.isError()) {
                        Utilidades.mostrarAlertaSimple("Error", "Error en la eliminación.", Alert.AlertType.ERROR);
                    } else {
                        Utilidades.mostrarAlertaSimple("Eliminación exitosa", "Periférico eliminado con éxito.", Alert.AlertType.INFORMATION);
                        listaPerifericos.remove(perifericoSeleccionado);
                        tvPeriferico.refresh();
                    }
                } catch (SQLException ex) {
                    Utilidades.mostrarAlertaSimple("Error", "No se pudo eliminar el registro de periférico.", Alert.AlertType.ERROR);
                }
            }else{
                alert.close();
            }
        }
    }
    
    void recargarTabla(){
        tvPeriferico.refresh();
    }
}
