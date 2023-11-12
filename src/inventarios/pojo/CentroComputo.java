
package inventarios.pojo;

/**
 *
 * @author raudel
 */
public class CentroComputo {
    
    String clave;
    int numero;
    int idCC;
    
    public CentroComputo(){
        
    }

    public CentroComputo(String clave, int numero) {
        this.clave = clave;
        this.numero = numero;
    }
    
    public CentroComputo(String clave, int numero, int idCC){
        this.clave = clave;
        this.numero = numero;
        this.idCC = idCC;
    }

    public int getIdCC() {
        return idCC;
    }

    public void setIdCC(int idCC) {
        this.idCC = idCC;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
}
