/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventarios.util;

import inventarios.pojo.Software;

public class singletonSoftware {
    public static singletonSoftware instance;
    public Software softwareActivo;
    public boolean esEdicion;

    public singletonSoftware() {
    }


    public static singletonSoftware getInstance() {
        if (instance == null) {
            instance = new singletonSoftware();
        }
        return instance;
    }
    public singletonSoftware(Software softwareActivo) {
        this.softwareActivo = softwareActivo;
    }
    
}
