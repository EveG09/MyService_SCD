/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scd.myservice.rest;

import com.google.gson.Gson;
import com.scd.myservice.controller.ControllerLogin;
import com.scd.myservice.controller.ControllerMaterial;
import com.scd.myservice.model.Material;
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

@Path("material")
public class RESTMaterial {
    
    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("idMaterial") @DefaultValue("0") int id,
            @FormParam("nombre") @DefaultValue("") String nombre,
            @FormParam("marca") @DefaultValue("") String marca,
            @FormParam("precio") @DefaultValue("0.0") double precio,
            @FormParam("token") String token) {

        ControllerMaterial cm = new ControllerMaterial();
        ControllerLogin cl = new ControllerLogin();
        String out = null;
        Material m = new Material();

        try {
            if (!token.equals("") && cl.validateTokenAdmin(token)) {
                m.setId(id);
                m.setNombre(nombre);
                m.setMarca(marca);
                m.setPrecio(precio);

                if (m.getId() == 0) {
                    cm.insert(m);

                } else {
                    cm.update(m);
                }
                out = new Gson().toJson(m);
            
            } else if (!token.equals("") && cl.validateTokenColaborador(token)) {
                m.setId(id);
                m.setNombre(nombre);
                m.setMarca(marca);
                m.setPrecio(precio);

                if (m.getId() == 0) {
                    cm.insert(m);

                } else {
                    cm.update(m);
                }
                out = new Gson().toJson(m);

            } else {
                //Devolvemos una descripcion del Error:
                out = "{\"error\":\"¡Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador!\"}";
            }

        } catch (Exception e) {
            // Imprimimos el Error en la consola del servidor:
            e.printStackTrace();
            out = "{\"error\":\"¡No se ha seleccionado ningún registro de material!\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@FormParam("idMaterial") @DefaultValue("0") int id, @FormParam("token") String token) throws Exception {
        ControllerMaterial cm = new ControllerMaterial();
        ControllerLogin cl = new ControllerLogin();
        String out = null;

        try {
            if (!token.equals("") && cl.validateTokenAdmin(token)) {
                if (id >= 1) {
                    // Mandamos el parámetro de ID al método de DELETE:
                    cm.delete(id);

                    // Devolvemos como respuesta un result de que el registro se ha eliminado correctamente:
                  out = "{\"result\":\"Movimiento realizado. Se eliminó de manera correcta el registro.\"}";
                }
                
                else{ 
                    //Devolvemos una descripcion del Error:
                    out = "{\"error\":\"Algo salió mal. Intenta nuevamente.\"}";
                }
                
            } else if (!token.equals("") && cl.validateTokenColaborador(token)) {
                if (id >= 1) {
                    // Mandamos el parámetro de ID al método de DELETE:
                    cm.delete(id);

                    // Devolvemos como respuesta un result de que el registro se ha eliminado correctamente:
                  out = "{\"result\":\"Movimiento realizado. Se eliminó de manera correcta el registro.\"}";
                }
                else{ 
                    //Devolvemos una descripcion del Error:
                    out = "{\"error\":\"Algo salió mal. Intenta nuevamente.\"}";
                }

            } else {
                //Devolvemos una descripcion del Error:
                 out = "{\"error\":\"¡Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador!\"}";
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
        
        ControllerMaterial cm = new ControllerMaterial();
        ControllerLogin cl = new ControllerLogin();
        List<Material> materiales = null;
        String out = null;
        
        try {
            if (!token.equals("") && cl.validateTokenAdmin(token)) {
                materiales = cm.getAll(filtro);
                out = new Gson().toJson(materiales);
            
            } else if (!token.equals("") && cl.validateTokenColaborador(token)) {
                materiales = cm.getAll(filtro);
                out = new Gson().toJson(materiales);
            }
        
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"¡Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador!\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
