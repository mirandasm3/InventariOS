
package inventarios.equipocomputo;

import inventarios.dao.EquipoDAO;
import inventarios.pojo.Equipo;
import inventarios.pojo.ResultadoOperacion;
import inventarios.util.Utilidades;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLRegistroEquipoComputoController implements Initializable {

    @FXML
    private TextField tfIdentificador;
    @FXML
    private TextField tfMemoriaRam;
    @FXML
    private TextField tfProcesador;
    @FXML
    private TextField tfTarjetaGrafica;
    @FXML
    private TextField tfTipoAlmacenamiento;
    @FXML
    private TextField tfEspacioAlmacenamiento;
    @FXML
    private TextField tfUbicacionFisica;
    @FXML
    private TextField tfSistemaOperativo;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField tfMemoriaRamCantidad;
    
    private ObservableList<Equipo> listaEquipos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void registrarEquipoComputo(ActionEvent event) {
        
        Equipo equipoComputo = new Equipo();
        
        String identificador = tfIdentificador.getText();
        String memoriaRam = tfMemoriaRam.getText();
        String cantidadRam = tfMemoriaRamCantidad.getText();
        int cantidadRamInt = Integer.parseInt(cantidadRam);
        String procesador = tfProcesador.getText();
        String tarjetaGrafica = tfTarjetaGrafica.getText();
        String tipoAlmacenamiento = tfTipoAlmacenamiento.getText();
        String espacioAlmacenamiento = tfEspacioAlmacenamiento.getText();
        String ubicacionFisica = tfUbicacionFisica.getText();
        String sistemaOperativo = tfSistemaOperativo.getText();
        
        equipoComputo.setIdCentroComputo(1);
        equipoComputo.setIdentificador(identificador);
        equipoComputo.setMemoriaRAM(memoriaRam);
        equipoComputo.setMemoriaRAMCantidad(cantidadRamInt);
        equipoComputo.setProcesador(procesador);
        equipoComputo.setTarjetaGrafica(tarjetaGrafica);
        equipoComputo.setTipoAlmacenamiento(tipoAlmacenamiento);
        equipoComputo.setEspacioAlmacenamiento(espacioAlmacenamiento);
        equipoComputo.setUbicacionFisica(ubicacionFisica);
        equipoComputo.setSistemaOperativo(sistemaOperativo);
        
        if(identificador.isEmpty() || memoriaRam.isEmpty() || procesador.isEmpty() || 
                tarjetaGrafica.isEmpty() || tipoAlmacenamiento.isEmpty() || espacioAlmacenamiento.isEmpty() || 
                ubicacionFisica.isEmpty() || sistemaOperativo.isEmpty()){
            Utilidades.mostrarAlertaSimple("ERROR", "No puede haber campos vacíos", Alert.AlertType.ERROR);
        }else{
            EquipoDAO eDao = new EquipoDAO();
            try{               
                ResultadoOperacion respuesta = eDao.registrarEquipoComputo(equipoComputo);
                if(respuesta.isError()){
                    Utilidades.mostrarAlertaSimple("Error", "Error en el registro.", Alert.AlertType.ERROR);
                }else{
                    listaEquipos.add(equipoComputo);
                    Utilidades.mostrarAlertaSimple("Registro exitoso", "Equipo de cómputo registrado con éxito", Alert.AlertType.INFORMATION);
                    Stage stage = (Stage) tfEspacioAlmacenamiento.getScene().getWindow();
                    stage.close();
                }
            } catch (SQLException e) {
                Utilidades.mostrarAlertaSimple("Error", "Error en la conexión con la base de datos. Intente de nuevo más tarde.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) tfEspacioAlmacenamiento.getScene().getWindow();
        stage.close();
    }
    
}
