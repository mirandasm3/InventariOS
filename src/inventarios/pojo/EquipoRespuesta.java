/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventarios.pojo;

import java.util.ArrayList;

public class EquipoRespuesta {
    private int codigoRespuesta;
    private ArrayList<Equipo> equipoLista;

    public EquipoRespuesta() {
    }

    public EquipoRespuesta(int codigoRespuesta, ArrayList<Equipo> equipoLista) {
        this.codigoRespuesta = codigoRespuesta;
        this.equipoLista = equipoLista;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Equipo> getEquipoLista() {
        return equipoLista;
    }

    public void setEquipoLista(ArrayList<Equipo> equipoLista) {
        this.equipoLista = equipoLista;
    }
    
    
}
