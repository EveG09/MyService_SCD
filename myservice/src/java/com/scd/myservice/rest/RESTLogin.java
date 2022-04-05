/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scd.myservice.rest;

import com.google.gson.Gson;
import com.scd.myservice.controller.ControllerLogin;
import com.scd.myservice.model.Cliente;
import com.scd.myservice.model.Colaborador;
import com.scd.myservice.model.Usuario;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author eveli
 */

@Path("login")
public class RESTLogin {
    
    // Servicio REST para la validacion del logueo del usuario "Administrador":
    @Path("validateAdmin")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginAdmin(@FormParam("nombreUsuario") String nombreUsuario, 
                          @FormParam("contrasenia") String contrasenia){
             
        String out = null;
        ControllerLogin cl = new ControllerLogin();
        Colaborador co = null;
        
        try {
            co = cl.loginAdmin(nombreUsuario, contrasenia);
            
            // Revisamos que tengamos una instancia de tipo empleado:
            if (co != null) {
                out= new Gson().toJson(co);
            
            } else{
                 out = "{\"error\":\"Datos de acceso incorectos. Intenta nuevamente o llama al Administrador\"}";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"¡Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador!\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
        
    // Servicios REST para la validacion del logueo del usuario "Colaborador":
    @Path("validateColaborador")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginColaborador(@FormParam("nombreUsuario") String nombreUsuario, 
                          @FormParam("contrasenia") String contrasenia){
             
        String out = null;
        ControllerLogin cl = new ControllerLogin();
        Colaborador co = null;
        
        try {
            co = cl.loginColaborador(nombreUsuario, contrasenia);
            
            // Revisamos que tengamos una instancia de tipo empleado:
            if (co != null) {
                out= new Gson().toJson(co);
            
            } else{
                 out = "{\"error\":\"Datos de acceso incorectos. Intenta nuevamente o llama al Administrador\"}";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"¡Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador!\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
        
    // Servicio REST para la validacion del logueo del usuario "Cliente":
    @Path("validateCliente")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginCliente(@FormParam("nombreUsuario") String nombreUsuario, 
                          @FormParam("contrasenia") String contrasenia){
             
        String out = null;
        ControllerLogin cl = new ControllerLogin();
        Cliente c = null;
        
        try {
            c = cl.loginCliente(nombreUsuario, contrasenia);
            
            // Revisamos que tengamos una instancia de tipo empleado:
            if (c != null) {
                out= new Gson().toJson(c);
            
            } else{
                 out = "{\"error\":\"Datos de acceso incorectos. Intenta nuevamente o llama al Administrador\"}";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"¡Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador!\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    // Servicio REST para cerrar sesión de manera correcta, llevando a cabo la eliminacion del token generado para el usuario logueado
    @Path("out")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response outAdmin(@FormParam("id") @DefaultValue("0") String idUsuario) {
        String out = null;
        ControllerLogin cl = new ControllerLogin();
        Usuario u = new Usuario();
        
        try {
            u.setId(Integer.parseInt(idUsuario));
            cl.deleteToken(u);
            out = "{\"result\":\"OK\"}";
            
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"¡Se generó un error inesperado en el cierre de sesión!\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
