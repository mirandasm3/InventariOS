
package inventarios.pojo;

import java.util.ArrayList;

public class SoftwareListaRespuesta {
    private int codigoRespuesta;
    private ArrayList<Software> softwareLista;

    public SoftwareListaRespuesta() {
    }

    public SoftwareListaRespuesta(int codigoRespuesta, ArrayList<Software> softwareLista) {
        this.codigoRespuesta = codigoRespuesta;
        this.softwareLista = softwareLista;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Software> getSoftwareLista() {
        return softwareLista;
    }

    public void setSoftwareLista(ArrayList<Software> softwareLista) {
        this.softwareLista = softwareLista;
    }
    
    
}
