/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scd.myservice.rest;

import com.google.gson.Gson;
import com.scd.myservice.controller.ControllerLogin;
import com.scd.myservice.controller.ControllerReparacion;
import com.scd.myservice.model.Reparacion;
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

@Path("reparacion")
public class RESTReparacion {
    
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro, @QueryParam("token") String token) {
        ControllerReparacion cr = new ControllerReparacion();
        ControllerLogin cl = new ControllerLogin();
        List<Reparacion> reparacion = null;
        String out = null;

        try {
            if (!token.equals("") && cl.validateTokenAdmin(token)) {
                reparacion = cr.getAll(filtro);
                out = new Gson().toJson(reparacion);
            
            } else if (!token.equals("") && cl.validateTokenColaborador(token)) {
                reparacion = cr.getAll(filtro);
                out = new Gson().toJson(reparacion);
                
            } else if (!token.equals("") && cl.validateTokenCliente(token)) {
                reparacion = cr.getAll(filtro);
                out = new Gson().toJson(reparacion);
                
            } else {
                out = "{\"error\":\"¡Acceso no autorizado!\"}";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"¡Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador!\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("idReparacion") @DefaultValue("0") int id,
            @FormParam("nombre") @DefaultValue("") String nombre,
            @FormParam("costo") @DefaultValue("0") double costo,
            @FormParam("tipoReparacion") @DefaultValue("") String tipoReparacion,
            @FormParam("descripcion") @DefaultValue("") String descripcion, 
            @FormParam("token") String token) {
        
        // Creamos un objeto de tipo ControllerReparación:
        ControllerReparacion cr = new ControllerReparacion();
        ControllerLogin cl = new ControllerLogin();
        String out = null;

        //Creamos un objeto de tipo Reparación:
        Reparacion reparacion = new Reparacion();

        try {
            if (!token.equals("") && cl.validateTokenAdmin(token)) {
                //Llenamos las propiedades del metodo Reparacion
                reparacion.setId(id);
                reparacion.setNombre(nombre);
                reparacion.setCosto(costo);
                reparacion.setTipoReparación(tipoReparacion);
                reparacion.setDescripcion(descripcion);

                // Evaluamos si hacemos un INSERT o un UPDATE con base en el ID del Tratamiento:
                if (reparacion.getId() == 0) {
                    cr.insert(reparacion);

                } else {
                    cr.update(reparacion);
                    // Devolvemos como respuesta todos los datos de Reparacion:
                    out = new Gson().toJson(reparacion);
                }
                
            } else if (!token.equals("") && cl.validateTokenColaborador(token)) {
                //Llenamos las propiedades del metodo Reparacion
                reparacion.setId(id);
                reparacion.setNombre(nombre);
                reparacion.setCosto(costo);
                reparacion.setTipoReparación(tipoReparacion);
                reparacion.setDescripcion(descripcion);

                // Evaluamos si hacemos un INSERT o un UPDATE con base en el ID del Tratamiento:
                if (reparacion.getId() == 0) {
                    cr.insert(reparacion);

                } else {
                    cr.update(reparacion);
                    // Devolvemos como respuesta todos los datos de Reparacion:
                    out = new Gson().toJson(reparacion);
                }
            
            } else {
                out = "{\"error\":\"¡Acceso no autorizado!\"}";
            }
            
        } catch (Exception e) {
            // Imprimimos el Error en la consola del servidor:
            e.printStackTrace();

            //Devolvemos una descripcion del Error:
            out = "{\"error\":\"¡No se ha seleccionado ningún registro de Reparación!\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@FormParam("idReparacion") @DefaultValue("0") int id, @FormParam("token") String token) {
        // Creamos un objeto de tipo ControllerReparacion:
        ControllerReparacion cr = new ControllerReparacion();
        ControllerLogin cl = new ControllerLogin();
        String out = null;

        try {
            if (!token.equals("") && cl.validateTokenAdmin(token)) {
                if (id >= 1) {
                    //Mantenemos el parámetro de ID al método de DELETE:
                    cr.delete(id);

                    // Devolvemos como respuesta un result de que el registro se ha eliminado correctamente:
                    out = "{\"result\":\"Movimiento realizado. Se elimino de manera correcta el registro.\"}";
                } else {
                    //Devolvemos una descripcion del Error:
                    out = "{\"error\":\"¡Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador!\"}";
                }
            
            } else if (!token.equals("") && cl.validateTokenColaborador(token)) {
                if (id >= 1) {
                    //Mantenemos el parámetro de ID al método de DELETE:
                    cr.delete(id);

                    // Devolvemos como respuesta un result de que el registro se ha eliminado correctamente:
                    out = "{\"result\":\"Movimiento realizado. Se elimino de manera correcta el registro.\"}";
                } else {
                    //Devolvemos una descripcion del Error:
                    out = "{\"error\":\"¡Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador!\"}";
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
}
