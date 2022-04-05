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
public class Servicio {
    
    private int id;
    private String fecha;
    private Cita reservacion;
    private Colaborador colaborador;

    public Servicio() {}

    public Servicio(String fecha, Cita reservacion, Colaborador colaborador) {
        this.fecha = fecha;
        this.reservacion = reservacion;
        this.colaborador = colaborador;
    }

    public Servicio(int id, String fecha, Cita reservacion, Colaborador empleado) {
        this.id = id;
        this.fecha = fecha;
        this.reservacion = reservacion;
        this.colaborador = empleado;
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
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the reservacion
     */
    public Cita getReservacion() {
        return reservacion;
    }

    /**
     * @param reservacion the reservacion to set
     */
    public void setReservacion(Cita reservacion) {
        this.reservacion = reservacion;
    }

    /**
     * @return the colaborador
     */
    public Colaborador getColaborador() {
        return colaborador;
    }

    /**
     * @param colaborador the colaborador to set
     */
    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }
}
