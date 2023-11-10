/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventarios.pojo;

/**
 *
 * @author LENOVO
 */
public class Administrador {


    
    String nombre;
    int numeroPersonal;
    String password;
    String contacto;
    
    public Administrador(){
        
    }
    
    public Administrador(String nombre, int numeroPersonal, String password, String contacto){
        this.nombre = nombre;
        this.numeroPersonal = numeroPersonal;
        this.password = password;
        this.contacto = contacto;
    }
    
        public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroPersonal() {
        return numeroPersonal;
    }

    public void setNumeroPersonal(int numeroPersonal) {
        this.numeroPersonal = numeroPersonal;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    
}
