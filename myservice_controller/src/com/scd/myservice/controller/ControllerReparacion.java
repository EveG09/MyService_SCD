/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scd.myservice.controller;

import com.scd.myservice.database.ConnectionMySQL;
import com.scd.myservice.model.Reparacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ControllerReparacion {
    
    public int insert(Reparacion r) throws Exception {
        //Definimos la consulta SQL que realiza la inserción del registro:
        String sql = "INSERT INTO reparacion (nombre, costo, tipoReparacion, descripcion, estatus) VALUES(?, ?, ?, ?, ?)";

        // Aquí guardaremos el ID que se generará:
        int idGenerado = -1;

        //Con este objeto vamos a conectar a la Base de Datos:
        ConnectionMySQL connMySQL = new ConnectionMySQL();

        //Abrimos la conexión con MySQL:
        Connection conn = connMySQL.open();

        // Con este objeto ejecutaremos la sentencia SQL que realiza la inserción en la tabla. Debemos especificarle que queremos que nos devuelva el ID
        // que se genera al realizar la inserción del registro.
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        // En este objeto guardaremos el resultado de la consulta, la cual nos devolverá los ID's que se generaron. En este caso, solo se generará un ID:      
        ResultSet rs = null;

        // Llenamos el valor de campo de la consulta SQL definida antes:
        pstmt.setString(1, r.getNombre());
        pstmt.setDouble(2, r.getCosto());
        pstmt.setString(3, r.getTipoReparación());
        pstmt.setString(4, r.getDescripcion());
        pstmt.setInt(5, 1);

        // Ejecutamos la consulta SQL:
        pstmt.executeUpdate();

        // Le pedimos al PreparedStatementel valor de las claves primarias generadas, que en este caso, es solo un valor:
        rs = pstmt.getGeneratedKeys();

        // Intentamos movernos al primer registro:
        if (rs.next()) {
            idGenerado = rs.getInt(1);
            r.setId(idGenerado);
        }

        // Cerramos todos los objetos de conexión con la Base de Datos:
        rs.close();
        pstmt.close();
        connMySQL.close();

        // Devolvemos el ID generado:
        return idGenerado;
    }

    public void update(Reparacion r) throws Exception {
        //Definimos la consulta SQL que realiza la inserción del registro:
        String sql = "UPDATE reparacion SET nombre = ?, costo = ?, tipoReparacion = ?, descripcion = ? WHERE idReparacion = ?";

        //Con este objeto vamos a conectar a la Base de Datos:
        ConnectionMySQL connMySQL = new ConnectionMySQL();

        //Abrimos la conexión con MySQL:
        Connection conn = connMySQL.open();

        // Declaramos e inicializamos el objeto con el que ejecutaremos la consulta SQL:
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, r.getNombre());
        pstmt.setDouble(2, r.getCosto());
        pstmt.setString(3, r.getTipoReparación());
        pstmt.setString(4, r.getDescripcion());
        pstmt.setInt(5, r.getId());
        
        // Ejecutamos la consulta SQL:
        pstmt.executeUpdate();

        // Cerramos todos los objetos de conexión con la Base de Datos:
        pstmt.close();
        connMySQL.close();
    }

    public void delete(int idReparacion) throws Exception {
        //Definimos la consulta SQL que realiza la inserción del registro:
        String sql = "UPDATE reparacion SET estatus = 0 Where idReparacion = ?";
        //Con este objeto vamos a conectar a la Base de Datos:
        ConnectionMySQL connMySQL = new ConnectionMySQL();

        //Abrimos la conexión con MySQL:
        Connection conn = connMySQL.open();

        // Declaramos e inicializamos el objeto con el que ejecutaremos la consulta SQL:
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // Obtenemos el id del producto para hacer su eliminación lógica cambiando el estatus a 0:
        pstmt.setInt(1, idReparacion);

        // Ejecutamos la consulta SQL:
        pstmt.executeUpdate();

        // Cerramos todos los objetos de conexión con la Base de Datos:
        pstmt.close();
        connMySQL.close();
    }

    public List<Reparacion> getAll(String filtro) throws Exception {
        // Definimos la consulta SQL:
        String sql = "SELECT * FROM reparacion WHERE estatus = 1";

        // Aquí guardamos objetos de tipo Reparacion. Una lista es un
        // contenedor dinámico de objetos.
        List<Reparacion> reparacion = new ArrayList<Reparacion>();

        // Una variable temporal para crear nuevas instancias de Reparacion:
        Reparacion r = null;

        //Con este objeto vamos a conectar a la Base de Datos:
        ConnectionMySQL connMySQL = new ConnectionMySQL();

        //Abrimos la conexión con MySQL:
        Connection conn = connMySQL.open();

        //Declaramos e inicializamos el objeto con el que ejecutaremos
        // la consulta SQL:
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // Ejecutamos la consulta y guardamos su resultado:
        ResultSet rs = pstmt.executeQuery();

        // Recorremos el ResultSet:
        while (rs.next()) {
            r = fill(rs);
            reparacion.add(r);
        }

        // Cerramos lo objetos de BD:
        rs.close();
        pstmt.close();
        connMySQL.close();

        // Devolvemos la lista de Reparacion:
        return reparacion;
    }

    public Reparacion fill(ResultSet rs) throws SQLException {
        // Creamos una nueva instancia de Reparacion:
        Reparacion r = new Reparacion();

        // Llenamos sus propiedades:
        r.setId(rs.getInt("idReparacion"));
        r.setNombre(rs.getString("nombre"));
        r.setCosto(rs.getDouble("costo"));
        r.setTipoReparación(rs.getString("tipoReparacion"));
        r.setDescripcion(rs.getString("descripcion"));
        r.setEstatus(rs.getInt("estatus"));

        // Devolvemos el objeto de tipo Reparacion
        return r;
    }
}
