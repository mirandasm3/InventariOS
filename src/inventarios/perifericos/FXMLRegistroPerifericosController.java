
package inventarios.perifericos;

import inventarios.pojo.Periferico;
import inventarios.dao.PerifericoDAO;
import inventarios.pojo.ResultadoOperacion;
import inventarios.util.Utilidades;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author miran
 */
public class FXMLRegistroPerifericosController implements Initializable {

    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private ComboBox<String> cbEstado;
    @FXML
    private TextField tfIdentificador;
    @FXML
    private TextField tfMarca;
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
    private ObservableList<String> listaEstado;
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
        estados.add("Excelente");
        estados.add("Bueno");
        estados.add("Aceptable");
        estados.add("Defectuoso");
        estados.add("Obsoleto");

        listaEstado = FXCollections.observableArrayList(estados);
        
        cbEstado.setItems(listaEstado);
        
        ArrayList<String> tipos = new ArrayList<String>();
        tipos.add("Mouse");
        tipos.add("Teclado");
        tipos.add("Monitor");
        tipos.add("Audífonos");
        listaTipo = FXCollections.observableArrayList(tipos);
        
        cbTipo.setItems(listaTipo);
    }
  
    @FXML
    private void registrarPeriferico(ActionEvent event) {
        
        Periferico periferico = new Periferico();
        
        String identificador = tfIdentificador.getText();
        String marca = tfMarca.getText();
        String tipo = cbTipo.getValue();
        String estado = cbEstado.getValue();
        
        periferico.setIdCentroComputo(1);
        periferico.setEstado(estado);
        periferico.setIdentificador(identificador);
        periferico.setMarca(marca);
        periferico.setTipo(tipo);
        
        if((identificador.isEmpty() || marca.isEmpty()) || (tipo == null || estado == null)){
            Utilidades.mostrarAlertaSimple("Campos vacíos", "No puede haber campos vacíos.", Alert.AlertType.WARNING);
        }else {
            PerifericoDAO pDao = new PerifericoDAO();
            try {
                
                ResultadoOperacion resultado = pDao.registrarPeriferico(periferico);
                if (resultado.isError()) {
                    Utilidades.mostrarAlertaSimple("Error", "Error en el registro.", Alert.AlertType.ERROR);
                } else {
                    listaPerifericos.add(periferico);
                    Utilidades.mostrarAlertaSimple("Registro exitoso", "Periférico registrado con éxito.", Alert.AlertType.INFORMATION);
                    tfIdentificador.clear();
                    tfMarca.clear();
                    cbTipo.setValue(null);
                    cbEstado.setValue(null);
                }
            } catch (SQLException ex) {
                Utilidades.mostrarAlertaSimple("Error", "Error en la conexión con la base de datos. Intente de nuevo más tarde.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void volver(ActionEvent event) {
        Stage stage = (Stage) cbEstado.getScene().getWindow();
        stage.close();
    }
}
    

