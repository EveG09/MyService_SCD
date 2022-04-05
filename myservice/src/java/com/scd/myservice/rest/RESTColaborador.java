/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scd.myservice.rest;

import com.google.gson.Gson;
import com.scd.myservice.controller.ControllerColaborador;
import com.scd.myservice.controller.ControllerLogin;
import com.scd.myservice.model.Colaborador;
import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author eveli
 */

@Path("colaborador")
public class RESTColaborador {
    
    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("colaborador") @DefaultValue("{}") String jsonColaborador, @FormParam("token") String token) {
        String out = null;
        ControllerColaborador cc = new ControllerColaborador();
        ControllerLogin cl = new ControllerLogin();
        Colaborador colaborador = null;
        try {
            if (!token.equals("") && cl.validateTokenAdmin(token)) {
                if (jsonColaborador == null || jsonColaborador.equals("{}")) {
                    out = "{\"error\":\"¡No se recibieron datos del cliente para guardar!\"}";
                } else {
                    colaborador = new Gson().fromJson(jsonColaborador, Colaborador.class);
                    if (colaborador.getFoto() == null) {
                        colaborador.setFoto("");
                    
                    } if (colaborador.getRutaFoto() == null) {
                        colaborador.setRutaFoto("");
                    
                    } if (colaborador.getId() == 0) {
                        cc.insert(colaborador);
                    
                    } else {
                        //cc.update(colaborador);
                    }
                    out = new Gson().toJson(colaborador);
                }

            } else {
                out = "{\"error\":\"¡Acceso no autorizado!\"}";
            }
     
        } catch (Exception e) {
            e.printStackTrace();
            // Devolvemos una descripcion del Error:
            out = "{\"error\":\"¡No se ha seleccionado ningún registro de cliente!\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro, @QueryParam("token") String token) {
        String out = null;
        ControllerColaborador cc = new ControllerColaborador();
        ControllerLogin cl = new ControllerLogin();
        List<Colaborador> colaboradores = null;
        Gson gson = new Gson();

        try {
            if (!token.equals("") && cl.validateTokenAdmin(token)) {
                colaboradores = cc.getAll(filtro);
                out = gson.toJson(colaboradores);
            
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
