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
public class ServicioMaterial {

    private int id;
    private Material material;
    private Servicio servicio;

    public ServicioMaterial() {}

    public ServicioMaterial(Material material, Servicio servicio) {
        this.material = material;
        this.servicio = servicio;
    }

    public ServicioMaterial(int id, Material material, Servicio servicio) {
        this.id = id;
        this.material = material;
        this.servicio = servicio;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * @param material the material to set
     */
    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * @return the servicio
     */
    public Servicio getServicio() {
        return servicio;
    }

    /**
     * @param servicio the servicio to set
     */
    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
}
