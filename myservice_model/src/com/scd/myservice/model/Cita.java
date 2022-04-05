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
public class Cita {

    private int idCita;
    private String fechaCita;
    private int estatus;
    private Cliente cliente;
    private Reparacion reparacion;
    private Horario horario;

    public Cita() {}
    
    public Cita(int id, String fecha, int estatus, Cliente cliente, Reparacion reparacion, Horario horario) {
        this.idCita = id;
        this.fechaCita = fecha;
        this.estatus = estatus;
        this.cliente = cliente;
        this.reparacion = reparacion;
        this.horario = horario;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return idCita;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.idCita = id;
    }

    /**
     * @return the fechaCita
     */
    public String getFechaCita() {
        return fechaCita;
    }

    /**
     * @param fechaCita the fechaCita to set
     */
    public void setFechaCita(String fechaCita) {
        this.fechaCita = fechaCita;
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

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the reparacion
     */
    public Reparacion getReparacion() {
        return reparacion;
    }

    /**
     * @param reparacion the reparacion to set
     */
    public void setReparacion(Reparacion reparacion) {
        this.reparacion = reparacion;
    }

    /**
     * @return the horario
     */
    public Horario getHorario() {
        return horario;
    }

    /**
     * @param horario the horario to set
     */
    public void setHorario(Horario horario) {
        this.horario = horario;
    }
}
