/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scd.myservice.controller;

import com.scd.myservice.database.ConnectionMySQL;
import com.scd.myservice.model.Cliente;
import com.scd.myservice.model.Persona;
import com.scd.myservice.model.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eveli
 */
public class ControllerCliente {
    
    // Inserta un registro de cliente en la base de datos y devuelve el ID generado:
    public int insert(Cliente c) throws Exception {
        String sql = "{CALL insertarCliente(?, ?, ?, ?, ?, ?, ?, " +// Datos Persona
                                            "?, ?, ?," +//Datos Usuario
                                            "?, ?, ?," +//Datos Cliente
                                            "?, ?, ?, ?)}"; //Valores de Retorno

        //Aquí guardaremos los ID's que se generán: 
        int idPersonaGenerado = -1;
        int idUsuarioGenerado = -1;
        int idClienteGenerado = -1;
        
        String numClienteGenerado = "";

        // Declaramos un objeto de tipo Conexion:
        ConnectionMySQL connMySQL = new ConnectionMySQL();

        // Abrimos la conexión con MySQL:
        Connection conn = connMySQL.open();

        // Creamos un objeto para invocar el StoredProcedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        
        // Establecemos los parámetros de los datos personales en el orden en que los pide el procedimiento almacenado comenzando en 1:
        cstmt.setString(1, c.getPersona().getNombre());
        cstmt.setString(2, c.getPersona().getApellidoPaterno());
        cstmt.setString(3, c.getPersona().getApellidoMaterno());
        cstmt.setString(4, c.getPersona().getGenero());
        cstmt.setString(5, c.getPersona().getDomicilio());
        cstmt.setString(6, c.getPersona().getTelefono());
        cstmt.setString(7, c.getPersona().getRfc());

        //Establecemos los parámetros de los datos de Usuario:
        cstmt.setString(8, c.getUsuario().getNombreUsuario());
        cstmt.setString(9, c.getUsuario().getContrasenia());
        cstmt.setString(10, c.getUsuario().getRol());

        //Establecemos los parámetros de los datos de Cliente:
        cstmt.setString(11, c.getCorreo());

        //Registramos los parámetros de salida:
       
        cstmt.registerOutParameter(12, Types.INTEGER);
        cstmt.registerOutParameter(13, Types.INTEGER);
        cstmt.registerOutParameter(14, Types.INTEGER);
        cstmt.registerOutParameter(15, Types.INTEGER);
        
        //Ejecutamos el StoredProcedure
        cstmt.executeUpdate();

        //Recuperamos los ID's generados:
        idPersonaGenerado = cstmt.getInt(14);
        idUsuarioGenerado = cstmt.getInt(15);
        idClienteGenerado = cstmt.getInt(16);
        numClienteGenerado = cstmt.getString(17);

        //Los guardamos en el objeto Empleado que nos pasaron como parámetro:
        c.getPersona().setId(idPersonaGenerado);
        c.getUsuario().setId(idUsuarioGenerado);
        c.setId(idClienteGenerado);
        c.setNumeroUnico(numClienteGenerado);

        //Cerramos los objetos de Base de datos:
        cstmt.close();
        connMySQL.close();

        //Devolvemos el ID de Empleado generado:
        return idClienteGenerado;

    }
    
     // Buscar un cliente por su nombre
    public List<Cliente> getAllByNombreCliente(String nombre) throws Exception {
        // Consulta SQL a ejecutar:
        String sql = "SELECT * FROM v_clientes WHERE nombre LIKE '%" + nombre + "%'";
        
        // Aquí guardaremos objetos de tipo Cliente. Una lista es un contenedor dinámico de objetos
        List<Cliente> clientes = new ArrayList<Cliente>();

        // Una variable temporal para crear nuevas instancias de Cliente:
        Cliente c = null;
        
        // Generamos el objeto de la conexión:
        ConnectionMySQL connMySQL = new ConnectionMySQL();
        
        // Abrimos la conexión:
        Connection conn = connMySQL.open();
        
        // Objeto para ejecutar la consulta:
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        // Ejecutammos la consulta y guardamos su resultado:
        ResultSet rs = pstmt.executeQuery();
        
        //Recorremos el ResultSet:
        while (rs.next()) {
            c = fill(rs);
            //Agregamos el objeto de tipo Empleado a la lista dinámica:
            clientes.add(c);
        }
        //Cerramos los objetos de BD:
        rs.close();
        pstmt.close();
        connMySQL.close();

        //Devolvemos la lista de Sucursales:
        return clientes;
    }
    
    // Consulta todos los registros de clientes en la BD y devuelve una lista dinámica de objetos de tipo Cliente.
    public List<Cliente> getAll(String filtro) throws Exception {
        // Definimos la consulta SQL:
        String sql = "SELECT * FROM v_clientes WHERE estatus = 1";

        // Aquí guardaremos objetos de tipo Cliente, en una lista, la cual es un contenedor dinámico de objetos:
        List<Cliente> clientes = new ArrayList<Cliente>();

        // Definimos una variable temporal para crear nuevos objetos de tipo Cliente:
        Cliente c = null;

        // Declaramos un objeto de tipo Conexion:
        ConnectionMySQL connMySQL = new ConnectionMySQL();

        // Abrimos la conexión con MySQL:
        Connection conn = connMySQL.open();

        // Declaramos e inicializamos el objeto con el que ejecutaremos la consulta SQL:
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // Ejecutammos la consulta y guardamos su resultado:
        ResultSet rs = pstmt.executeQuery();

        // Recorremos el ResultSet:
        while (rs.next()) {
            c = fill(rs);
            
            // Agregamos el objeto de tipo Cliente a la lista dinámica:
            clientes.add(c);
        }
        
        // Cerramos los objetos de la conexion a la BD:
        rs.close();
        pstmt.close();
        connMySQL.close();

        // Devolvemos la lista de Clientes:
        return clientes;
    }
    
    /**
     * Crear objetos de tipo Cliente, Persona y Usuario y llena sus propiedades utilizando los
     * datos proporcionados por un ResultSet.
     *
     * @param rs
     * @return
     */
    private Cliente fill(ResultSet rs) throws SQLException {
        // Definimos una variable temporal para crear nuevos objetos de tipo Cliente:
        Cliente c = new Cliente();

        // Definimos una variable temporal para crear nuevos objetos de tipo Persona
        Persona p = new Persona();

        // Definimos una variable temporal para crear nuevos objetos de tipo Usuario:
        Usuario u = new Usuario();

        // Llenamos los datos correspondientes de tipo Persona:
        p.setId(rs.getInt("idPersona"));
        p.setNombre(rs.getString("nombre"));
        p.setApellidoPaterno(rs.getString("apellidoPaterno"));
        p.setApellidoMaterno(rs.getString("apellidoMaterno"));
        p.setDomicilio(rs.getString("domicilio"));
        p.setGenero(rs.getString("genero"));
        p.setTelefono(rs.getString("telefono"));
        p.setRfc(rs.getString("rfc"));

        // Llenamos los datos correspondientes de tipo Usuario:
        u.setContrasenia(rs.getString("contrasenia"));
        u.setId(rs.getInt("idUsuario"));
        u.setNombreUsuario(rs.getString("nombreUsuario"));
        u.setRol(rs.getString("rol"));
        u.setToken();

        // Llenamos los datos correspondientes de tipo Cliente:
        c.setId(rs.getInt("idCliente"));
        c.setCorreo(rs.getString("correo"));
        c.setEstatus(rs.getInt("estatus"));
        c.setNumeroUnico(rs.getString("numeroUnico"));
        
        // Establecemos sus respectivos datos de Persona al Cliente:
        c.setPersona(p);

        // Establecemos sus respectivos datos de Usuario al Cliente:
        c.setUsuario(u);

        return c;
    }
}
