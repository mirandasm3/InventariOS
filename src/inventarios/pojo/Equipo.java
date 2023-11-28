/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventarios.pojo;

import java.util.List;


public class Equipo {
    private int idEquipoComputo;
    private String identificador;
    private String procesador;
    private String memoriaRAM;
    private int memoriaRAMCantidad;
    private String tarjetaGrafica;
    private String tipoAlmacenamiento;
    private String espacioAlmacenamiento;
    private String ubicacionFisica;
    private String sistemaOperativo;
    private int nombreCentroComputo;
    private List<Software> softwareInstalado;

    public Equipo() {
    }

    public Equipo(int idEquipoComputo, String identificador, String procesador, String memoriaRAM, int memoriaRAMCantidad, String tarjetaGrafica, String tipoAlmacenamiento, String espacioAlmacenamiento, String ubicacionFisica, String sistemaOperativo, int nombreCentroComputo) {
        this.idEquipoComputo = idEquipoComputo;
        this.identificador = identificador;
        this.procesador = procesador;
        this.memoriaRAM = memoriaRAM;
        this.memoriaRAMCantidad = memoriaRAMCantidad;
        this.tarjetaGrafica = tarjetaGrafica;
        this.tipoAlmacenamiento = tipoAlmacenamiento;
        this.espacioAlmacenamiento = espacioAlmacenamiento;
        this.ubicacionFisica = ubicacionFisica;
        this.sistemaOperativo = sistemaOperativo;
        this.nombreCentroComputo = nombreCentroComputo;
    }

    public int getIdEquipoComputo() {
        return idEquipoComputo;
    }

    public void setIdEquipoComputo(int idEquipoComputo) {
        this.idEquipoComputo = idEquipoComputo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public String getMemoriaRAM() {
        return memoriaRAM;
    }

    public void setMemoriaRAM(String memoriaRAM) {
        this.memoriaRAM = memoriaRAM;
    }

    public int getMemoriaRAMCantidad() {
        return memoriaRAMCantidad;
    }

    public void setMemoriaRAMCantidad(int memoriaRAMCantidad) {
        this.memoriaRAMCantidad = memoriaRAMCantidad;
    }

    public String getTarjetaGrafica() {
        return tarjetaGrafica;
    }

    public void setTarjetaGrafica(String tarjetaGrafica) {
        this.tarjetaGrafica = tarjetaGrafica;
    }

    public String getTipoAlmacenamiento() {
        return tipoAlmacenamiento;
    }

    public void setTipoAlmacenamiento(String tipoAlmacenamiento) {
        this.tipoAlmacenamiento = tipoAlmacenamiento;
    }

    public String getEspacioAlmacenamiento() {
        return espacioAlmacenamiento;
    }

    public void setEspacioAlmacenamiento(String espacioAlmacenamiento) {
        this.espacioAlmacenamiento = espacioAlmacenamiento;
    }

    public String getUbicacionFisica() {
        return ubicacionFisica;
    }

    public void setUbicacionFisica(String ubicacionFisica) {
        this.ubicacionFisica = ubicacionFisica;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public int getNombreCentroComputo() {
        return nombreCentroComputo;
    }

    public void setNombreCentroComputo(int nombreCentroComputo) {
        this.nombreCentroComputo = nombreCentroComputo;
    }

    @Override
    public String toString() {
        return "Equipo{" + "idEquipoComputo=" + idEquipoComputo + ", identificador=" + identificador + ", procesador=" + procesador + ", memoriaRAM=" + memoriaRAM + ", memoriaRAMCantidad=" + memoriaRAMCantidad + ", tarjetaGrafica=" + tarjetaGrafica + ", tipoAlmacenamiento=" + tipoAlmacenamiento + ", espacioAlmacenamiento=" + espacioAlmacenamiento + ", ubicacionFisica=" + ubicacionFisica + ", sistemaOperativo=" + sistemaOperativo + ", nombreCentroComputo=" + nombreCentroComputo + '}';
    }
}
