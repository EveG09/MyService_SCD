/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scd.myservice.model;

import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author eveli
 */
public class Usuario {

    private int id;
    private String nombreUsuario;
    private String contrasenia;
    private String token;
    private String rol;
    
    public Usuario() {}
    
    public Usuario(String nombreUsuario, String contrasenia, String token, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.token = token;
        this.rol = rol;
    }
    
    public Usuario(int id, String nombreUsuario, String contrasenia, String token, String rol) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.token = token;
        this.rol = rol;
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
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return the contrasenia
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @param contrasenia the contrasenia to set
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    public void setToken() {
        String u = this.nombreUsuario;
        String k = new Date().toString();
        String t = DigestUtils.sha256Hex(u + ";" + k);
        this.token = t;
    }

    /**
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(String rol) {
        this.rol = rol;
    }    
}
