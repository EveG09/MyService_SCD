/********************************************************
 *      BASE DE DATOS myservice                         *
 *                                                      *
 *      Archivo de Definicion de Datos (DDL)            *
 *      Procedimientos Almacenados - Stored Procedures  *
 *******************************************************/
 
 /*
    Version:        1.0
    Fecha:          17/03/2022 23:00:00
    Autor:          Paula Dolores Valencia Palomares
    Email:          pau_doly@hotmail.com
    Comentarios:    Esta es la primera version de la base de datos
                    con las instrucciones necesarias para
                    generar las tablas.
 */

 /*
    Version:        2.0
    Fecha:          22/03/2022
    Autor:          Paula Valencia
    Email:          pau_doly@hotmail.com
    Comentarios:    - Se modifico la tabla [empleado]: Se renombro como colaborador.
    		    - Se modifico la tabla [reservacion]: Se renombro como cita.
 */

-- Procedimiento Almacenado Insertar-Colaborador
DROP PROCEDURE IF EXISTS sp_insertarColaborador;
DELIMITER //
CREATE PROCEDURE sp_insertarColaborador(/* Datos Personales */
                                    IN	var_nombre          VARCHAR(50),    -- 1
                                    IN	var_apellidoPaterno VARCHAR(50),    -- 2
                                    IN	var_apellidoMaterno VARCHAR(50),    -- 3
                                    IN  var_genero          VARCHAR(2),     -- 4
                                    IN	var_domicilio       VARCHAR(200),   -- 5
                                    IN	var_telefono        VARCHAR(25),    -- 6
                                    IN	var_rfc             VARCHAR(14),    -- 7
                                    
                                    /* Datos de Usuario */
                                    IN	var_nombreUsuario   VARCHAR(48),    -- 8
                                    IN	var_contrasenia     VARCHAR(48),    -- 9
                                    IN	var_rol             VARCHAR(24),    -- 10
                                    
                                    /* Datos de Colaborador */
                                    IN	var_puesto          VARCHAR(20),    -- 11
                                    IN	var_foto            LONGTEXT,       -- 12
                                    IN	var_rutaFoto        TEXT,           -- 13
									IN  var_descripcion	    VARCHAR(100),   -- 14
                                    
                                    /* Valores de Retorno */
                                    OUT	var_idPersona       	INT,            -- 15
                                    OUT	var_idUsuario       	INT,            -- 16
                                    OUT	var_idColaborador      	INT,	        -- 17
                                    OUT var_numeroColaborador   VARCHAR(20)     -- 18
				)
BEGIN        
        -- Comenzamos insertando los datos de la Persona:
        INSERT INTO persona (nombre, apellidoPaterno, apellidoMaterno, genero,
                             domicilio, telefono, rfc)
                     VALUES (var_nombre, var_apellidoPaterno, var_apellidoMaterno, 
                             var_genero, var_domicilio, var_telefono, var_rfc);        
		-- Obtenemos el ID de Persona que se generó:
        SET var_idPersona = LAST_INSERT_ID();

        -- Insertamos los datos de seguridad del Empleado:
        INSERT INTO usuario (nombreUsuario, contrasenia, rol) 
                     VALUES (var_nombreUsuario, var_contrasenia, var_rol);
        -- Obtenemos el ID de Usuario que se generó:
        SET var_idUsuario = LAST_INSERT_ID();

        --  Generamos el numero de empleado.
        --  Comenzamos agregando el primer digito (la letra E):
        SET var_numeroColaborador = 'CB';        
        --  Agregamos los digitos del RFC si los tiene:
        IF  LENGTH(var_rfc) >= 4 THEN
            SET var_numeroColaborador = CONCAT(var_numeroColaborador, SUBSTRING(var_rfc, 1, 4));
        END IF;
        --  Finalmente, agregamos el timestamp:
        SET var_numeroColaborador = CONCAT(var_numeroColaborador, CAST(UNIX_TIMESTAMP() AS CHAR));

        -- Finalmente, insertamos en la tabla Colaborador:
        INSERT INTO colaborador(numeroColaborador, puesto, estatus, foto, rutaFoto, 
                                 descripcion, idPersona, idUsuario) 
                    VALUES(var_numeroColaborador, var_puesto, 1, var_foto, var_rutaFoto,
						   var_descripcion, var_idPersona, var_idUsuario);
        -- Obtenemos el ID del Colaborador que se generá:
        SET var_idColaborador = LAST_INSERT_ID();
    END
//
DELIMITER ;


-- Procedimiento Almacenado Actualizar-Colaborador
DROP PROCEDURE IF EXISTS sp_actualizarColaborador;
DELIMITER //
CREATE PROCEDURE sp_actualizarColaborador(	/* Datos Personales */
                                    IN  var_nombre          VARCHAR(64),    -- 1
                                    IN  var_apellidoPaterno VARCHAR(64),    -- 2
                                    IN  var_apellidoMaterno VARCHAR(64),    -- 3
                                    IN  var_genero          VARCHAR(2),     -- 4
                                    IN  var_domicilio       VARCHAR(200),   -- 5
                                    IN  var_telefono        VARCHAR(25),    -- 6
                                    IN	var_rfc             VARCHAR(14),    -- 7
                                    
                                    /* Datos de Usuario */
                                    IN	var_nombreUsuario   VARCHAR(48),    -- 8
                                    IN	var_contrasenia     VARCHAR(48),    -- 9
                                    IN	var_rol             VARCHAR(24),    -- 10
                                    
                                    /* Datos de Empleado */
                                    IN  var_puesto          VARCHAR(20),    -- 11
                                    IN  var_foto            LONGTEXT,       -- 12
                                    IN  var_rutaFoto        TEXT,           -- 13
									IN  var_descripcion	    VARCHAR(100),	-- 14
                                    
                                    /* ID's de las tablas relacionadas con el Empleado */
                                    IN	var_idPersona       INT,            -- 14
                                    IN	var_idUsuario       INT,            -- 15
                                    IN	var_idColaborador   INT             -- 16
                                )                                    
    BEGIN
        -- Comenzamos actualizando los datos personales del Empleado:
        UPDATE persona  SET     nombre = var_nombre, 
                                apellidoPaterno = var_apellidoPaterno,
                                apellidoMaterno = var_apellidoMaterno,
                                genero = var_genero,
                                domicilio = var_domicilio,
                                telefono = var_telefono, 
                                rfc = var_rfc
                        WHERE   idPersona = var_idPersona;

        -- Actualizamos los datos de Seguridad:
        UPDATE usuario  SET     nombreUsuario = var_nombreUsuario,
                                contrasenia = var_contrasenia,
                                rol = var_rol
                        WHERE   idUsuario = var_idUsuario;

        -- Actualizamos sus datos de Empleado:
        UPDATE colaborador SET  puesto = var_puesto, 
                                estatus = 1, 
                                foto = var_foto, 
                                rutaFoto = var_rutaFoto,
								descripcion = var_descripcion
                        WHERE   idColaborador = var_idColaborador;        
    END
//
DELIMITER ;


DROP PROCEDURE IF EXISTS sp_insertarCliente;
DELIMITER $$
CREATE PROCEDURE sp_insertarCliente(   /* Datos Personales */
                                    IN  var_nombre          VARCHAR(64),
                                    IN  var_apellidoPaterno VARCHAR(64),
                                    IN  var_apellidoMaterno VARCHAR(64),
                                    IN  var_genero          VARCHAR(2),
                                    IN  var_domicilio       VARCHAR(200),
                                    IN	var_telefono        VARCHAR(25),
                                    IN	var_rfc             VARCHAR(14),
                                    
                                    /* Datos de Usuario */
                                    IN  var_nombreUsuario   VARCHAR(48),
                                    IN  var_contrasenia     VARCHAR(48),
                                    IN  var_rol             VARCHAR(24),
                                    
                                    /* Datos de Cliente */
                                    IN  var_correo          VARCHAR(100),
                                    
                                    /* Valores de Retorno */
                                    OUT var_idPersona       INT,
                                    OUT var_idUsuario       INT,
                                    OUT var_idCliente       INT,
									OUT var_numeroUnico     VARCHAR(40)
                                )
BEGIN

         -- Comenzamos insertando los datos de la Persona:
        INSERT INTO persona (nombre, apellidoPaterno, apellidoMaterno, genero,
                             domicilio, telefono, rfc)
                     VALUES (var_nombre, var_apellidoPaterno, var_apellidoMaterno, 
                             var_genero, var_domicilio, var_telefono, var_rfc);
        -- Obtenemos el ID de Persona que se generó:
        SET var_idPersona = LAST_INSERT_ID();

        -- Insertamos los datos de seguridad del Empleado:
        INSERT INTO usuario (nombreUsuario, contrasenia, rol) 
                     VALUES (var_nombreUsuario, var_contrasenia, var_rol);
        -- Obtenemos el ID de Usuario que se generó:
        SET var_idUsuario = LAST_INSERT_ID();

		--  Generamos el numero de empleado.
        --  Comenzamos agregando el primer digito (la letra E):
        SET var_numeroUnico = 'CL';        
        
		--  Agregamos los digitos del RFC si los tiene:
        IF  LENGTH(var_rfc) >= 4 THEN
            SET var_numeroUnico = CONCAT(var_numeroUnico, SUBSTRING(var_rfc, 1, 4));
        END IF;
        
		--  Finalmente, agregamos el timestamp:
        SET var_numeroUnico = CONCAT(var_numeroUnico, CAST(UNIX_TIMESTAMP() AS CHAR));

        -- Finalmente, insertamos en la tabla cliente:
        INSERT INTO cliente ( correo, estatus, numeroUnico, idPersona, idUsuario) 
                    VALUES( var_correo, 1, var_numeroUnico, var_idPersona, var_idUsuario);
        -- Recuperamos el ID del CLiente que se genera de forma automatica:
        SET var_idCliente = LAST_INSERT_ID();
    END
$$
DELIMITER ;

-- Stored Procedure para actualizar datos de clientes previamente registrados.
DROP PROCEDURE IF EXISTS sp_actualizarCliente;
DELIMITER $$
CREATE PROCEDURE sp_actualizarCliente(     /* Datos Personales */
                                        IN  var_nombre          VARCHAR(64),
                                        IN  var_apellidoPaterno VARCHAR(64),
                                        IN  var_apellidoMaterno VARCHAR(64),
                                        IN  var_genero          VARCHAR(2),
                                        IN  var_domicilio       VARCHAR(200),
                                        IN  var_telefono        VARCHAR(25),
                                        IN  var_rfc             VARCHAR(14),

                                        /* Datos de Usuario */
                                        IN  var_nombreUsuario   VARCHAR(48),
                                        IN  var_contrasenia     VARCHAR(48),
                                        IN  var_rol             VARCHAR(24),

                                        /* Datos de Cliente */
                                        IN  var_correo          VARCHAR(100),

                                        /* ID's de las tablas afectadas */
                                        IN var_idPersona        INT,
                                        IN var_idUsuario        INT,
                                        IN var_idCliente        INT
                                    )
BEGIN
        -- Comenzamos actualizando los datos personales:
        UPDATE  persona SET     nombre = var_nombre, 
                                apellidoPaterno = var_apellidoPaterno,
                                apellidoMaterno = var_apellidoMaterno,
                                genero = var_genero,
                                domicilio = var_domicilio,
                                telefono = var_telefono, 
                                rfc = var_rfc
                        WHERE   idPersona = var_idPersona;

        -- DespuÃ©s actualizamos los datos de seguridad:
        UPDATE  usuario SET     nombreUsuario = var_nombreUsuario, 
                                contrasenia = var_contrasenia,
                                rol = var_rol 
                        WHERE   idUsuario = var_idUsuario;

        -- Finalmente, actualizamos los datos del cliente:
        UPDATE  cliente SET     correo = var_correo,
                                estatus = 1
                        WHERE   idCliente = var_idCliente;

    END
$$
DELIMITER ;


-- Stored Procedure para insertar una Reservacion:
DROP PROCEDURE IF EXISTS insertarReservacion;
DELIMITER $$
CREATE PROCEDURE insertarReservacion(   /* Datos Personales */
                                        IN var_fecha VARCHAR(21),
                                        IN var_idCliente        INT,
                                        IN var_idReparacion     INT,
                                        OUT var_idReservacion   INT
                                )
    BEGIN
        -- Comenzamos insertando en la tabla Persona:
        INSERT INTO reservacion (fecha, estatus, idCliente, idReparacion) 
                    VALUES(STR_TO_DATE(var_fecha, '%d/%m/%Y'), 1, var_idCliente, var_idReparacion);
        -- Recuperamos el ID de la Persona que se generÃÂ³:
        SET var_idReservacion = LAST_INSERT_ID();
    END
$$
DELIMITER ;