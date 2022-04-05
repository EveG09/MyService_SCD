/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scd.myservice.controller;

import com.scd.myservice.database.ConnectionMySQL;
import com.scd.myservice.model.Cita;
import com.scd.myservice.model.Cliente;
import com.scd.myservice.model.Colaborador;
import com.scd.myservice.model.Horario;
import com.scd.myservice.model.Persona;
import com.scd.myservice.model.Reparacion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eveli
 */
public class ControllerCita {
    
    public void insert(Cita c) throws Exception {
        String sql = "INSERT INTO cita(estatus, idCliente, idReparacion, fechaCita, idHorario) "
                    + "VALUES (" + c.getEstatus() + ", " + c.getCliente().getId() + ", " + c.getReparacion().getId()
                    + ", STR_TO_DATE('" + c.getFechaCita()+ "', '%Y-%m-%d'), " + c.getHorario().getId() + ")";
        
        // Con este objeto nos vamos a conectar a la Base de Datos:
        ConnectionMySQL connMySQL = new ConnectionMySQL();

        // Abrimos la conexion de la base de datos:
        Connection conn = connMySQL.open();
    
        Statement stmt = conn.createStatement();
        
        stmt.execute(sql);
        stmt.close();
        connMySQL.close();
    }
    
    public void delete(int idCita) throws Exception {
        //Definimos la consulta SQL que realiza la inserción del registro:
        String sql = "UPDATE cita SET estatus = 3 WHERE idCita = ?;";

        // Con este objeto nos vamos a conectar a la Base de Datos:
        ConnectionMySQL connMySQL = new ConnectionMySQL();

        // Abrimos la conexión a la Base de Datos:
        Connection conn = connMySQL.open();

        // Declaramos e inicializamos el objeto con el que ejecutaremos la consulta SQL:
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // Obtenemos el id del producto para hacer su eliminación lógica cambiando el estatus a 0:
        pstmt.setInt(1, idCita);

        // Ejecutamos la consulta SQL:
        pstmt.executeUpdate();

        // Cerramos todos los objetos de conexión con la Base de Datos:
        pstmt.close();
        connMySQL.close();
    }
    
    public List<Horario> getAllHorarios(String fecha) throws SQLException, Exception {
        String sql = "SELECT H1.* FROM horario H1\n"
                + "	LEFT JOIN\n"
                + "(SELECT H.*\n"
                + "	FROM horario H\n"
                + "	INNER JOIN cita C\n"
                + "	ON H.idHorario = C.idHorario\n"
                + "	WHERE C.fechaCita = STR_TO_DATE('" + fecha + "', '%Y-%m-%d')) AS SQ2\n"
                + "ON H1.idHorario = SQ2.idHorario\n"
                + "WHERE SQ2.idHorario IS NULL;";

        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConnectionMySQL connMySQL = new ConnectionMySQL();

        //Abrimos la conexion de la base de datos:
        Connection conn = connMySQL.open();

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        List<Horario> horarios = new ArrayList<>();

        while (rs.next()) {
            Horario h = new Horario();
            h.setId(rs.getInt("idHorario"));
            h.setHoraInicio(rs.getString("horaInicio"));
            h.setHoraFin(rs.getString("horaFin"));
            horarios.add(h);
        }

        // Cerramos los objetos de BD:
        rs.close();
        stmt.close();
        connMySQL.close();

        // Devolvemos la lista de horarios:
        return horarios;
    }

    public List<Cita> getAll(String filtro) throws Exception {
        // Definimos la consulta SQL:
        String sql = "SELECT * FROM v_citas WHERE estatusCita = 1";

        // Aquí guardaremos objetos de tipo Cita. Una lista es un contenedor dinámico de objetos
        List<Cita> citas = new ArrayList<Cita>();

        // Una variable temporal para crear nuevas instancias de Cita:
        Cita c = null;

        // Con este objeto vamos a conectar a la Base de Datos:
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
            citas.add(c);
        }
        // Cerramos los objetos de BD:
        rs.close();
        pstmt.close();
        connMySQL.close();

        // Devolvemos la lista de citas:
        return citas;
    }

    public List<Cita> getAllAtendidas(String filtro) throws Exception {
        // Definimos la consulta SQL:
        String sql = "SELECT * FROM v_citas WHERE estatusCita = 2;";

        // Aquí guardaremos objetos de tipo Cita. Una lista es un contenedor dinámico de objetos
        List<Cita> citas = new ArrayList<Cita>();

        // Una variable temporal para crear nuevas instancias de Cita:
        Cita c = null;

        // Con este objeto vamos a conectar a la Base de Datos:
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
            citas.add(c);
        }
        // Cerramos los objetos de BD:
        rs.close();
        pstmt.close();
        connMySQL.close();

        // Devolvemos la lista de citas:
        return citas;
    }

    public List<Cita> getAllCanceladas(String filtro) throws Exception {
        // Definimos la consulta SQL:
        String sql = "SELECT * FROM v_citas WHERE estatusCita = 3;";

        // Aquí guardaremos objetos de tipo Cita. Una lista es un contenedor dinámico de objetos
        List<Cita> citas = new ArrayList<Cita>();

        // Una variable temporal para crear nuevas instancias de Cita:
        Cita c = null;

        // Con este objeto vamos a conectar a la Base de Datos:
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
            citas.add(c);
        }
        // Cerramos los objetos de BD:
        rs.close();
        pstmt.close();
        connMySQL.close();

        // Devolvemos la lista de citas:
        return citas;
    }
    
    /**
     * Crear un objeto de tipo Cita y llena sus propiedades utilizando los datos
     * proporcionados por un ResultSet.
     *
     * @param rs
     * @return
     */
    private Cita fill(ResultSet rs) throws SQLException {
        // Una variable temporal para crear nuevos objetos de tipo Cita
        Cita c = new Cita();

        // Una variable temporal para crear nuevos objetos de tipo Persona para Cliente:
        Persona pc = new Persona();
        
        // Una variable temporal para crear nuevos objetos de tipo Cliente:
        Cliente cl = new Cliente();

        // Una variable temporal para crear nuevos objetos de tipo Reparación:
        Reparacion r = new Reparacion();

        // Una variable temporal para crear nuevos objetos de tipo Sala:
        Horario h = new Horario();

        // Llenamos los datos de Persona:
        pc.setId(rs.getInt("idPersona"));
        pc.setNombre(rs.getString("nombre"));
        pc.setApellidoPaterno(rs.getString("apellidoPaterno"));
        pc.setApellidoMaterno(rs.getString("apellidoMaterno"));
        pc.setGenero(rs.getString("genero"));
        pc.setDomicilio(rs.getString("domicilio"));
        pc.setTelefono(rs.getString("telefono"));
        pc.setRfc(rs.getString("rfc"));

        // Llenamos los datos de Cliente
        cl.setId(rs.getInt("idCliente"));
        cl.setCorreo(rs.getString("correo"));
        cl.setNumeroUnico(rs.getString("numeroUnico"));

        // Llenamos los datos de Reparación
        r.setId(rs.getInt("idReparacion"));
        r.setNombre(rs.getString("nombreReparacion"));
        r.setCosto(rs.getDouble("costo"));
        r.setTipoReparación(rs.getString("tipoReparacion"));
        r.setDescripcion(rs.getString("descripcion"));
        r.setEstatus(rs.getInt("estatus"));

        // Llenamos los datos de Horario
        h.setId(rs.getInt("idHorario"));
        h.setHoraInicio(rs.getString("horaInicio"));
        h.setHoraFin(rs.getString("horaFin"));
        
        //Llenamos los datos de Cita
        c.setId(rs.getInt("idCita"));
        c.setEstatus(rs.getInt("estatusCita"));
        c.setFechaCita(rs.getString("fechaCita"));

        // Establecemos su persona al Cliente:
        cl.setPersona(pc);

        // Establecemos sus datos corresondientes de Cliente, Reparación, Horario, Colaborador:
        c.setCliente(cl);
        c.setReparacion(r);
        c.setHorario(h);

        return c;
    }
}
