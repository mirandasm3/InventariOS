
package inventarios.equipocomputo;

import inventarios.dao.CentroComputoDAO;
import inventarios.dao.EquipoDAO;
import inventarios.pojo.CentroComputo;
import inventarios.pojo.Equipo;
import inventarios.pojo.ResultadoOperacion;
import inventarios.util.Utilidades;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    @FXML
    private ComboBox<String> cbCC;
    
    private ObservableList<String> listaCC;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarCombo();
    }

    private void llenarCombo(){
        ArrayList<String> ccs = new ArrayList<String>();
        ArrayList<CentroComputo> ccConsulta;
        try {
            ccConsulta = CentroComputoDAO.consultarCCs();

            for (CentroComputo cc : ccConsulta) {
                String clavee = cc.getClave();
                ccs.add(clavee);
            }
        } catch (SQLException ex) {
            Utilidades.mostrarAlertaSimple("Error", "Error en la conexión con la base de datos. Intente de nuevo más tarde.", Alert.AlertType.ERROR);
        }
        listaCC = FXCollections.observableArrayList(ccs);
        cbCC.setItems(listaCC);
    }

    @FXML
    private void registrarEquipoComputo(ActionEvent event) throws SQLException {
        
        Equipo equipoComputo = new Equipo();       
        
        String identificador = tfIdentificador.getText().toUpperCase();
        String memoriaRam = tfMemoriaRam.getText();
        String cantidadRam = tfMemoriaRamCantidad.getText();
        String procesador = tfProcesador.getText();
        String tarjetaGrafica = tfTarjetaGrafica.getText();
        String tipoAlmacenamiento = tfTipoAlmacenamiento.getText();
        String espacioAlmacenamiento = tfEspacioAlmacenamiento.getText();
        String ubicacionFisica = tfUbicacionFisica.getText();
        String sistemaOperativo = tfSistemaOperativo.getText();
        String claveCC = cbCC.getValue();
        
        
        try{
            int idCentro = CentroComputoDAO.buscarCC(claveCC).getIdCC();
            String clave = CentroComputoDAO.buscarCC(claveCC).getClave();
            equipoComputo.setIdCentroComputo(idCentro);
        }catch(SQLException e){
            Utilidades.mostrarAlertaSimple("Error", "Error en la conexión con la base de datos. Intente de nuevo más tarde.", Alert.AlertType.ERROR);
        }       
        
        if(identificador.isEmpty() || memoriaRam.isEmpty() || procesador.isEmpty() || tarjetaGrafica.isEmpty() || tipoAlmacenamiento.isEmpty() || espacioAlmacenamiento.isEmpty() || ubicacionFisica.isEmpty() || sistemaOperativo.isEmpty() ||cantidadRam.isEmpty() || claveCC == null){
            Utilidades.mostrarAlertaSimple("ERROR", "No puede haber campos vacíos", Alert.AlertType.ERROR);
        }else{
            int cantidadRamInt = Integer.parseInt(cantidadRam);
            equipoComputo.setIdentificador(identificador);
            equipoComputo.setMemoriaRAM(memoriaRam);
            equipoComputo.setMemoriaRAMCantidad(cantidadRamInt);
            equipoComputo.setProcesador(procesador);
            equipoComputo.setTarjetaGrafica(tarjetaGrafica);
            equipoComputo.setTipoAlmacenamiento(tipoAlmacenamiento);
            equipoComputo.setEspacioAlmacenamiento(espacioAlmacenamiento);
            equipoComputo.setUbicacionFisica(ubicacionFisica);
            equipoComputo.setSistemaOperativo(sistemaOperativo);
            EquipoDAO eDao = new EquipoDAO();                
            try{
                boolean buscarEquipo = new EquipoDAO().buscarEquipo(identificador);
                if(buscarEquipo == false){
                    ResultadoOperacion respuesta = eDao.registrarEquipoComputo(equipoComputo);
                    if(respuesta.isError()){
                    Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
                }else{
                    listaEquipos.add(equipoComputo);
                    Utilidades.mostrarAlertaSimple("Registro exitoso", "Equipo de cómputo registrado con éxito", Alert.AlertType.INFORMATION);
                    Stage stage = (Stage) tfEspacioAlmacenamiento.getScene().getWindow();
                    stage.close();
                }
            }else{
                    Utilidades.mostrarAlertaSimple("Error", "El identificador ya ha sido registrado en la base de datos.", Alert.AlertType.ERROR);
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
