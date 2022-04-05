/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scd.myservice.database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author eveli
 */
public class ConnectionMySQL {
    
    // Aqui guardamos y gestinamos la conexion con MySQL:
    Connection conexion;
    
    public Connection open() throws Exception {
        // Establecemos el Driver de MySQL:
        String driver = "com.mysql.jdbc.Driver";
        
        // Establecemos la ruta de conexión:
        String url = "jdbc:mysql://localhost:3306/myservice";

        // Establecemos el usuario y la contraseña:
        String usuario = "root";
        String contrasenia = "";

        //Registramos el Driver nos sirve para ejecutar clases en tiempo de ejecucion 
        Class.forName(driver);

        // Abrimos la conexión con MySQL;
        conexion = DriverManager.getConnection(url, usuario, contrasenia);

        // Devolvemos el objeto que mantiene la conexion con MySQL:
        return conexion;
    }
 
    // Cerramos la conexion con MySQL
    public void close() {
        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
