
package inventarios.perifericos;

import inventarios.dao.PerifericoDAO;
import inventarios.pojo.Periferico;
import inventarios.pojo.ResultadoOperacion;
import inventarios.util.Utilidades;
import java.io.IOException;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author miran
 */
public class FXMLModificaPerifericoController implements Initializable {
    Periferico periferico = new Periferico();
    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private ComboBox<String> cbEstado;
    @FXML
    private TextField tfIdentificador;
    @FXML
    private TextField tfMarca;
    private ObservableList<String> listaEstado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarCombo();
    }

    private void llenarCombo(){

        ArrayList<String> estados = new ArrayList<String>();
        estados.add("Excelente");
        estados.add("Bueno");
        estados.add("Aceptable");
        estados.add("Defectuoso");
        estados.add("Obsoleto");

        listaEstado = FXCollections.observableArrayList(estados);
        
        cbEstado.setItems(listaEstado);
    }    

    void setPeriferico(Periferico perifericoSeleccionado) {
        this.periferico = perifericoSeleccionado;
        
        String oldTipo = periferico.getTipo();
        String oldID = periferico.getIdentificador();
        String oldMarca = periferico.getMarca();
        String oldEstado = periferico.getEstado();
        cbTipo.setValue(oldTipo);
        cbTipo.setDisable(true);
        tfIdentificador.setText(oldID);
        tfMarca.setText(oldMarca);
        cbEstado.setValue(oldEstado);
    }
    
    @FXML
    private void modificarPeriferico(ActionEvent event) throws IOException {
        Periferico perifericoNuevo = new Periferico();
        
        String identificador = tfIdentificador.getText();
        String marca = tfMarca.getText();
        String estado = cbEstado.getValue();
        
        int id = periferico.getIdPeriferico();
        
        perifericoNuevo.setEstado(estado);
        perifericoNuevo.setIdentificador(identificador);
        perifericoNuevo.setMarca(marca);
        
        if((identificador.isEmpty() || marca.isEmpty() || estado == null)){
            Utilidades.mostrarAlertaSimple("Campos vacíos", "No puede haber campos vacíos.", Alert.AlertType.WARNING);
        }else {
            PerifericoDAO pDao = new PerifericoDAO();
            try {
                ResultadoOperacion resultado = pDao.modificarPeriferico(id, perifericoNuevo);
                if (resultado.isError()) {
                    Utilidades.mostrarAlertaSimple("Error", "Error en el registro de actualización.", Alert.AlertType.ERROR);
                } else {
                    Utilidades.mostrarAlertaSimple("Actualización exitosa", "Periférico actualizado con éxito.\n"+" Actualice la tabla para"
                            + "visualizar los cambios.", Alert.AlertType.INFORMATION);
                    Stage stage = (Stage) cbEstado.getScene().getWindow();
                    stage.close();
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
