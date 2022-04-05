/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scd.myservice.controller;

import com.scd.myservice.database.ConnectionMySQL;
import com.scd.myservice.model.Colaborador;
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
public class ControllerColaborador {
    
    // Inserta un registro en la base d datos y devuelve el ID generado
    public int insert(Colaborador co) throws Exception {
        // Definimos la consulta SQL que invoca al Stored Procedure:
        String sql = "{CALL sp_insertarColaborador(?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, "
                + "?, ?, ?, ?, "
                + "?, ?, ?, ?)}"; // Valores de Retorno
        
        // Aquí guardaremos los ID's que se generán: 
        int idPersonaGenerado = -1;
        int idUsuarioGenerado = -1;
        int idColaboradorGenerado = -1;
        
        String numColaboradorGenerado = "";
        
        // Declaramos un objeto de tipo Conexion:
        ConnectionMySQL connMySQL = new ConnectionMySQL();

        // Abrimos la conexión con MySQL:
        Connection conn = connMySQL.open();
        
        // Con este objeto invocaremos el Stored Procedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        
        // Establecemos los parámetros de los datos personales en el orden en que los pide el procedimiento almacenado comenzando en 1:
        cstmt.setString(1, co.getPersona().getNombre());
        cstmt.setString(2, co.getPersona().getApellidoPaterno());
        cstmt.setString(3, co.getPersona().getApellidoMaterno());
        cstmt.setString(4, co.getPersona().getGenero());
        cstmt.setString(5, co.getPersona().getDomicilio());
        cstmt.setString(6, co.getPersona().getTelefono());
        cstmt.setString(7, co.getPersona().getRfc());
        
        // Establecemos los parámetros de los datos de Usuario:
        cstmt.setString(8, co.getUsuario().getNombreUsuario());
        cstmt.setString(9, co.getUsuario().getContrasenia());
        cstmt.setString(10, co.getUsuario().getRol());
        
        // Establecemos los parámetros de los datos de Colaborador:
        cstmt.setString(11, co.getPuesto());
        cstmt.setString(12, co.getFoto());
        cstmt.setString(13, co.getRutaFoto());
        cstmt.setString(14, co.getDescripcion());
        
        // Registramos los parámetros de salida:
        cstmt.registerOutParameter(15, Types.INTEGER);
        cstmt.registerOutParameter(16, Types.INTEGER);
        cstmt.registerOutParameter(17, Types.INTEGER);
        cstmt.registerOutParameter(18, Types.INTEGER);
        
        // Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
        
        // Recuperamos los ID's generados:
        idPersonaGenerado = cstmt.getInt(15);
        idUsuarioGenerado = cstmt.getInt(16);
        idColaboradorGenerado = cstmt.getInt(17);
        numColaboradorGenerado = cstmt.getString(18);
        
        // Los guardamos en el objeto Colaborador que nos pasaron como parámetro:
        co.getPersona().setId(idPersonaGenerado);
        co.getUsuario().setId(idUsuarioGenerado);
        co.setId(idColaboradorGenerado);
        co.setNumeroColaborador(numColaboradorGenerado);
        
        // Cerramos los objetos de la conexion a la BD:
        cstmt.close();
        connMySQL.close();
        
        // Devolvemos el ID de Colaborador generado:
        return idColaboradorGenerado;
    }
    
    // Consulta todos los registros de clientes en la BD y devuelve una lista dinámica de objetos de tipo Colaborador.
    public List<Colaborador> getAll(String filtro) throws Exception {
        // Definimos la consulta SQL:
        String sql = "SELECT * FROM v_colaboradores WHERE estatus = 1";

        // Aquí guardaremos objetos de tipo Cliente, en una lista, la cual es un contenedor dinámico de objetos:
        List<Colaborador> colaboradores = new ArrayList<Colaborador>();

        // Definimos una variable temporal para crear nuevos objetos de tipo Colaborador:
        Colaborador co = null;

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
            co = fill(rs);
            
            // Agregamos el objeto de tipo Colaborador a la lista dinámica:
            colaboradores.add(co);
        }
        
        // Cerramos los objetos de la conexion a la BD:
        rs.close();
        pstmt.close();
        connMySQL.close();

        // Devolvemos la lista de Colaboradores:
        return colaboradores;
    }
    
    /**
     * Crear un objeto de tipo Colaborador y llena sus propiedades utilizando los
     * datos proporcionados por un ResultSet.
     *
     * @param rs
     * @return
     */
    private Colaborador fill(ResultSet rs) throws SQLException {
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
}
