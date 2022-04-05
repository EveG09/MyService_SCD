/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scd.myservice.rest;

import com.google.gson.Gson;
import com.scd.myservice.controller.ControllerHorario;
import com.scd.myservice.controller.ControllerLogin;
import com.scd.myservice.model.Horario;
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

@Path("horario")
public class RESTHorario {
    
    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("idHorario") @DefaultValue("0") int id,
            @FormParam("horaInicio") @DefaultValue("") String horaInicio,
            @FormParam("horaFin") @DefaultValue("") String horaFin,
            @FormParam("token") String token) {

        ControllerHorario cp = new ControllerHorario();
        ControllerLogin cl = new ControllerLogin();
        String out = null;
        Horario h = new Horario();

        try {
            if (!token.equals("") && cl.validateTokenAdmin(token)) {
                h.setId(id);
                h.setHoraInicio(horaInicio);
                h.setHoraFin(horaFin);

                if (h.getId() == 0) {
                    cp.insert(h);

                } else {
                    cp.update(h);
                }
                out = new Gson().toJson(h);
                
            } else if (!token.equals("") && cl.validateTokenColaborador(token)) {
                h.setId(id);
                h.setHoraInicio(horaInicio);
                h.setHoraFin(horaFin);

                if (h.getId() == 0) {
                    cp.insert(h);

                } else {
                    cp.update(h);
                }
                out = new Gson().toJson(h);
                
            } else {
                out = "{\"error\":\"¡Acceso no autorizado!\"}";
            }

        } catch (Exception e) {
            // Imprimimos el Error en la consola del servidor:
            e.printStackTrace();
            out = "{\"error\":\"¡No se ha seleccionado ningún registro de horario!\"}";
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@FormParam("idHorario") @DefaultValue("0") int id, @FormParam("token") String token) throws Exception {
        ControllerHorario cp = new ControllerHorario();
       ControllerLogin cl = new ControllerLogin();
        String out = null;

        try {
            if (!token.equals("") && cl.validateTokenAdmin(token)) {
                if (id > 0) {
                    // Mandamos el parámetro de ID al método de DELETE:
                    cp.delete(id);

                    // Devolvemos como respuesta un result de que el registro se ha eliminado correctamente:
                    out = "{\"result\":\"Movimiento realizado. Se eliminó de manera correcta el registro.\"}";
                    
                } else {
                    //Devolvemos una descripcion del Error:
                    out = "{\"error\":\"¡Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador!\"}";
                }
                
            } else if (!token.equals("") && cl.validateTokenColaborador(token)) {    
                if (id > 0) {
                    // Mandamos el parámetro de ID al método de DELETE:
                    cp.delete(id);

                    // Devolvemos como respuesta un result de que el registro se ha eliminado correctamente:
                    out = "{\"result\":\"Movimiento realizado. Se eliminó de manera correcta el registro.\"}";
                    
                } else {
                    //Devolvemos una descripcion del Error:
                    out = "{\"error\":\"Algo salió mal. Intenta nuevamente.\"}";
                }
                
            } else {
                out = "{\"error\":\"¡Acceso no autorizado!\"}";
            }

        } catch (Exception e) {
            // Imprimimos el Error en la consola del servidor:
            e.printStackTrace();
            
            //Devolvemos una descripcion del Error:
            out = "{\"error\":\"¡Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador!\"}";
        }        
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro, @QueryParam("token") String token) throws Exception {
        ControllerHorario ch = new ControllerHorario();
        ControllerLogin cl = new ControllerLogin();
        List<Horario> horarios = null;
        String out = null;
        
        try {
            if (!token.equals("") && cl.validateTokenAdmin(token)) {
                horarios = ch.getAll(filtro);
                out = new Gson().toJson(horarios);
            
            } else if (!token.equals("") && cl.validateTokenColaborador(token)) {
                horarios = ch.getAll(filtro);
                out = new Gson().toJson(horarios);
                
            } else if (!token.equals("") && cl.validateTokenCliente(token)) {
                horarios = ch.getAll(filtro);
                out = new Gson().toJson(horarios);
                
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
