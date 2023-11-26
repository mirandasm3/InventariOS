
package inventarios.software;

import inventarios.InventariOS;
import inventarios.interfaz.INotificacionOperacion;
import inventarios.dao.EquipoHasSoftwareDAO;
import inventarios.dao.SoftwareDAO;
import inventarios.pojo.Software;
import inventarios.pojo.SoftwareListaRespuesta;
import inventarios.util.Constantes;
import inventarios.util.Utilidades;
import inventarios.util.SingletonSoftware;
import java.io.IOException;
import java.net.URL;
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
    private TableColumn colIdentificadorEquipo;
    @FXML
    private TableColumn colNombreSoftware;
    @FXML
    private TableColumn colVersion;
    @FXML
    private TableColumn colCentroComputo;
    @FXML
    private TextField tfBusqueda;
    @FXML
    private TableView<Software> tvSoftware;
    @FXML
    private RadioButton tbNombre;
    @FXML
    private ToggleGroup tgFiltroBusqueda;
    @FXML
    private RadioButton tbIdentificadorEquipo;
    private ObservableList<Software> softwareLista;
    private int filtroSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaSoftware();
        cargarTablaSoftware();
        configurarCambioFiltro();
    }    

    @FXML
    private void btnConsultar(ActionEvent event) {

     
  }



    @FXML
    private void btnEliminar(ActionEvent event) {
      Software softwareSeleccionado = tvSoftware.getSelectionModel().getSelectedItem();
      if(softwareSeleccionado !=null){
        Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Desea eliminar el registro?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
          int codigoRespuesta = SoftwareDAO.eliminarSoftware(softwareSeleccionado.getIdSoftware());
          switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
              Utilidades.mostrarAlertaSimple("Error de conexion", "Error en la "
                      + "conexión con la base de datos.", Alert.AlertType.ERROR);
              break;
            case Constantes.ERROR_CONSULTA:
              Utilidades.mostrarAlertaSimple("Error de consulta", "Por el "
                        + "momento no se puede obtener la información.", Alert.AlertType.INFORMATION);
              break;
            case Constantes.OPERACION_EXITOSA:
              Utilidades.mostrarAlertaSimple("Software actualizado", "La "
                        + "informacion del del software fue eliminada correctamente", Alert.AlertType.INFORMATION);
              EquipoHasSoftwareDAO.eliminarSoftware_Equipo(softwareSeleccionado.getIdSoftware(), softwareSeleccionado.getIdEquipo());
              cargarTablaSoftware();
              break;      
          }
        }else{
           alert.close();
        }
      }else{
        Utilidades.mostrarAlertaSimple("Selecciona un alumno", "Para borrar un alumno debes seleccionarlo previamente en la taba",
                    Alert.AlertType.WARNING);
      }
    }

    private void cargarTablaSoftware() {
        softwareLista = FXCollections.observableArrayList();
        SoftwareListaRespuesta respuestaBD = SoftwareDAO.obtenerInformacionSoftware();
        switch(respuestaBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarAlertaSimple("Sin conexion",
                        "Lo sentimos por el momento no hay conexión para pdoer cargar la información",
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarAlertaSimple("Error al cargar la base de datos",
                        "Hubo un error al cargar la información por favor inténtelo más tarde",
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                softwareLista.addAll(respuestaBD.getSoftwareLista());
                tvSoftware.setItems(softwareLista);
                configurarBusquedaTabla();
                break;
        }
        
    }

    private void configurarTablaSoftware() {
       colIdentificadorEquipo.setCellValueFactory(new PropertyValueFactory("identificadorEquipo"));
       colNombreSoftware.setCellValueFactory(new PropertyValueFactory("nombre"));
       colVersion.setCellValueFactory(new PropertyValueFactory("version"));
       colCentroComputo.setCellValueFactory(new PropertyValueFactory("identificadorCC"));

    }

    private void configurarBusquedaTabla() {
        if(softwareLista.size()>0){
            FilteredList<Software> filtradoSoftware = new FilteredList<>(softwareLista, p -> true);
            tfBusqueda.textProperty().addListener(new ChangeListener<String>(){
                public void changed(ObservableValue<? extends String> observable,
                String oldValue, String newValue){
                filtradoSoftware.setPredicate(softwareFiltro ->{
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerNewValue = newValue.toLowerCase();
                    if(filtroSeleccionado == 1){
                        if(softwareFiltro.getNombre().toLowerCase().contains(lowerNewValue)){
                            return true;
                        }
                    }else{ 
                        if(softwareFiltro.getIdentificadorEquipo().toLowerCase().contains(lowerNewValue)){
                            return true;
                        }
                    }
                    return false;
                });
            }
            });
            SortedList<Software> listaOrdenadaSoftware = new SortedList<>(filtradoSoftware);
            listaOrdenadaSoftware.comparatorProperty().bind(tvSoftware.comparatorProperty());
            tvSoftware.setItems(listaOrdenadaSoftware);
        }
    }
    private int configurarCambioFiltro(){
        tgFiltroBusqueda.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                System.out.println("Cambio");
                if(tbNombre.isSelected()){
                    filtroSeleccionado = 1;
                }else{
                    filtroSeleccionado = 2;
                }
                    
            }
        });
        return filtroSeleccionado;
    }
    
    @FXML
    private void btnModificar(ActionEvent event) {
        Software softwareSeleccionado = tvSoftware.getSelectionModel().getSelectedItem();
        if(softwareSeleccionado == null){
            Utilidades.mostrarAlertaSimple("Debe elegir un software", 
                    "Intente seleccionar un software antes de intentar modificarlo", 
                    Alert.AlertType.INFORMATION);
        }else{
                SingletonSoftware.getInstance().esEdicion = true;
                SingletonSoftware.getInstance().softwareActivo = softwareSeleccionado;
                Stage escenarioBase = (Stage) tvSoftware.getScene().getWindow();
                Parent vista;
                  try {
                    vista = FXMLLoader.load(getClass().getResource("software/FXMLRegistrarSoftware.fxml"));
                    Scene escenaAdmin = new Scene(vista);
                    Stage escenarioNuevo = new Stage();
                    escenarioNuevo.setTitle("Registro de software");
                    escenarioNuevo.setScene(escenaAdmin);
                    escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
                    escenarioNuevo.showAndWait();
                  } catch (IOException ex) {
                    Utilidades.mostrarAlertaSimple("Error", "Error al cargar.", Alert.AlertType.ERROR);
                  }

            }
        }
}