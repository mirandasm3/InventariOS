/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventarios.pojo;

/**
 *
 * @author LENOVO
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
