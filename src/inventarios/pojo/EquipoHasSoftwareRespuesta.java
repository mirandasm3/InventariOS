/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventarios.pojo;


public class EquipoHasSoftwareRespuesta {
    private int codigoRespuesta;
    private int idSoftware;
    private int idEquipo;


    public EquipoHasSoftwareRespuesta() {
    }

    public EquipoHasSoftwareRespuesta(int codigoRespuesta, int idSoftware, int idEquipo) {
        this.codigoRespuesta = codigoRespuesta;
        this.idSoftware = idSoftware;
        this.idEquipo = idEquipo;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public int getIdSoftware() {
        return idSoftware;
    }

    public void setIdSoftware(int idSoftware) {
        this.idSoftware = idSoftware;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }
    
}
