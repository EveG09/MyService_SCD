/********************************************************
 *      BASE DE DATOS myservice                             *
 *                                                      *
 *      Archivo de Definicion de Datos (DDL)            *
 *      Vistas - Views                                  *
 *******************************************************/
 
 /*
    Version:        1.0
    Fecha:          23/03/2022 18:48:00
    Autor:          Evelin Garrido
	Email:          evelingarrido09@gmail.com
    Comentarios:    Esta es la primera version de las vistas
                    principales.
 */

USE myservice;

--  Vista que consulta todos los datos del Administrador
DROP VIEW IF EXISTS v_administrador;
CREATE VIEW v_administrador AS
    SELECT  P.*,
            CO.idColaborador,
            CO.numeroColaborador,
            CO.puesto,
            CO.estatus,
            CO.foto,
            CO.rutaFoto,
			CO.descripcion,
            U.*
    FROM    persona P
            INNER JOIN colaborador CO ON CO.idPersona = P.idPersona
            INNER JOIN usuario U ON U.idUsuario = CO.idUsuario
	WHERE   rol = 'Administrador';

--  Vista que consulta todos los datos de un Colaborador
DROP VIEW IF EXISTS v_colaboradores;
CREATE VIEW v_colaboradores AS
    SELECT  P.*,
            CO.idColaborador,
            CO.numeroColaborador,
            CO.puesto,
            CO.estatus,
            CO.foto,
            CO.rutaFoto,
			CO.descripcion,
            U.*
    FROM    persona P
            INNER JOIN colaborador CO ON CO.idPersona = P.idPersona
            INNER JOIN usuario U ON U.idUsuario = CO.idUsuario
	WHERE   rol <> 'Administrador';

--  Vista que consulta todos los datos de un Cliente
DROP VIEW IF EXISTS v_clientes;
CREATE VIEW v_clientes AS
    SELECT  P.*,
            CL.idCliente,
            CL.correo,
            CL.estatus,
            CL.numeroUnico,
            U.*
    FROM    persona P
            INNER JOIN cliente CL ON CL.idPersona = P.idPersona
            INNER JOIN usuario U ON U.idUsuario = CL.idUsuario;

-- Vista que consulta todos los datos de una Cita:
DROP VIEW IF EXISTS v_citas;
CREATE VIEW v_citas AS
	SELECT  P.*,
			CL.idCliente,
			CL.correo,
			CL.numeroUnico,
			C.idCita,
			DATE_FORMAT(C.fechaCita, '%d/%m/%Y') AS fechaCita,
			C.estatus AS estatusCita,
			R.idReparacion,
			R.nombre AS nombreReparacion,
			R.costo,
			R.tipoReparacion,
			R.descripcion,
			R.estatus,
			H.idHorario,
			H.horaInicio,
			H.horaFin
	FROM persona P
			INNER JOIN cliente CL ON CL.idPersona = P.idPersona
			INNER JOIN cita C ON C.idCliente = CL.idCliente
			INNER JOIN reparacion R ON C.idReparacion = R.idReparacion
			INNER JOIN horario H ON C.idHorario = H.idHorario;