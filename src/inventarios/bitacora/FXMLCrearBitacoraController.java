package inventarios.bitacora;

import inventarios.dao.BitacoraDAO;
import inventarios.pojo.Bitacora;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class FXMLCrearBitacoraController {
    public DatePicker dpFecha;
    public TextArea taDescripcion;

    public void registrar(ActionEvent actionEvent) {
        Bitacora bitacora = new Bitacora();
        bitacora.setFecha(dpFecha.getValue());
        bitacora.setDescripcion(taDescripcion.getText());
        boolean respuesta = BitacoraDAO.registrarBitacora(bitacora);
        if (respuesta) {
            System.out.println("Registro exitoso");
        } else {
            System.out.println("Registro fallido");
        }
        cleanFields();
    }

    public void regresar(ActionEvent actionEvent) {
        Stage stage = (Stage) dpFecha.getScene().getWindow();
        stage.close();
    }

    private void cleanFields() {
        dpFecha.setValue(null);
        taDescripcion.setText("");
    }
}