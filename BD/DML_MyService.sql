/****************************************************
 *	BASE DE DATOS myservice                         *
 *                                                  *
 *	Archivo de Datos Base para pruebas (DML)    *
 ***************************************************/
 
 /*
    Version:        1.0
    Comentarios:    Datos básicos para hacer pruebas
                    de consultas con la Base de Datos.
 */

USE myservice;

--  Datos personales [persona]:
--	Persona Virtual del Administrador:
INSERT INTO persona (idPersona, nombre, apellidoPaterno, apellidoMaterno, domicilio, telefono, rfc) VALUES(1, 'Administrador', '', '', '', '', 'ADMINISTRATOR');
-- Empleados
INSERT INTO persona (idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, domicilio, telefono, rfc) VALUES(11, 'Daniel Abraham', 'Sanchez', 'Aboytes', 'M', 'Inventores #614, Col. Aurora', '47712345680', 'SAAD980916');
INSERT INTO persona (idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, domicilio, telefono, rfc) VALUES(12, 'Paula Dolores', 'Valencia', 'Palomares', 'F', 'Sabinas #123, Col. Adquirientes', '47712345682', 'VAPP990516');
INSERT INTO persona (idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, domicilio, telefono, rfc) VALUES(13, 'Evelin', 'Garrido', '', 'F', 'Pio XI #123, Col. Valle Verde', '47712345689', 'GAXE961231');
INSERT INTO persona (idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, domicilio, telefono, rfc) VALUES(14, 'Sergio Arturo', 'Alba', 'Arguello', 'M', 'Estaño #222, Col. Echeveste', '47712345681', 'ALAS941216');
INSERT INTO persona (idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, domicilio, telefono, rfc) VALUES(15, 'Carlos', 'Ramires', 'Diaz', 'M', 'Campeche #615, Col. Vista hermosa', '47712345675', 'RADC950906');
INSERT INTO persona (idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, domicilio, telefono, rfc) VALUES(16, 'Fabian', 'Suarez', 'Molina', 'M', 'Guanajuato #131, Col. Las Peras', '47712345676', 'SUMF901010');
INSERT INTO persona (idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, domicilio, telefono, rfc) VALUES(17, 'Amelia', 'Loreda', 'Avila', 'F', 'Baviera #321, Col. La Carmona', '47712345677', 'LOAA961216');
-- Cliente
INSERT INTO persona (idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, domicilio, telefono, rfc) VALUES(18, 'Renato', 'Sanchez', 'Reyes', 'M', 'Alhondiga # 316 Col. Heroes', '47712345678', 'SARR851205');
INSERT INTO persona (idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, domicilio, telefono, rfc) VALUES(19, 'Luis', 'Ramires', 'Diaz', 'M', 'Bolivia #410, Col. Obrera', '47712345679', 'RADL950402');
INSERT INTO persona (idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, domicilio, telefono, rfc) VALUES(20, 'Fabiola', 'Mendez', 'Paredes', 'F', 'Pedro Moreno #725, Col. Centro', '47712345670', 'MEPF800609');
INSERT INTO persona (idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, domicilio, telefono, rfc) VALUES(21, 'Ana Maria', 'Torres', 'Mocha', 'F', 'Blvd. Hidalgo #1150, Col. La Florida', '47712345671', 'TOMA980413');
INSERT INTO persona (idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, domicilio, telefono, rfc) VALUES(22, 'Anni', 'Leonhart', 'Valtierra', 'F', 'Paseo de los Gavilanes #233, Col. San Isidro', '47712345672', 'LEVA900719');
INSERT INTO persona (idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, domicilio, telefono, rfc) VALUES(23, 'Mario', 'Maldiva', 'Morales', 'M', 'Blas Galindo #400, Col. Bosques', '47712345673', 'MAMM900606');
INSERT INTO persona (idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, domicilio, telefono, rfc) VALUES(24, 'Raul', 'Arboleda', 'Cortes', 'M', 'Rio Mayo #208, Col. Cerrito de Jerez', '47712345674', 'ARCR891117');


--	Usuarios [usuario]:
-- Usuario Administrador:
INSERT INTO usuario (idUsuario, nombreUsuario, contrasenia, rol) VALUES(1, 'admin', 'admin', 'Administrador');
-- Empleado
INSERT INTO usuario (idUsuario, nombreUsuario, contrasenia, rol) VALUES(11, 'dani', 'u1234', 'Colaborador');
INSERT INTO usuario (idUsuario, nombreUsuario, contrasenia, rol) VALUES(12, 'paula', 'u1235', 'Colaborador');
INSERT INTO usuario (idUsuario, nombreUsuario, contrasenia, rol) VALUES(13, 'evelin', 'u1237', 'Colaborador');
INSERT INTO usuario (idUsuario, nombreUsuario, contrasenia, rol) VALUES(14, 'sergio', 'u1236', 'Colaborador');
INSERT INTO usuario (idUsuario, nombreUsuario, contrasenia, rol) VALUES(15, 'radc', 'u1238', 'Colaborador');
INSERT INTO usuario (idUsuario, nombreUsuario, contrasenia, rol) VALUES(16, 'sumf', 'u1239', 'Colaborador');
INSERT INTO usuario (idUsuario, nombreUsuario, contrasenia, rol) VALUES(17, 'loaa', 'u1240', 'Colaborador');
-- Cliente
INSERT INTO usuario (idUsuario, nombreUsuario, contrasenia, rol) VALUES(18, 'sarr', 'u1250', 'Cliente');
INSERT INTO usuario (idUsuario, nombreUsuario, contrasenia, rol) VALUES(19, 'radl', 'u1252', 'Cliente');
INSERT INTO usuario (idUsuario, nombreUsuario, contrasenia, rol) VALUES(20, 'mepf', 'u1253', 'Cliente');
INSERT INTO usuario (idUsuario, nombreUsuario, contrasenia, rol) VALUES(21, 'toma', 'u1254', 'Cliente');
INSERT INTO usuario (idUsuario, nombreUsuario, contrasenia, rol) VALUES(22, 'leva', 'u1255', 'Cliente');
INSERT INTO usuario (idUsuario, nombreUsuario, contrasenia, rol) VALUES(23, 'mamm', 'u1256', 'Cliente');
INSERT INTO usuario (idUsuario, nombreUsuario, contrasenia, rol) VALUES(24, 'arcr', 'u1251', 'Cliente');


-- Datos de Empleados [empleado]:
-- Empleado Virtual del Administrador:
INSERT INTO colaborador (idColaborador, numeroColaborador, puesto, estatus, foto, rutaFoto, idPersona, idUsuario) VALUES(1, '', 'Administrador', 1, '', '', 1, 1);
--  
INSERT INTO colaborador (idColaborador, numeroColaborador, puesto, estatus, foto, rutaFoto, idPersona, idUsuario) VALUES(2, 'CBSAAD20220310', 'Colaborador', 1, '', '', 11, 11);
INSERT INTO colaborador (idColaborador, numeroColaborador, puesto, estatus, foto, rutaFoto, idPersona, idUsuario) VALUES(3, 'CBVAPP20220310', 'Colaborador', 1, '', '', 12, 12);
INSERT INTO colaborador (idColaborador, numeroColaborador, puesto, estatus, foto, rutaFoto, idPersona, idUsuario) VALUES(4, 'CBGAXE20220310', 'Colaborador', 1, '', '', 13, 13);
INSERT INTO colaborador (idColaborador, numeroColaborador, puesto, estatus, foto, rutaFoto, idPersona, idUsuario) VALUES(5, 'CBALAS20220310', 'Colaborador', 1, '', '', 14, 14);
INSERT INTO colaborador (idColaborador, numeroColaborador, puesto, estatus, foto, rutaFoto, idPersona, idUsuario) VALUES(6, 'CBRADC20220310', 'Colaborador', 1, '', '', 15, 15);
INSERT INTO colaborador (idColaborador, numeroColaborador, puesto, estatus, foto, rutaFoto, idPersona, idUsuario) VALUES(7, 'CBSUMF20220310', 'Colaborador', 1, '', '', 16, 16);
INSERT INTO colaborador (idColaborador, numeroColaborador, puesto, estatus, foto, rutaFoto, idPersona, idUsuario) VALUES(8, 'CBLOAA20220310', 'Colaborador', 1, '', '', 17, 17);


-- Datos de Clientes [cliente]:
INSERT INTO cliente (idCliente, numeroUnico, correo, estatus, idPersona, idUsuario) VALUES (1, 'CLSARR20220312', 'rena@email.com', 1, 18, 18);
INSERT INTO cliente (idCliente, numeroUnico, correo, estatus, idPersona, idUsuario) VALUES (2, 'CLRADL20220312', 'luis@email.com', 1, 19, 19);
INSERT INTO cliente (idCliente, numeroUnico, correo, estatus, idPersona, idUsuario) VALUES (3, 'CLMEPF20220313', 'fabiola@email.com', 1, 20, 20);
INSERT INTO cliente (idCliente, numeroUnico, correo, estatus, idPersona, idUsuario) VALUES (4, 'CLTOMA20220314', 'ana@email.com', 1, 21, 21);
INSERT INTO cliente (idCliente, numeroUnico, correo, estatus, idPersona, idUsuario) VALUES (5, 'CLLEVA20220314', 'anni@email.com', 1, 22, 22);
INSERT INTO cliente (idCliente, numeroUnico, correo, estatus, idPersona, idUsuario) VALUES (6, 'CLMAMM20220316', 'mario@email.com', 1, 23, 23);
INSERT INTO cliente (idCliente, numeroUnico, correo, estatus, idPersona, idUsuario) VALUES (7, 'CLARCR20220317', 'raul@email.com', 1, 24, 24);


-- Datos de Material [material]
INSERT INTO material (idMaterial, nombre, marca, estatus, precio) VALUES(1, 'Espatula', 'Torres', 1, 60);
INSERT INTO material (idMaterial, nombre, marca, estatus, precio) VALUES(2, 'Desarmador Plano', 'TRUPER', 1, 50);
INSERT INTO material (idMaterial, nombre, marca, estatus, precio) VALUES(3, 'Desarmador Cruz', 'TRUPER', 1, 50);
INSERT INTO material (idMaterial, nombre, marca, estatus, precio) VALUES(4, 'Martillo', 'TRUPER', 1, 98);
INSERT INTO material (idMaterial, nombre, marca, estatus, precio) VALUES(5, 'Brocha', 'Patito', 1, 68);
INSERT INTO material (idMaterial, nombre, marca, estatus, precio) VALUES(6, 'Botes', 'TRUPER', 1, 10);


--      Datos de horarios [horario]
INSERT INTO horario (idHorario, horaInicio, horaFin) VALUES(1, '09:00:00', '10:30:00');
INSERT INTO horario (idHorario, horaInicio, horaFin) VALUES(2, '10:30:00', '12:00:00');
INSERT INTO horario (idHorario, horaInicio, horaFin) VALUES(3, '12:00:00', '13:30:00');
INSERT INTO horario (idHorario, horaInicio, horaFin) VALUES(4, '13:30:00', '15:00:00');
INSERT INTO horario (idHorario, horaInicio, horaFin) VALUES(5, '15:00:00', '16:30:00');
INSERT INTO horario (idHorario, horaInicio, horaFin) VALUES(6, '16:30:00', '18:00:00');
INSERT INTO horario (idHorario, horaInicio, horaFin) VALUES(7, '18:00:00', '19:30:00');

--      Datos de Reparacion [reparacion]
INSERT INTO reparacion (idReparacion, nombre, costo, tipoReparacion, descripcion, estatus) VALUES(1, '-', 48, 'Fontanería', '-', 1);
INSERT INTO reparacion (idReparacion, nombre, costo, tipoReparacion, descripcion, estatus) VALUES(2, '-', 90, 'Calefacción', '-', 1);
INSERT INTO reparacion (idReparacion, nombre, costo, tipoReparacion, descripcion, estatus) VALUES(3, '-', 60, 'Electricidad', '-', 1);
INSERT INTO reparacion (idReparacion, nombre, costo, tipoReparacion, descripcion, estatus) VALUES(4, '-', 140, 'Montaje de muebles', '-', 1);
INSERT INTO reparacion (idReparacion, nombre, costo, tipoReparacion, descripcion, estatus) VALUES(5, '-', 150, 'Carpintería', '-', 1);
INSERT INTO reparacion (idReparacion, nombre, costo, tipoReparacion, descripcion, estatus) VALUES(6, '-', 250, 'Pequeñas reparaciones', '-', 1);
INSERT INTO reparacion (idReparacion, nombre, costo, tipoReparacion, descripcion, estatus) VALUES(7, '-', 39, 'Pintura', '-', 1);
INSERT INTO reparacion (idReparacion, nombre, costo, tipoReparacion, descripcion, estatus) VALUES(8, '-', 110, 'Ventanas', '-', 1);


--      Datos para la vista servicio [servicio]
INSERT INTO servicio(fecha, idCita, idColaborador) VALUES ('2022-04-08 01:15:00', 1, 3);
INSERT INTO servicio_reparacion(idReparacion, idServicio) VALUES(1, 1);
INSERT INTO servicio_reparacion_material(idServicioReparacion, idMaterial, precio) VALUES(1, 1, 60);
INSERT INTO servicio_reparacion_material(idServicioReparacion, idMaterial, precio) VALUES(1, 3, 50);

-- 2
INSERT INTO servicio(fecha, idCita, idColaborador) VALUES ('2022-10-10 02:15:00', 2, 2);
INSERT INTO servicio_reparacion(idReparacion, idServicio) VALUES(2, 2);
INSERT INTO servicio_reparacion_material(idServicioReparacion, idMaterial, precio) VALUES(2, 2, 50);
INSERT INTO servicio_reparacion_material(idServicioReparacion, idMaterial, precio) VALUES(2, 4, 98);