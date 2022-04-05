/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scd.myservice.rest;

import com.google.gson.Gson;
import com.scd.myservice.controller.ControllerCita;
import com.scd.myservice.controller.ControllerLogin;
import com.scd.myservice.model.Cita;
import com.scd.myservice.model.Cliente;
import com.scd.myservice.model.Horario;
import com.scd.myservice.model.Reparacion;
import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("cita")
public class RESTCita {
    
    @Path("insert")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@QueryParam("estatus") int estatus,
                           @QueryParam("idCliente") int idCliente,            
                           @QueryParam("idReparacion") int idReparacion,
                           @QueryParam("fechaCita") String fechaCita,
                           @QueryParam("idHorario") int idHorario,
                           @QueryParam("token") String token) {
        
        ControllerCita cc = new ControllerCita();
        ControllerLogin cl = new ControllerLogin();
        Cliente c = new Cliente();
        Reparacion r = new Reparacion();
        Horario h = new Horario();
        Cita ci = new Cita();
        String out = null;
        
        System.out.println(estatus + " " + idCliente + " " + idReparacion + " " + fechaCita + " " + idHorario + " " + token);
        try {
            if (!token.equals("") && cl.validateTokenCliente(token)) {
                c.setId(idCliente);
                r.setId(idReparacion);
                h.setId(idHorario);
                
                // Llenamos las propiedades de la reservacion:
                ci.setEstatus(estatus);
                ci.setCliente(c);
                ci.setReparacion(r);
                ci.setFechaCita(fechaCita);
                ci.setHorario(h);
                
                cc.insert(ci);
                out = "{\"result\":\"¡Cita realizada con éxito!\"}";
                            
            } else {
                out = "{\"error\":\"¡Acceso no autorizado!\"}";
            }
        
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"¡Se produjo un error al realizar la cita. Intente más tarde!\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@QueryParam("idCita") @DefaultValue("0") int id, @QueryParam("token") String token) throws Exception {
        // Creamos un objeto de tipo ControllerProducto:
        ControllerCita cc = new ControllerCita();
        ControllerLogin cl = new ControllerLogin();
        String out = null;

        try {
            if (!token.equals("") && cl.validateTokenCliente(token)) {
                if (id >= 1) {
                    // Mandamos el parámetro de ID al método de DELETE:
                    cc.delete(id);

                    // Devolvemos como respuesta un result de que el registro se ha eliminado correctamente:
                    out = "{\"result\":\"Movimiento realizado. Se canceló de manera correcta el registro.\"}";
                } else {
                    //Devolvemos una descripcion del Error:
                    out = "{\"error\":\"¡Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador!\"}";
                }
            
            } else if (!token.equals("") && cl.validateTokenColaborador(token)) {
                if (id >= 1) {
                    // Mandamos el parámetro de ID al método de DELETE:
                    cc.delete(id);

                    // Devolvemos como respuesta un result de que el registro se ha eliminado correctamente:
                    out = "{\"result\":\"Movimiento realizado. Se canceló de manera correcta el registro.\"}";
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
    
    @Path("getAllHorarios")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHorarios(@QueryParam("fecha") String fecha,
            @QueryParam("idSala") int idSala,
            @QueryParam("token") String token) {

        ControllerCita cc = new ControllerCita();
        ControllerLogin cl = new ControllerLogin();
        String out = null;
        List<Horario> horarios = null;

        try {
            if (!token.equals("") && cl.validateTokenCliente(token)) {
                horarios = cc.getAllHorarios(fecha);
                out = new Gson().toJson(horarios);
                
            } else {
                out = "{\"error\":\"¡Acceso no autorizado!\"}";
            }

        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"¡Se produjo un error al cargar los horarios disponibles. Intente más tarde!\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro) {
        String out = null;
        ControllerCita cc = new ControllerCita();
        //ControllerLogin cl = new ControllerLogin();
        List<Cita> citas = null;
        Gson gson = new Gson();

        try {
           // if (!token.equals("") && cl.validateTokenColaborador(token)) {
                citas = cc.getAll(filtro);
                out = gson.toJson(citas);
//
//            } else {
//                out = "{\"error\":\"¡Acceso no autorizado!\"}";
//            }

        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"¡Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador!\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("getAllAttended")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAttended(@QueryParam("filtro") @DefaultValue("") String filtro) {
        String out = null;
        ControllerCita cc = new ControllerCita();
        //ControllerLogin cl = new ControllerLogin();
        List<Cita> citas = null;
        Gson gson = new Gson();

        try {
           // if (!token.equals("") && cl.validateTokenColaborador(token)) {
                citas = cc.getAllAtendidas(filtro);
                out = gson.toJson(citas);
//
//            } else {
//                out = "{\"error\":\"¡Acceso no autorizado!\"}";
//            }

        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"¡Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador!\"}";

        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("getAllCancelled")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCancelled(@QueryParam("filtro") @DefaultValue("") String filtro) {
        String out = null;
        ControllerCita cc = new ControllerCita();
        //ControllerLogin cl = new ControllerLogin();
        List<Cita> citas = null;
        Gson gson = new Gson();

        try {
           // if (!token.equals("") && cl.validateTokenColaborador(token)) {
                citas = cc.getAllCanceladas(filtro);
                out = gson.toJson(citas);
//
//            } else {
//                out = "{\"error\":\"¡Acceso no autorizado!\"}";
//            }

        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"¡Ocurrió un error inesperado. Intenta nuevamente o llama al Administrador!\"}";

        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
