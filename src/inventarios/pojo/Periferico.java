
package inventarios.pojo;

/**
 *
 * @author diana
 */
public class Periferico {
    private int idPeriferico;
    private int idCentroComputo;
    private String tipo;
    private String identificador;
    private String marca;
    private String estado;

    public Periferico() {
    }
    
    public Periferico(int idPeriferico, int idCentroComputo, String tipo, String identificador, String marca, String estado) {
        this.idPeriferico = idPeriferico;
        this.idCentroComputo = idCentroComputo;
        this.tipo = tipo;
        this.identificador = identificador;
        this.marca = marca;
        this.estado = estado;
    }
    
    public int getIdPeriferico() {
        return idPeriferico;
    }

    public void setIdPeriferico(int idPeriferico) {
        this.idPeriferico = idPeriferico;
    }

    public int getIdCentroComputo() {
        return idCentroComputo;
    }

    public void setIdCentroComputo(int idCentroComputo) {
        this.idCentroComputo = idCentroComputo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
