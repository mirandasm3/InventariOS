/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package inventarios.equipocomputo;

import inventarios.dao.EquipoDAO;
import inventarios.pojo.Equipo;
import inventarios.pojo.ResultadoOperacion;
import inventarios.util.Utilidades;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Manuel
 */
public class FXMLModificaEquipoComputoController implements Initializable {
    Equipo equipo = new Equipo();
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
    private TextField tfMemoriaRamCantidad;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void modificarEquipoComputo(ActionEvent event) {
        Equipo equipoNuevo = new Equipo();
        
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
        
        int id = equipo.getIdEquipoComputo();
        
        equipoNuevo.setIdentificador(identificador);
        equipoNuevo.setMemoriaRAM(memoriaRam);
        equipoNuevo.setMemoriaRAMCantidad(cantidadRamInt);
        equipoNuevo.setProcesador(procesador);
        equipoNuevo.setTarjetaGrafica(tarjetaGrafica);
        equipoNuevo.setTipoAlmacenamiento(tipoAlmacenamiento);
        equipoNuevo.setEspacioAlmacenamiento(espacioAlmacenamiento);
        equipoNuevo.setUbicacionFisica(ubicacionFisica);
        equipoNuevo.setSistemaOperativo(sistemaOperativo);
        
        if((identificador.isEmpty() || procesador.isEmpty() || memoriaRam.isEmpty() || cantidadRam.isEmpty() || tarjetaGrafica.isEmpty() || tipoAlmacenamiento.isEmpty() || espacioAlmacenamiento.isEmpty() || ubicacionFisica.isEmpty() || sistemaOperativo.isEmpty())){
            Utilidades.mostrarAlertaSimple("Campos vacíos", "No puede haber campos vacíos", Alert.AlertType.WARNING);
        }else{
            EquipoDAO eDao = new EquipoDAO();
            try {
                ResultadoOperacion resultado = eDao.modificarEquipo(id, equipoNuevo);
                if(resultado.isError()){
                    Utilidades.mostrarAlertaSimple("ERROR", "Error en la actualización", Alert.AlertType.ERROR);
                }else{
                   Utilidades.mostrarAlertaSimple("Actualización exitosa", "Equipo de cómputo actualizado con éxito. Actualice la tabla para visualizar los cambios.", Alert.AlertType.INFORMATION);
                    Stage stage = (Stage) tfEspacioAlmacenamiento.getScene().getWindow();
                    stage.close(); 
                }
            } catch (Exception e) {
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
            Stage escenarioRegistro = (Stage) tfIdentificador.getScene().getWindow();
            escenarioRegistro.close();
        }else{
            alert.close();
        }
    }
    
    void setEquipo(Equipo equipoSeleccionado){
        this.equipo = equipoSeleccionado;
        
        String oldIdentificador = equipo.getIdentificador();
        String oldProcesador = equipo.getProcesador();
        String oldMemoriaRAM = equipo.getMemoriaRAM();
        int oldMemoriaRAMCantidad = equipo.getMemoriaRAMCantidad();
        String oldMemoriaRAMCantidadString = String.valueOf(oldMemoriaRAMCantidad);
        String oldTarjetaGrafica = equipo.getTarjetaGrafica();
        String oldTipoAlmacenamiento = equipo.getTipoAlmacenamiento();
        String oldEspacioAlmacenamiento = equipo.getEspacioAlmacenamiento();
        String oldUbicacionFisica = equipo.getUbicacionFisica();
        String oldSistemaOperativo = equipo.getSistemaOperativo();
        
        tfIdentificador.setText(oldIdentificador);
        tfProcesador.setText(oldProcesador);
        tfMemoriaRam.setText(oldMemoriaRAM);
        tfMemoriaRamCantidad.setText(oldMemoriaRAMCantidadString);
        tfTarjetaGrafica.setText(oldTarjetaGrafica);
        tfTipoAlmacenamiento.setText(oldTipoAlmacenamiento);
        tfEspacioAlmacenamiento.setText(oldEspacioAlmacenamiento);
        tfUbicacionFisica.setText(oldUbicacionFisica);
        tfSistemaOperativo.setText(oldSistemaOperativo);        
    }
    
}
