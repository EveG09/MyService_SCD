/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scd.myservice.model;

/**
 *
 * @author eveli
 */
public class Reparacion {
    
    private int idReparacion;
    private String nombre;
    private double costo;
    private String tipoReparacion;
    private String descripcion;
    private int estatus;
    
    public Reparacion() {}

    public Reparacion(int id, String nombre, double costo, String tipoReparacion, String descripcion, int estatus) {
        this.idReparacion = id;
        this.nombre = nombre;
        this.costo = costo;
        this.tipoReparacion = tipoReparacion;
        this.descripcion = descripcion;
        this.estatus = estatus;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return idReparacion;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.idReparacion = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the costo
     */
    public double getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(double costo) {
        this.costo = costo;
    }

    /**
     * @return the tipoReparación
     */
    public String getTipoReparación() {
        return tipoReparacion;
    }

    /**
     * @param tipoReparación the tipoReparación to set
     */
    public void setTipoReparación(String tipoReparación) {
        this.tipoReparacion = tipoReparación;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the estatus
     */
    public int getEstatus() {
        return estatus;
    }

    /**
     * @param estatus the estatus to set
     */
    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }
}
