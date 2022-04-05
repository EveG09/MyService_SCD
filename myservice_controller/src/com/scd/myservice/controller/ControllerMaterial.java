/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scd.myservice.controller;

import com.scd.myservice.database.ConnectionMySQL;
import com.scd.myservice.model.Material;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ControllerMaterial {
    
    public int insert(Material m) throws Exception {
        // Definimos la consulta SQL que realiza la inserción del registro:
        String sql = "INSERT INTO material(nombre, marca, precio, estatus) VALUES(?, ?, ?, ?)";
        
        // Aquí guardaremos el ID que se generará:
        int idGenerado = -1;
        
        // Declaramos un objeto de tipo Conexion:
        ConnectionMySQL connMySQL = new ConnectionMySQL();

        // Abrimos la conexión con MySQL:
        Connection conn = connMySQL.open();
        
        // Con este objeto ejecutaremos la sentencia SQL que realiza la inserción en la tabla. Debemos especificarle que queremos que nos devuelva el ID
        // que se genera al realizar la inserción del registro.
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        // En este objeto guardaremos el resultado de la consulta, la cual nos devolverá los ID's que se generaron. En este caso, solo se generará un ID:      
        ResultSet rs = null;
        
        // Llenamos el valor de campo de la consulta SQL definida antes:
        pstmt.setString(1, m.getNombre());
        pstmt.setString(2, m.getMarca());
        pstmt.setDouble(3, m.getPrecio());
        pstmt.setInt(4, 1);
        
        // Ejecutamos la consulta SQL:
        pstmt.executeUpdate();
        
        // Le pedimos al PreparedStatementel valor de las claves primarias generadas, que en este caso, es solo un valor:
        rs = pstmt.getGeneratedKeys();
        
        // Intentamos movernos al primer registro:
        if (rs.next()) {
            idGenerado = rs.getInt(1);
            m.setId(idGenerado);
        }
        
        // Cerramos todos los objetos de conexión con la Base de Datos:
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Devolvemos el ID generado:
        return idGenerado;
    }
    
    public void update(Material m) throws Exception {
        //Definimos la consulta SQL que realiza la inserción del registro:
        String sql = "UPDATE material SET nombre = ?, marca = ?, precio = ? WHERE idMaterial = ?";
        
        // Con este objeto nos vamos a conectar a la Base de Datos:
        ConnectionMySQL connMySQL = new ConnectionMySQL();
        
        // Abrimos la conexión a la Base de Datos:
        Connection conn = connMySQL.open();
        
        // Declaramos e inicializamos el objeto con el que ejecutaremos la consulta SQL:
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, m.getNombre());
        pstmt.setString(2, m.getMarca());
        pstmt.setDouble(3, m.getPrecio());    
        pstmt.setInt(4, m.getId());
        
        // Ejecutamos la consulta SQL:
        pstmt.executeUpdate();
        
        // Cerramos todos los objetos de conexión con la Base de Datos:
        pstmt.close();
        connMySQL.close();
    }
    
    public void delete(int idMaterial) throws Exception {
        //Definimos la consulta SQL que realiza la inserción del registro:
        String sql = "UPDATE material SET estatus = 0 WHERE idMaterial = ?";
        // Con este objeto nos vamos a conectar a la Base de Datos:
        ConnectionMySQL connMySQL = new ConnectionMySQL();
        
        // Abrimos la conexión a la Base de Datos:
        Connection conn = connMySQL.open();
        
        // Declaramos e inicializamos el objeto con el que ejecutaremos la consulta SQL:
        PreparedStatement pstmt = conn.prepareStatement(sql);
              
        // Obtenemos el id del producto para hacer su eliminación lógica cambiando el estatus a 0:
        pstmt.setInt(1, idMaterial);
        
        // Ejecutamos la consulta SQL:
        pstmt.executeUpdate();
        
        // Cerramos todos los objetos de conexión con la Base de Datos:
        pstmt.close();
        connMySQL.close();
    }
    
    public List<Material> getAll(String filtro)throws Exception {
        // Definimos la consulta SQL:
        String sql = "SELECT * FROM material WHERE estatus = 1";
        
        // Aquí guardamos objetos de tipo Sucursal. Una lista es un contenedor dinámico de objetos.
        List<Material> material = new ArrayList<Material>();
        
        // Una variable temporal para crear nuevas instancias de Horario:
        Material m = null;
        
        // Con este objeto vamos a conectar a la Base de Datos:
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
            m = fill(rs);
            material.add(m);
        }
        
        // Cerramos lo objetos de BD:
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        // Devolvemos la lista de materiales:
        return material;
    }
    
    /**
     * Crea un objeto de tipo Material y llena sus propiedades utilizando
     * los datos proporcionados por un ResultSet.
     * @param rs
     * @return 
     */
    private Material fill(ResultSet rs) throws Exception {
        // Creamos una nueva instancia de Sucursal:
        Material m = new Material();
        
        // LLenamos sus propiedades
        m.setId(rs.getInt("idMaterial"));
        m.setNombre(rs.getString("nombre"));
        m.setMarca(rs.getString("marca"));
        m.setPrecio(rs.getDouble("precio"));
        m.setEstatus(rs.getInt("estatus"));
       
        // Devolvemos el objeto de tipo Horario:
        return m;
    }
}
