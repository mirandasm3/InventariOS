/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarios.bitacora;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import inventarios.dao.BitacoraDAO;
import inventarios.pojo.Bitacora;
import inventarios.pojo.BitacoraRespuesta;
import inventarios.util.Constantes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author seren
 */
public class FXMLConsultarBitacoraController implements Initializable {

  public TableView tablaBitacora;
  private BitacoraRespuesta bitacoraRespuesta;
    @FXML
    private TableColumn<?, ?> columnaFecha;
    @FXML
    private TableColumn<?, ?> columnaDescripcion;
    @FXML
    private TableColumn<?, ?> columnaIdEc;
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    tablaBitacora.setItems(obtenerBitacora());
  }

    @FXML
  public void Eliminar(ActionEvent actionEvent) {
      
  }

  public ObservableList<Bitacora> obtenerBitacora() {
    this.bitacoraRespuesta = BitacoraDAO.obtenerBitacora();
    ObservableList<Bitacora> bitacoraObservableList = FXCollections.observableArrayList();

    if (this.bitacoraRespuesta.getCodigoRespuesta() == Constantes.OPERACION_EXITOSA) {
      bitacoraObservableList.addAll(this.bitacoraRespuesta.getBitacoraLista());
    }
    return bitacoraObservableList;
  }

    @FXML
    private void clicVolver(ActionEvent event) {
        Stage stage = (Stage) tablaBitacora.getScene().getWindow();
        stage.close();
    }


}
