package inventarios.software;

import inventarios.pojo.EquipoHasSoftwareRespuesta;
import inventarios.dao.EquipoDAO;
import inventarios.pojo.Software;
import inventarios.interfaz.INotificacionOperacion;
import inventarios.dao.EquipoHasSoftwareDAO;
import inventarios.dao.SoftwareDAO;
import inventarios.pojo.Equipo;
import inventarios.pojo.EquipoRespuesta;
import inventarios.pojo.ResultadoOperacion;
import inventarios.pojo.SoftwareListaRespuesta;
import inventarios.pojo.SoftwareRespuesta;
import inventarios.util.Constantes;
import inventarios.util.Utilidades;
import inventarios.util.SingletonSoftware;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class FXMLRegistrarSoftwareController implements Initializable {
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfVersion;
    @FXML
    private TextField tfTamaño;
    @FXML
    private TextField tfEditor;
    @FXML
    private TableView<Software> tvSoftwares;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcVersion;
    @FXML
    private TableColumn tcEditor;
    @FXML
    private TableColumn tcTamaño;
    
    private ObservableList<Software> listaSoftwares = FXCollections.observableArrayList();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcVersion.setCellValueFactory(new PropertyValueFactory<>("version"));
        tcEditor.setCellValueFactory(new PropertyValueFactory<>("editor"));
        tcTamaño.setCellValueFactory(new PropertyValueFactory<>("tamaño"));
        
        tvSoftwares.setItems(listaSoftwares);
    }

    @FXML
    private void btnRegistrar(ActionEvent event) {
        Software software = new Software();
        
        String nombre = tfNombre.getText();
        String version = tfVersion.getText();
        String editor = tfEditor.getText();
        String tamaño = tfTamaño.getText();
        
        software.setNombre(nombre);
        software.setVersion(version);
        software.setEditor(editor);
        software.setTamaño(tamaño);
        
        if((nombre.isEmpty() || version.isEmpty() || editor.isEmpty() || tamaño.isEmpty())){
            Utilidades.mostrarAlertaSimple("Campos vacíos", "No puede haber campos vacíos.", Alert.AlertType.WARNING);
        }else{
            SoftwareDAO sDao = new SoftwareDAO();
            try {
                boolean buscarSoftware = new SoftwareDAO().buscarSoftware(nombre, version);
                if(buscarSoftware == false){
                    ResultadoOperacion resultado = sDao.registrarSoftware(software);
                    if(resultado.isError()){
                        Utilidades.mostrarAlertaSimple("Error", "Error en el registro", Alert.AlertType.ERROR);
                    }else{
                        listaSoftwares.add(software);
                        Utilidades.mostrarAlertaSimple("Registro exitoso", "Software registrado con éxito", Alert.AlertType.INFORMATION);
                        tfNombre.clear();
                        tfVersion.clear();
                        tfEditor.clear();
                        tfTamaño.clear();
                    }
                }else{
                    Utilidades.mostrarAlertaSimple("Error", "El software y su versión ya han sido registrados en la base de datos.", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                Utilidades.mostrarAlertaSimple("Error", "Error en la conexión con la base de datos. Intente de nuevo más tarde.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void btnVolver(ActionEvent event) {
        Stage stage = (Stage) tfEditor.getScene().getWindow();
        stage.close();
    }
}
