package inventarios.pojo;

import java.util.ArrayList;

public class BitacoraRespuesta {
    private int codigoRespuesta;
    private ArrayList<Bitacora> bitacoraLista;

    public BitacoraRespuesta() {
    }

    public BitacoraRespuesta(int codigoRespuesta, ArrayList<Bitacora> bitacoraLista) {
        this.codigoRespuesta = codigoRespuesta;
        this.bitacoraLista = bitacoraLista;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public ArrayList<Bitacora> getBitacoraLista() {
        return bitacoraLista;
    }

    public void setBitacoraLista(ArrayList<Bitacora> bitacoraLista) {
        this.bitacoraLista = bitacoraLista;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
}
