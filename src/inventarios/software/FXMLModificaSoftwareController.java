package inventarios.software;

import inventarios.dao.SoftwareDAO;
import inventarios.pojo.ResultadoOperacion;
import inventarios.pojo.Software;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLModificaSoftwareController implements Initializable {

    @FXML
    private TextField tfVersion;
    @FXML
    private TextField tfEditor;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfTamaño;
    
    Software software = new Software();
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    void setSoftware(Software softwareSeleccionado){
        this.software = softwareSeleccionado;
        
        int idSoftware = software.getIdSoftware();
        String oldNombre = software.getNombre();
        String oldVersion = software.getVersion();
        String oldEditor = software.getEditor();
        String oldTamaño = software.getTamaño();
      
        tfNombre.setText(oldNombre);
        tfVersion.setText(oldVersion);
        tfEditor.setText(oldEditor);
        tfTamaño.setText(oldTamaño);
    }

    @FXML
    private void modificarSoftware(ActionEvent event) {
        Software softwareNuevo = new Software();
        
        int idSoftware = software.getIdSoftware();
        String nombre = tfNombre.getText();
        String version = tfVersion.getText();
        String editor = tfEditor.getText();
        String tamaño = tfTamaño.getText();
        
        
        softwareNuevo.setNombre(nombre);
        softwareNuevo.setVersion(version);
        softwareNuevo.setEditor(editor);
        softwareNuevo.setTamaño(tamaño);
        
        if((nombre.isEmpty() || version.isEmpty() || editor.isEmpty() || tamaño.isEmpty())){
            Utilidades.mostrarAlertaSimple("Campos vacíos", "No puede haber campos vacíos.", Alert.AlertType.WARNING);
        }else{
            SoftwareDAO sDao = new SoftwareDAO();
            try {
                boolean buscarSoftware = new SoftwareDAO().buscarSoftware(nombre, version);
                if(buscarSoftware == false){
                    ResultadoOperacion resultado = sDao.modificarSoftware(idSoftware, softwareNuevo);
                    if(resultado.isError()){
                    Utilidades.mostrarAlertaSimple("Error", resultado.getMensaje(), Alert.AlertType.ERROR);
                    }else{
                   Utilidades.mostrarAlertaSimple("Actualización exitosa", "Software actualizado con éxito.\n"+" Actualice la tabla para"
                            + "visualizar los cambios.", Alert.AlertType.INFORMATION);
                    Stage stage = (Stage) tfEditor.getScene().getWindow();
                    stage.close(); 
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
    private void volver(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Desea cancelar la operación?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage escenarioRegistro = (Stage) tfEditor.getScene().getWindow();
            escenarioRegistro.close();
        }else{
            alert.close();
        }
    }    
}
