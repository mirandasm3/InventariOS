
package inventarios.pojo;

import java.util.List;

public class Software {
    private int idSoftware;
    private String nombre;
    private String version;
    private String editor;
    private String tamaño;
    private String fechaInstalacion;
    private String identificadorEquipo;
    private String identificadorCC;
    private int idEquipo;

  public List<Equipo> getEquiposInstalados() {
    return equiposInstalados;
  }

  public void setEquiposInstalados(List<Equipo> equiposInstalados) {
    this.equiposInstalados = equiposInstalados;
  }
    private List<Equipo> equiposInstalados;
    
     public Software() {
    }

    public Software(String nombre, String editor, String tamaño) {
        this.nombre = nombre;
        this.editor = editor;
        this.tamaño = tamaño;
    }
     
    

    public Software(int idSoftware, String nombre, String version, String editor, String tamaño, String fechaInstalacion, String identificadorEquipo, String identificadorCC, int idEquipo) {
        this.idSoftware = idSoftware;
        this.nombre = nombre;
        this.version = version;
        this.editor = editor;
        this.tamaño = tamaño;
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

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
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
