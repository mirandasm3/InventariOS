/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventarios.pojo;

public class Software {
    private int idSoftware;
    private String nombre;
    private String version;
    private String editor;
    private String tamano;
    private String fechaInstalacion;
    private String identificadorEquipo;
    private String identificadorCC;
    private int idEquipo;
    
     public Software() {
    }

    public Software(int idSoftware, String nombre, String version, String editor, String tamano, String fechaInstalacion, String identificadorEquipo, String identificadorCC, int idEquipo) {
        this.idSoftware = idSoftware;
        this.nombre = nombre;
        this.version = version;
        this.editor = editor;
        this.tamano = tamano;
        this.fechaInstalacion = fechaInstalacion;
        this.identificadorEquipo = identificadorEquipo;
        this.identificadorCC = identificadorCC;
        this.idEquipo = idEquipo;
    }

    public int getIdSoftware() {
        return idSoftware;
    }

    public void setIdSoftware(int idSoftware) {
        this.idSoftware = idSoftware;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getFechaInstalacion() {
        return fechaInstalacion;
    }

    public void setFechaInstalacion(String fechaInstalacion) {
        this.fechaInstalacion = fechaInstalacion;
    }

    public String getIdentificadorEquipo() {
        return identificadorEquipo;
    }

    public void setIdentificadorEquipo(String identificadorEquipo) {
        this.identificadorEquipo = identificadorEquipo;
    }

    public String getIdentificadorCC() {
        return identificadorCC;
    }

    public void setIdentificadorCC(String identificadorCC) {
        this.identificadorCC = identificadorCC;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }
    
}
