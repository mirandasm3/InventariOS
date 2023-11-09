/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventarios.pojo;

public class SoftwareRespuesta {
    private int codigoRespuesta;
    private Software softwareRespuesta;

    public SoftwareRespuesta() {
    }

    public SoftwareRespuesta(int codigoRespuesta, Software softwareRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
        this.softwareRespuesta = softwareRespuesta;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public Software getSoftwareRespuesta() {
        return softwareRespuesta;
    }

    public void setSoftwareRespuesta(Software softwareRespuesta) {
        this.softwareRespuesta = softwareRespuesta;
    }
    
}
