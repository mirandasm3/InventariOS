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
import inventarios.pojo.ResultadoOperacion;
import inventarios.util.Constantes;
import inventarios.util.Utilidades;
import java.sql.SQLException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
  private ObservableList<Bitacora> listaBitacoras = FXCollections.observableArrayList();
  
  
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
    Bitacora bitacoraSeleccionada = (Bitacora) tablaBitacora.getSelectionModel().getSelectedItem();
      if (bitacoraSeleccionada == null) {
          Utilidades.mostrarAlertaSimple("Falta selección", "Debe seleccionar un registro de la tabla.", Alert.AlertType.WARNING);
      } else {
          Alert alert = new Alert(AlertType.CONFIRMATION);
          alert.setTitle("Confirmación de eliminación");
          alert.setHeaderText("¿Desea eliminar la bitácora seleccionada");
          alert.setContentText("Esta acción no se puede deshacer.");

          Optional<ButtonType> result = alert.showAndWait();
          if (result.get() == ButtonType.OK) {

              BitacoraDAO bitacoraDao = new BitacoraDAO();
              ResultadoOperacion resultado;
              try {
                  boolean respuesta = BitacoraDAO.eliminarBitacora(bitacoraSeleccionada.getIdBitacora());
                      listaBitacoras.remove(bitacoraSeleccionada);
                      tablaBitacora.refresh();
               
              } catch (SQLException ex) {
                  Utilidades.mostrarAlertaSimple("Error", "No se pudo eliminar la bitácora.", Alert.AlertType.ERROR);
              }
          }else{
              alert.close();
          }
      }
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
