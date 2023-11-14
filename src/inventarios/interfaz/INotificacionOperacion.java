/*
 * Autor: Rodrigo Aguilar López
 * Fecha de creación: 24/05/2023
 * Descripción: Interface para notificar cambios de estados en los Cuerpos Academicos
 */
package inventarios.interfaz;

public interface INotificacionOperacion {
    public void notificarOperacionGuardar();
    
    public void notificarOperacionActualizar();
}