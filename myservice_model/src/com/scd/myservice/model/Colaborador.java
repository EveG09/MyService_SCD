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
public class Colaborador {
    
    private int id;
    private String numeroColaborador;
    private String puesto;
    private int estatus;
    private String foto;
    private String rutaFoto;
    private String descripcion;
    private Persona persona;
    private Usuario usuario;

    public Colaborador() {}

    public Colaborador(String numeroColaborador, String puesto, int estatus, String foto, String rutaFoto, String descripcion, Persona persona, Usuario usuario) {
        this.numeroColaborador = numeroColaborador;
        this.puesto = puesto;
        this.estatus = estatus;
        this.foto = foto;
        this.rutaFoto = rutaFoto;
        this.descripcion = descripcion;
        this.persona = persona;
        this.usuario = usuario;
    }

    public Colaborador(int id, String numeroColaborador, String puesto, int estatus, String foto, String rutaFoto, String descripcion, Persona persona, Usuario usuario) {
        this.id = id;
        this.numeroColaborador = numeroColaborador;
        this.puesto = puesto;
        this.estatus = estatus;
        this.foto = foto;
        this.rutaFoto = rutaFoto;
        this.descripcion = descripcion;
        this.persona = persona;
        this.usuario = usuario;
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
     * @return the numeroColaborador
     */
    public String getNumeroColaborador() {
        return numeroColaborador;
    }

    /**
     * @param numeroColaborador the numeroColaborador to set
     */
    public void setNumeroColaborador(String numeroColaborador) {
        this.numeroColaborador = numeroColaborador;
    }

    /**
     * @return the puesto
     */
    public String getPuesto() {
        return puesto;
    }

    /**
     * @param puesto the puesto to set
     */
    public void setPuesto(String puesto) {
        this.puesto = puesto;
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
     * @return the foto
     */
    public String getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     * @return the rutaFoto
     */
    public String getRutaFoto() {
        return rutaFoto;
    }

    /**
     * @param rutaFoto the rutaFoto to set
     */
    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
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
     * @return the persona
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
