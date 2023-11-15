/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventarios.util;

import inventarios.pojo.Software;

public class SingletonSoftware {
    public static SingletonSoftware instance;
    public Software softwareActivo;
    public boolean esEdicion;

    public SingletonSoftware() {
    }


    public static SingletonSoftware getInstance() {
        if (instance == null) {
            instance = new SingletonSoftware();
        }
        return instance;
    }
    public SingletonSoftware(Software softwareActivo) {
        this.softwareActivo = softwareActivo;
    }
    
}
