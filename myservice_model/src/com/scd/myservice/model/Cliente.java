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
public class Cliente {
    
    private int id;
    private String numeroUnico;
    private String correo;
    private int estatus;
    private Persona persona;
    private Usuario usuario;

    public Cliente() {}

    public Cliente(String numeroUnico, String correo, int estatus, Persona persona, Usuario usuario) {
        this.numeroUnico = numeroUnico;
        this.correo = correo;
        this.estatus = estatus;
        this.persona = persona;
        this.usuario = usuario;
    }

    public Cliente(int id, String numeroUnico, String correo, int estatus, Persona persona, Usuario usuario) {
        this.id = id;
        this.numeroUnico = numeroUnico;
        this.correo = correo;
        this.estatus = estatus;
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
     * @return the numeroUnico
     */
    public String getNumeroUnico() {
        return numeroUnico;
    }

    /**
     * @param numeroUnico the numeroUnico to set
     */
    public void setNumeroUnico(String numeroUnico) {
        this.numeroUnico = numeroUnico;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
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
