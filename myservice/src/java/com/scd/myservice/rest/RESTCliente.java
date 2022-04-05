/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scd.myservice.rest;

import com.google.gson.Gson;
import com.scd.myservice.controller.ControllerCliente;
import com.scd.myservice.controller.ControllerLogin;
import com.scd.myservice.model.Cliente;
import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author eveli
 */
@Path("cliente")
public class RESTCliente {
    
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro, @QueryParam("token") String token) {
        String out = null;
        ControllerCliente cc = new ControllerCliente();
        ControllerLogin cl = new ControllerLogin();
        List<Cliente> clientes = null;
        Gson gson = new Gson();

        try {
            if (!token.equals("") && cl.validateTokenAdmin(token)) {
                clientes = cc.getAll(filtro);
                out = gson.toJson(clientes);
                
            } else if (!token.equals("") && cl.validateTokenColaborador(token)) {
                clientes = cc.getAll(filtro);
                out = gson.toJson(clientes);
            
            } else {
                out = "{\"error\":\"¡Acceso no autorizado!\"}";
            }

        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"¡Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador!\"}";

        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("getAllByNombreCliente")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllByNombreCliente(@QueryParam("nombre") String nombre, 
                                @QueryParam("token") String token) {
        String out = null;
        ControllerCliente cc = new ControllerCliente();
        ControllerLogin cl = new ControllerLogin();
        List<Cliente> clientes = null;
        Gson gson = new Gson();

        try {
            if (!token.equals("") && cl.validateTokenCliente(token)) {
                clientes = cc.getAllByNombreCliente(nombre);
                out = gson.toJson(clientes);
                
            } else {
                out = "{\"error\":\"¡Acceso no autorizado!\"}";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"¡Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador!\"}";

        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}