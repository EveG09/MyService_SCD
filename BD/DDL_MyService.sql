/************************************************
 *      BASE DE DATOS myservice                 *
 *                                              *
 *      Archivo de Definicion de Datos (DDL)    *
 ***********************************************/
 
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
    Fecha:          21/03/2022 15:10:00
    Autor:          Evelin Garrido
	Email:          evelingarrido09@gmail.com
    Comentarios:   - Se modifico la tabla [material]: Se cambio el tipo de dato del campo
                     [precio] de FLOAT a DOUBLE.
				   - Se modifico la tabla [reparacion]: Se cambio el tipo de dato del campo
                     [costo] de FLOAT a DOUBLE, y en el campo [tipoReparacion] se hizo el 
                     cambio de VARCHAR(2) a VARCHAR(30). 
				   - Se modifico la tabla [horario]: Se cambio el campo [hora] por [horaInicio],
                     se agrego el campo [horaFin], y se quito el campo [fecha].
				   - Se modifico la tabla [reservacion]: Se hizo la modificacion en la
                     [fk_reservacion_reparacion] al momento de referenciarla con la tabla 
                     [reparacion], ademas, se agrego el campo [idHorario] con su respectiva 
                     FOREIGN KEY [fk_reservacion_horario].
 */
 
DROP DATABASE IF EXISTS myservice;
CREATE DATABASE myservice;
USE myservice;

--  Esta tabla guarda los datos de una persona
CREATE TABLE persona(
    idPersona           INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre              VARCHAR(50) NOT NULL DEFAULT '',
    apellidoPaterno     VARCHAR(50) NOT NULL DEFAULT '',
    apellidoMaterno     VARCHAR(50) NOT NULL DEFAULT '',
    domicilio           VARCHAR(200) NOT NULL DEFAULT '',
	genero              VARCHAR(2)  NOT NULL DEFAULT 'O',
    telefono            VARCHAR(25) NOT NULL DEFAULT '',
    rfc                 VARCHAR(14) NOT NULL DEFAULT ''
);

--  Esta tabla guarda los datos del usuario.
CREATE TABLE usuario(
    idUsuario           INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombreUsuario       VARCHAR(50) NOT NULL,
    contrasenia         VARCHAR(50) NOT NULL DEFAULT '',
    token               TEXT,
    rol                 VARCHAR(30) NOT NULL DEFAULT ''
);

--  Esta tabla guarda los datos de un cliente, incluidos
--  sus datos personales (dentro de la tabla persona).
CREATE TABLE cliente(
    idCliente       INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	correo          VARCHAR(100) NOT NULL DEFAULT '',
	estatus         INT NOT NULL DEFAULT 1, -- 1: Activo; 2: Inactivo
	numeroUnico     VARCHAR(40) NOT NULL,
    idPersona       INT NOT NULL,
    idUsuario       INT NOT NULL,
    CONSTRAINT  fk_cliente_persona  FOREIGN KEY (idPersona) 
                REFERENCES persona(idPersona) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  fk_cliente_usuario  FOREIGN KEY (idUsuario) 
                REFERENCES usuario(idUsuario) ON DELETE CASCADE ON UPDATE CASCADE	
);


--  Esta tabla guarda los datos del colaborador, incluidos
--  sus datos personales (dentro de la tabla persona).
CREATE TABLE colaborador(
    idColaborador       INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    numeroColaborador   VARCHAR(40),
    puesto              VARCHAR(30),
    estatus             INT NOT NULL DEFAULT 1, -- 1: Activo; 2: Inactivo
    foto                LONGTEXT,
    rutaFoto            TEXT,
	descripcion			VARCHAR(100),
    idPersona           INT NOT NULL,
    idUsuario           INT NOT NULL,
    CONSTRAINT  fk_colaborador_persona  FOREIGN KEY (idPersona) 
                REFERENCES persona(idPersona) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  fk_colaborador_usuario  FOREIGN KEY (idUsuario) 
                REFERENCES usuario(idUsuario) ON DELETE CASCADE ON UPDATE CASCADE
);


--  Esta tabla guarda los materiales necesarios para los servicios:
CREATE TABLE material(
    idMaterial      INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre          VARCHAR(255) NOT NULL DEFAULT '',
    marca           VARCHAR(255) NOT NULL DEFAULT '',
    estatus         INT NOT NULL DEFAULT 1, -- 1: Activo; 2: Inactivo
    precio       	DOUBLE NOT NULL DEFAULT 0.0 
);


--  Esta tabla guarda los datos de las reparaciones.
CREATE TABLE reparacion(
    idReparacion    INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre          VARCHAR(65) NOT NULL DEFAULT '',
	costo           DOUBLE NOT NULL DEFAULT 0,
    tipoReparacion  VARCHAR(30) NOT NULL,
	descripcion     TEXT NOT NULL,
    estatus         INT NOT NULL DEFAULT 1
);


--  Esta tabla guarda diferentes horarios identificados
--  por dia y por hora de inicio y hora de termino.
CREATE TABLE horario(
    idHorario   INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    horaInicio  VARCHAR(10),
    horaFin     VARCHAR(10)
);

--  Esta tabla guarda datos de citas incluidos:
--      1. La fecha y la hora de la cita.
--      2. El estatus de la cita.
--      3. El cliente que realiza la cita.
--      4. La reparacion que se llevara a cabo.
CREATE TABLE cita(
    idCita            INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    estatus           INT,	-- 0: Cancelada; 1: Activa; 2: Atendida;
    idCliente         INT NOT NULL,
    idReparacion      INT NOT NULL,
	fechaCita         DATE,
    idHorario         INT NOT NULL,
	idColaborador     INT NOT NULL,
    CONSTRAINT  fk_cita_cliente FOREIGN KEY (idCliente)
                REFERENCES cliente(idCliente) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  fk_cita_reparacion FOREIGN KEY (idReparacion) 
                REFERENCES reparacion(idReparacion) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  fk_cita_horario FOREIGN KEY (idHorario) 
                REFERENCES horario(idHorario) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT  fk_cita_colaborador FOREIGN KEY (idColaborador) 
                REFERENCES colaborador(idColaborador) ON DELETE CASCADE ON UPDATE CASCADE
);


--  Esta tabla guarda los datos de las citas atendidas a traves
--  de los servicios brindados.
CREATE TABLE servicio(
    idServicio      INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    fecha           DATETIME NOT NULL,
    idCita          INT NOT NULL,
    idColaborador   INT NOT NULL,
    CONSTRAINT  fk_servicio_cita  FOREIGN KEY (idCita) 
                REFERENCES cita (idCita) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  fk_servicio_colaborador  FOREIGN KEY (idColaborador) 
                REFERENCES colaborador (idColaborador) ON DELETE CASCADE ON UPDATE CASCADE
);


--  Esta tabla guarda los datos de los tratamientos adquiridos en cada
--  servicio.
CREATE TABLE servicio_reparacion (
    idServicioReparacion   INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idReparacion           INT NOT NULL,
    idServicio             INT NOT NULL,
    CONSTRAINT  fk_serviciotratamiento_reparacion  FOREIGN KEY (idReparacion) 
                REFERENCES reparacion(idReparacion) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  fk_tratamientoservicio_servicio  FOREIGN KEY (idServicio) 
                REFERENCES servicio(idServicio) ON DELETE CASCADE ON UPDATE CASCADE
);


--  Esta tabla guarda la informacion de los servicios, las reparaciones 
--  realizados así como el detalle de los materiales (con cantidades y precios)
--  de cada reparacion.
--  La información de esta tabla es redundante, sin embargo, es necesario
--  dejarla así para no perder la integridad de los datos históricos.
CREATE TABLE servicio_reparacion_material
(
    idServicioReparacion    INT NOT NULL,
    idMaterial              INT NOT NULL,
    precio	                DOUBLE NOT NULL DEFAULT 0.0,
    CONSTRAINT  fk_servicioreparacionmaterial_servicioreparacion  FOREIGN KEY (idServicioReparacion) 
                REFERENCES servicio_reparacion (idServicioReparacion) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  fk_servicioreparacionmaterial_material  FOREIGN KEY (idMaterial) 
                REFERENCES material(idMaterial) ON DELETE CASCADE ON UPDATE CASCADE
);