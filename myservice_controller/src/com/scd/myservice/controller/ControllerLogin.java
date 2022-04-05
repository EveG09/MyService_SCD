/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scd.myservice.controller;

import com.scd.myservice.database.ConnectionMySQL;
import com.scd.myservice.model.Cliente;
import com.scd.myservice.model.Colaborador;
import com.scd.myservice.model.Persona;
import com.scd.myservice.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author eveli
 */
public class ControllerLogin {
    
    // Lleva a cabo la validacion del logueo del usuario "Administrador":
    public Colaborador loginAdmin(String nombreUsuario, String contrasenia) throws Exception {       
        // Definimos la consulta SQL:
        String sql = "SELECT * FROM v_administrador WHERE nombreUsuario = ? AND contrasenia = ? "
                + "AND estatus = 1 AND (token IS NULL OR token = '');";
        
        // Definimos una variable temporal para crear nuevas instancias de Colaborador:
        Colaborador co = null;
        
        // Declaramos un objeto de tipo Conexion:
        ConnectionMySQL connMySQL = new ConnectionMySQL();

        // Abrimos la conexión con MySQL:
        Connection conn = connMySQL.open();
        
        // Declaramos e inicializamos el objeto con el que ejecutaremos la consulta SQL:
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // Llenamos los parámetros de la consulta:
        pstmt.setString(1, nombreUsuario);
        pstmt.setString(2, contrasenia);
        
        // Ejecutammos la consulta y guardamos su resultado:
        ResultSet rs = pstmt.executeQuery();

        // Recorremos el ResultSet:
        if (rs.next()) {
            co = fillColaboradorAdmin(rs);
            saveToken(co.getUsuario());
        }
        
        // Cerramos los objetos de la conexion a la BD:
        rs.close();
        pstmt.close();
        connMySQL.close();

        // Devolvemos los datos del colaborador "Administrador":
        return co;
    }
    
    // Lleva a cabo la validacion del logueo del usuario "Colaborador":
    public Colaborador loginColaborador(String nombreUsuario, String contrasenia) throws Exception {       
        // Definimos la consulta SQL:
        String sql = "SELECT * FROM v_colaboradores WHERE nombreUsuario = ? AND contrasenia = ? "
                + "AND estatus = 1 AND (token IS NULL OR token = '');";
        
        // Definimos una variable temporal para crear nuevas instancias de Colaborador:
        Colaborador co = null;
        
        // Declaramos un objeto de tipo Conexion:
        ConnectionMySQL connMySQL = new ConnectionMySQL();

        // Abrimos la conexión con MySQL:
        Connection conn = connMySQL.open();
        
        // Declaramos e inicializamos el objeto con el que ejecutaremos la consulta SQL:
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // Llenamos los parámetros de la consulta:
        pstmt.setString(1, nombreUsuario);
        pstmt.setString(2, contrasenia);
        
        // Ejecutammos la consulta y guardamos su resultado:
        ResultSet rs = pstmt.executeQuery();

        // Recorremos el ResultSet:
        if (rs.next()) {
            co = fillColaborador(rs);
            saveToken(co.getUsuario());
        }
        
        // Cerramos los objetos de la conexion a la BD:
        rs.close();
        pstmt.close();
        connMySQL.close();

        // Devolvemos los datos del colaborador "Administrador":
        return co;
    }
    
    // Lleva a cabo la validacion del logueo del usuario "Cliente":
    public Cliente loginCliente(String nombreUsuario, String contrasenia) throws Exception {       
        // Definimos la consulta SQL:
        String sql = "SELECT * FROM v_clientes WHERE nombreUsuario = ? AND contrasenia = ? "
                + "AND estatus = 1 AND (token IS NULL OR token = '');";
        
        // Definimos una variable temporal para crear nuevas instancias de Cliente:
        Cliente c = null;
        
        // Declaramos un objeto de tipo Conexion:
        ConnectionMySQL connMySQL = new ConnectionMySQL();

        // Abrimos la conexión con MySQL:
        Connection conn = connMySQL.open();
        
        // Declaramos e inicializamos el objeto con el que ejecutaremos la consulta SQL:
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // Llenamos los parámetros de la consulta:
        pstmt.setString(1, nombreUsuario);
        pstmt.setString(2, contrasenia);
        
        // Ejecutammos la consulta y guardamos su resultado:
        ResultSet rs = pstmt.executeQuery();

        // Recorremos el ResultSet:
        if (rs.next()) {
            c = fillCliente(rs);
            saveToken(c.getUsuario());
        }
        
        // Cerramos los objetos de la conexion a la BD:
        rs.close();
        pstmt.close();
        connMySQL.close();

        // Devolvemos los datos del colaborador "Administrador":
        return c;
    }
    
    // Metodo para guardar el token generado al inciar sesion:
    public void saveToken(Usuario u) throws Exception {
        // Definimos la consulta SQL:
        String sql = "UPDATE usuario SET token = '" + u.getToken() + "' WHERE idUsuario = " + u.getId() + ";";
        
        // Declaramos un objeto de tipo Conexion:
        ConnectionMySQL connMySQL = new ConnectionMySQL();

        // Abrimos la conexión con MySQL:
        Connection conn = connMySQL.open();
        
        // Declaramos e inicializamos el objeto con el que ejecutaremos la consulta SQL:
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        // Cerramos los objetos de la conexion a la BD:
        pstmt.execute();
        pstmt.close();
        conn.close();
        connMySQL.close();
    }
    
    // Metodo para eliminar el token generado al inciar sesion:
    public void deleteToken(Usuario u) throws Exception {
        // Definimos la consulta SQL:
        String sql = "UPDATE usuario SET token = '' WHERE idUsuario = " + u.getId()+ ";";
        
        // Declaramos un objeto de tipo Conexion:
        ConnectionMySQL connMySQL = new ConnectionMySQL();

        // Abrimos la conexión con MySQL:
        Connection conn = connMySQL.open();
        
        // Declaramos e inicializamos el objeto con el que ejecutaremos la consulta SQL:
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        // Cerramos los objetos de la conexión a la BD:
        pstmt.execute();
        pstmt.close();
        conn.close();
        connMySQL.close();
    }
    
    // Metodo para validar el token cuando el "Administrador" inicie sesion de manera correcta: 
    public boolean validateTokenAdmin(String token) throws Exception {
        boolean valid = false;
        
        // Definimos la consulta SQL:
        String sql = "SELECT * FROM v_administrador WHERE token = '" + token + "';";
        
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
            valid = true;
        }
        
        // Cerramos los objetos de la conexión a la BD:
        pstmt.close();
        conn.close();
        connMySQL.close();      

        // Devolvemos la validación del token:
        return valid;
    }
    
    // Metodo para validar el token cuando cualquier Colaborador registrado en la base de datos inicie sesion de manera correcta: 
    public boolean validateTokenColaborador(String token) throws Exception {
        boolean valid = false;
        
        // Definimos la consulta SQL:
        String sql = "SELECT * FROM v_colaboradores WHERE token = '" + token + "';";
        
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
            valid = true;
        }
        
        // Cerramos los objetos de la conexión a la BD:
        pstmt.close();
        conn.close();
        connMySQL.close();      

        // Devolvemos la validación del token:
        return valid;
    }
    
    // Metodo para validar el token cuando cualquier Colaborador registrado en la base de datos inicie sesion de manera correcta: 
    public boolean validateTokenCliente(String token) throws Exception {
        boolean valid = false;
        
        // Definimos la consulta SQL:
        String sql = "SELECT * FROM v_clientes WHERE token = '" + token + "';";
        
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
            valid = true;
        }
        
        // Cerramos los objetos de la conexión a la BD:
        pstmt.close();
        conn.close();
        connMySQL.close();      

        // Devolvemos la validación del token:
        return valid;
    }
    
    /**
     * Crear un objeto de tipo Colaborador y llena sus propiedades utilizando los
     * datos proporcionados por un ResultSet.
     *
     * @param rs
     * @return
     */
    private Colaborador fillColaboradorAdmin(ResultSet rs) throws SQLException {
        // Definimos una variable temporal para crear nuevos objetos de tipo Colaborador
        Colaborador co = new Colaborador();

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
        u.setId(rs.getInt("idUsuario"));
        u.setContrasenia(rs.getString("contrasenia"));
        u.setNombreUsuario(rs.getString("nombreUsuario"));
        u.setRol(rs.getString("rol"));
        u.setToken();

        // Llenamos los datos correspondientes de tipo Colaborador:
        co.setId(rs.getInt("idColaborador"));
        co.setNumeroColaborador(rs.getString("numeroColaborador"));
        co.setPuesto(rs.getString("puesto"));
        co.setEstatus(rs.getInt("estatus"));
        co.setFoto(rs.getString("foto"));
        co.setRutaFoto(rs.getString("rutaFoto"));
        co.setDescripcion(rs.getString("descripcion"));

        // Establecemos sus respectivos datos de Persona al Colaborador "Administrador":
        co.setPersona(p);

        // Establecemos sus respectivos datos de Usuario al Colaborador "Administrador":
        co.setUsuario(u);

        return co;
    }
    
    /**
     * Crear un objeto de tipo Colaborador y llena sus propiedades utilizando los
     * datos proporcionados por un ResultSet.
     *
     * @param rs
     * @return
     */
    private Colaborador fillColaborador(ResultSet rs) throws SQLException {
        // Definimos una variable temporal para crear nuevos objetos de tipo Colaborador
        Colaborador co = new Colaborador();

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

        // Llenamos los datos correspondientes de tipo Colaborador:
        co.setId(rs.getInt("idColaborador"));
        co.setNumeroColaborador(rs.getString("numeroColaborador"));
        co.setPuesto(rs.getString("puesto"));
        co.setEstatus(rs.getInt("estatus"));
        co.setFoto(rs.getString("foto"));
        co.setRutaFoto(rs.getString("rutaFoto"));
        co.setDescripcion(rs.getString("descripcion"));

        // Establecemos sus respectivos datos de Persona al Colaborador "Administrador":
        co.setPersona(p);

        // Establecemos sus respectivos datos de Usuario al Colaborador "Administrador":
        co.setUsuario(u);

        return co;
    }
    
    /**
     * Crear objetos de tipo Cliente, Persona y Usuario y llena sus propiedades utilizando los
     * datos proporcionados por un ResultSet.
     *
     * @param rs
     * @return
     */
    private Cliente fillCliente(ResultSet rs) throws SQLException {
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
