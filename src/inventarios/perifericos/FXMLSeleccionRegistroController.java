
package inventarios.perifericos;

import inventarios.util.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author diana
 */
public class FXMLSeleccionRegistroController implements Initializable {

    @FXML
    private ComboBox<String> cbCC;
    @FXML
    private ComboBox<String> cbTipo;
    private ObservableList<String> listaCC;
    private ObservableList<String> listaTipo;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarCombos();
    }

    private void llenarCombos(){

        ArrayList<String> estados = new ArrayList<String>();
        estados.add("CC1");

        listaCC = FXCollections.observableArrayList(estados);
        
        cbCC.setItems(listaCC);
        
        ArrayList<String> tipos = new ArrayList<String>();
        tipos.add("Equipo de cómputo");
        tipos.add("Periféricos");
        listaTipo = FXCollections.observableArrayList(tipos);
        
        cbTipo.setItems(listaTipo);
    }

    @FXML
    private void volver(ActionEvent event) {
        Stage stage = (Stage) cbCC.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void registrarInventario(ActionEvent event) {
        String tipo = cbTipo.getValue();
        String cc = cbCC.getValue();
        if(tipo == null || tipo.isEmpty() || cc == null || cc.isEmpty()){
            Utilidades.mostrarAlertaSimple("Valores vacíos", "Debe seleccionar ambos filtros.", Alert.AlertType.WARNING);
        }else{
            if(cbTipo.getValue().contentEquals("Periféricos")){
                try{
                    Parent vista = FXMLLoader.load(getClass().getResource("FXMLRegistroPerifericos.fxml"));
                    Scene escenaAdmin = new Scene(vista);
                    Stage escenarioNuevo = new Stage();
                    escenarioNuevo.setTitle("Registro de periféricos");
                    escenarioNuevo.setScene(escenaAdmin);
                    escenarioNuevo.initModality(Modality.APPLICATION_MODAL);
                    escenarioNuevo.showAndWait();
                }catch(IOException io){
                    Utilidades.mostrarAlertaSimple("Error", "Error al cargar.", Alert.AlertType.ERROR);
                }
            }else{
                Utilidades.mostrarAlertaSimple("Registro de equipo de cómputo", "Registro de equipos de cómputo.", Alert.AlertType.INFORMATION);
            }
        }
    }
}