/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventarios.pojo;

import java.time.LocalDate;

/**
 *
 * @author seren
 */
public class Bitacora {
  private LocalDate fecha;
  private String descripcion;
  private int idBitacora;
  private int idEquipoDeComputo;

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public String getDescripcion() {
    return descripcion;
  }

    public int getIdBitacora() {
        return idBitacora;
    }

  public int getIdEquipoDeComputo() {
    return idEquipoDeComputo;
  }

  public void setIdBitacora(int idBitacora) {
    this.idBitacora = idBitacora;
  }

  public void setIdEquipoDeComputo(int idEquipoDeComputo) {
    this.idEquipoDeComputo = idEquipoDeComputo;
  }
}