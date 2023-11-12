
package inventarios.pojo;

/**
 *
 * @author raudel
 */
public class CentroComputo {
    
    String clave;
    int numero;
    
    public CentroComputo(){
        
    }
    
    public CentroComputo(String clave, int numero){
        this.clave = clave;
        this.numero = numero;
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
