
package inventarios.util;

import javafx.scene.control.Alert;

/**
 *
 * @author diana
 */
public class Utilidades {
    public static void mostrarAlertaSimple(String titulo, String mensaje, Alert.AlertType tipo){
        Alert alertaSImple = new Alert(tipo);
        alertaSImple.setTitle(titulo);
        alertaSImple.setContentText(mensaje);
        alertaSImple.setHeaderText(null);
        alertaSImple.showAndWait();
    }
}
