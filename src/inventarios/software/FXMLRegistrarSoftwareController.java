/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package inventarios.software;

import inventarios.pojo.EquipoHasSoftwareRespuesta;
import inventarios.dao.EquipoDAO;
import inventarios.pojo.Software;
import inventarios.interfaz.INotificacionOperacion;
import inventarios.dao.EquipoHasSoftwareDAO;
import inventarios.dao.SoftwareDAO;
import inventarios.pojo.Equipo;
import inventarios.pojo.EquipoRespuesta;
import inventarios.pojo.SoftwareListaRespuesta;
import inventarios.pojo.SoftwareRespuesta;
import inventarios.util.Constantes;
import inventarios.util.Utilidades;
import inventarios.util.singletonSoftware;
import java.net.URL;
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
    private Software softwareActivo = new Software();
    private Software softwareNuevo;
    private boolean edicion;
    private INotificacionOperacion interfazNotificacion;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfVersion;
    @FXML
    private TextField tfEditor;
    @FXML
    private TextField tfTamano;
    @FXML
    private DatePicker dpFechaInstalacion;
    @FXML
    private TableView<Equipo> tvEquipos;
    @FXML
    private TableColumn colIdentificador;
    @FXML
    private TableColumn colCentroComputo;
    @FXML
    private TableColumn colUbicacion;
    @FXML
    private TableColumn colAlmacenamiento;
    @FXML
    private TableColumn colRAM;
    @FXML
    private TableColumn colCPU;
    @FXML
    private Label lbTitulo;
    private boolean camposValidos = false;
    private ObservableList<Equipo> equipos;
    private int idEquipoSeleccionado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        if(singletonSoftware.getInstance().esEdicion){
            softwareActivo = singletonSoftware.getInstance().softwareActivo;
            lbTitulo.setText("Modificar software");
            cargarInformacionSoftware();
        }else{
            lbTitulo.setText("Registrar software");
            softwareActivo = new Software();
        }
    }    


    private void cargarInformacionSoftware() {
        tfNombre.setText(softwareActivo.getNombre());
        tfVersion.setText(softwareActivo.getVersion());
        tfEditor.setText(softwareActivo.getEditor());
        tfTamano.setText(softwareActivo.getTamano());
        
    }

    @FXML
    private void btnRegistrar(ActionEvent event) {
        validarCampos();
        
        Equipo equipoSeleccionado = tvEquipos.getSelectionModel().getSelectedItem();
        if(equipoSeleccionado != null) {
            validarCampos();
            if(camposValidos){
                establecerSoftwareEditado();
                if(singletonSoftware.getInstance().esEdicion){
                    actualizarSoftware(softwareActivo);
                }else{
                    registrarSoftware(softwareActivo);
                    Utilidades.mostrarAlertaSimple("Registro", "Registrado", Alert.AlertType.CONFIRMATION);
                }
            }
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona un equipo", "Selecciona el registro de la tabla del equipo para asignar el software", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
      Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirmación");
      alert.setHeaderText("¿Desea cacnelar la operación?");
      
      Optional<ButtonType> result = alert.showAndWait();
       if(result.get() == ButtonType.OK){
           resetearSingleton();
           regresarPantalla();
       }else{
         alert.close();
       }
    }
    
    private void validarCampos() {
        camposValidos = true;
        if(tfNombre.getText().isEmpty()){
            tfNombre.setStyle("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;");
            tfNombre.setVisible(true);
            camposValidos = false;
        }
        if(tfVersion.getText().isEmpty()){
            tfVersion.setStyle("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;");
            tfVersion.setVisible(true);
            camposValidos = false;
        }
        if(tfEditor.getText().isEmpty()){
            tfEditor.setStyle("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;");
            tfEditor.setVisible(true);
            camposValidos = false;
        }
        if(tfTamano.getText().isEmpty()){
            tfTamano.setStyle("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;");
            tfTamano.setVisible(true);
            camposValidos = false;
        }
    }

    private void establecerSoftwareEditado() {
            softwareActivo.setNombre(tfNombre.getText());
            softwareActivo.setVersion (tfVersion.getText()) ;
            softwareActivo.setEditor(tfEditor.getText());
            softwareActivo.setTamano(tfTamano.getText());      
    }

    private void actualizarSoftware(Software softwareActivo) {
        int codigoRespuesta = SoftwareDAO.modificarSoftware(softwareActivo,idEquipoSeleccionado);
        switch (codigoRespuesta) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarAlertaSimple("Error de conexion",
                        "Error en la conexión con la base de datos.",
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarAlertaSimple("Error de consulta",
                        "Por el momento no se puede obtener la información.",
                        Alert.AlertType.INFORMATION);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarAlertaSimple("Software actualizado", 
                        "La informacion del software fue actualizada correctamente",
                        Alert.AlertType.INFORMATION);
                EquipoHasSoftwareDAO.modificarRelacionEquipoSoftware(
                        softwareActivo.getIdSoftware(), softwareActivo.getIdEquipo(), idEquipoSeleccionado);
                resetearSingleton();
                regresarPantalla();
                break;
        }
    }
    private void registrarSoftware(Software softwareActivo) {
        SoftwareRespuesta respuestaSoftware = SoftwareDAO.registrarSoftware(softwareActivo);
        int codigoRespuesta = respuestaSoftware.getCodigoRespuesta();
        int idNuevoSoftwareRegistrado = respuestaSoftware.getSoftwareRespuesta().getIdSoftware();
        switch (codigoRespuesta) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarAlertaSimple("Error de conexion",
                        "Error en la conexión con la base de datos.",
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarAlertaSimple("Error de consulta",
                        "Por el momento no se puede obtener la información.",
                        Alert.AlertType.INFORMATION);
                break;
            case Constantes.OPERACION_EXITOSA:
                asignarSoftwareEquipo(idNuevoSoftwareRegistrado, idEquipoSeleccionado);
                regresarPantalla();
                break;
        }
    }
    private void regresarPantalla() {
        Stage stage = (Stage) tfEditor.getScene().getWindow();
        stage.close();
    }
    
    private void configurarTabla(){
        colIdentificador.setCellValueFactory(new PropertyValueFactory("identificador"));
        colCentroComputo.setCellValueFactory(new PropertyValueFactory("nombreCentroComputo"));
        colUbicacion.setCellValueFactory(new PropertyValueFactory("ubicacionFisica"));
        colAlmacenamiento.setCellValueFactory(new PropertyValueFactory("espacioAlmacenamiento"));
        colRAM.setCellValueFactory(new PropertyValueFactory("memoriaRAMCantidad"));
        colCPU.setCellValueFactory(new PropertyValueFactory("procesador"));
        
    }
    
    private void cargarInformacionTabla(){
        equipos = FXCollections.observableArrayList();
        EquipoRespuesta respuestaBD = EquipoDAO.obtenerInformacionEquipo();
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarAlertaSimple("Sin conexion",
                        "Por el momento no hay conexión con la base de datos, por favor reintente más tarde",
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarAlertaSimple("Error de conexión con la base de datos",
                        "Por favor inténtelo más tarde",
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                equipos.addAll(respuestaBD.getEquipoLista());
                tvEquipos.setItems(equipos);
                break;
        }
    }

    private void asignarSoftwareEquipo(int idSoftware, int idEquipoSeleccionado) {
        EquipoHasSoftwareRespuesta respuestaBD = EquipoHasSoftwareDAO.registrarSoftware_Equipo(idSoftware,idEquipoSeleccionado);
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarAlertaSimple("Sin conexion",
                        "Eror de conexión con la base de datos, por favor intente más tarde",
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarAlertaSimple("Error al cargar la base de datos",
                        "Hubo un error al cargar la información por favor inténtelo más tarde",
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarAlertaSimple("Software actualizado", 
                        "La informacion de software fue registrada correctamente",
                        Alert.AlertType.INFORMATION);
                break;
        }
    }

    private void resetearSingleton() {
        singletonSoftware.getInstance().softwareActivo = null;
        singletonSoftware.getInstance().esEdicion = false;
    }
}
