/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var citas = [];
var clientes = [];
var horarios = [];
var reparaciones = [];

function initializeModuleAppointment() {
    //Codigo para implementar el filtro en la tabla
    $("#txtBuscar").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        $("#tbodyCita tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
}

function printTable() {
    var print = document.getElementById('divTablaCita').innerHTML;
    var contenido = document.body.innerHTML;

    document.body.innerHTML = print;
    window.print();
    document.body.innerHTML = contenido;

}

function getAllAppointment() {
    var contenido = '';
    $.ajax({
        type: "GET",
        url: "api/cita/getAll"
    })
            .done(function (data) {
                //Revisamos si hubo algún error:
                if (data.error != null)
                {
                    Swal.fire('Error', data.error, 'warning');
                } else {
                    citas = data;


                    //Recorremos el arreglo JSON de Citas
                    for (var i = 0; i < citas.length; i++)
                    {


                        contenido = contenido + '<tr>' +
                                '<td>' + citas[i].fechaCita + '</td>' +
                                '<td>' + citas[i].cliente.persona.nombre + " " + citas[i].cliente.persona.apellidoPaterno + " " +
                                citas[i].cliente.persona.apellidoMaterno + '</td>' +
                                '<td>' + citas[i].reparacion.tipoReparacion + '</td>' +
                                '<td>' + citas[i].horario.horaInicio + " - " + citas[i].horario.horaFin + '</td>' +
                                '<td><button class="btn btn-outline-danger" onclick ="cancelAppointment(' + citas[i].idCita + ')" id="estiloInputText"><i class="fa fa-trash  mx-1"></i>Cancelar</button>' + '</td>';
                        '</tr>';
                    }

                    //Insertamos el contenido de la tabla dentro de el cuerpo de la misma
                    $("#tbodyCita").html(contenido);
                }
            });
}

function getAllAppointmentAttended() {
   var contenido = '';
    $.ajax({
        type: "GET",
        url: "api/cita/getAllAttended"
    })
            .done(function (data) {
                //Revisamos si hubo algún error:
                if (data.error != null)
                {
                    Swal.fire('Error', data.error, 'warning');
                } else {
                    citas = data;


                    //Recorremos el arreglo JSON de Citas
                    for (var i = 0; i < citas.length; i++)
                    {


                        contenido = contenido + '<tr>' +
                                '<td>' + citas[i].fechaCita + '</td>' +
                                '<td>' + citas[i].cliente.persona.nombre + " " + citas[i].cliente.persona.apellidoPaterno + " " +
                                citas[i].cliente.persona.apellidoMaterno + '</td>' +
                                '<td>' + citas[i].reparacion.tipoReparacion + '</td>' +
                                '<td>' + citas[i].horario.horaInicio + '</td>' +
                                '<td><span style="color: green">Atendida</span>' + '</td>' +
                                
                        '</tr>';
                    }

                    //Insertamos el contenido de la tabla dentro de el cuerpo de la misma
                    $("#tbodyCita").html(contenido);
                }
            });
}

function getAllAppointmentCancelled() {
   var contenido = '';
    $.ajax({
        type: "GET",
        url: "api/cita/getAllCancelled"
    })
            .done(function (data) {
                //Revisamos si hubo algún error:
                if (data.error != null)
                {
                    Swal.fire('Error', data.error, 'warning');
                } else {
                    citas = data;


                    //Recorremos el arreglo JSON de Citas
                    for (var i = 0; i < citas.length; i++)
                    {


                        contenido = contenido + '<tr>' +
                                '<td>' + citas[i].fechaCita + '</td>' +
                                '<td>' + citas[i].cliente.persona.nombre + " " + citas[i].cliente.persona.apellidoPaterno + " " +
                                citas[i].cliente.persona.apellidoMaterno + '</td>' +
                                '<td>' + citas[i].reparacion.tipoReparacion + '</td>' +
                                '<td>' + citas[i].horario.horaInicio + '</td>' +
                                '<td><span style="color: red">Cancelada</span>' + '</td>' +
                                
                        '</tr>';
                    }

                    //Insertamos el contenido de la tabla dentro de el cuerpo de la misma
                    $("#tbodyCita").html(contenido);
                }
            });
}

function searchClientAppointment() {
    var contenido = "";

    // Elemento visual de la búsqueda del cliente para la reservación txtSearch:
    var buscarCliente = $('#txtBuscarCliente').val();

    $.ajax({
        type: "GET",
        url: "api/cliente/getAllByNombreCliente",
        data: {nombre: buscarCliente,
            token: sessionStorage.getItem("token")}

    }).done(function (data) {
        clientes = data;

        //Recorremos el arreglo de clientes posición por posición:
        for (var i = 0; i < data.length; i++) {
            var nombreCompleto = clientes[i].persona.nombre + " " + clientes[i].persona.apellidoPaterno + " " +
                    clientes[i].persona.apellidoMaterno;

            // Agregamos un nuevo renglon a la tabla contenido sus respectivas columnas y valores:
            contenido = contenido + '<tr>' +
                    '<td>' + nombreCompleto + '</td>' +
                    '<td><button class="btn btn-outline-primary" onclick="selectClient(' + clientes[i].id + ');"><i class="fas fa-plus"></i>&nbspElegir</button>' + '</td>' +
                    '</tr>';
        }
        $('#tbodyClientes').html(contenido);
        $('#divTablaClientes').show();
        consultRepair();
    });
}

function selectClient(idCliente) {
    var pos = searchPositionCustomerByID(idCliente);

    var nombreCliente = clientes[pos].persona.nombre + " " + clientes[pos].persona.apellidoPaterno + " " +
            clientes[pos].persona.apellidoMaterno;

    $('#txtNombreCliente').val(nombreCliente);
    $('#txtCodigoCliente').val(clientes[pos].id);
    
    $('#divTablaClientes').modal('hide');
}

function searchPositionCustomerByID(id) {
    // Recorremos el arreglo posición por posición:
    for (var i = 0; i < clientes.length; i++) {

        // Comparamos si el ID del empleado en la posición actual es el mismo que el buscado:
        if (clientes[i].id === id) {
            return i;
        }
    }
    // Si llegamos a este punto significa que no encontramos un empleado con el ID especificado, 
    // en cuyo caso devolvemos el valor -1:
    return -1;
}

function consultRepair() {
// Esta variable contendrá el contenido HTML del nombre de las Reparaciones.
    var contenido = '';

// Hacemos la petición al servicio REST que nos consulta las Reparaciones:
    $.ajax({
        type: "GET",
        url: "api/reparacion/getAll",
        data: {token: sessionStorage.getItem("token")}

    }).done(function (data) {
        reparaciones = data;

        // Recorreremos el arreglo de productos posición por posición:
                for (var i = 0; i < reparaciones.length; i++) {
        // Agregamos el valor en el combobox del respectivo valor del nombre de las reparaciones:
                    contenido = contenido + '<option value="' + reparaciones[i].idReparacion + '">' + reparaciones[i].nombre +
                            '</option>';
                }
        // Insertamos el contenido de la tabla dentro de su cuerpo de la tabla
        $('#cmbReparacion').html(contenido);
    });
}

function consultSchedules() {
    var fecha = $('#txtFechaCita').val();
    
    // Esta variable contendrá el contenido HTML de la tabla
    var contenido = '';

    // Hacemos la peticion al servicio REST que nos consulta las sucursales y salas:
    $.ajax({
        type: "GET",
        url: "api/cita/getAllHorarios",
        data: {
            fecha: fecha,
            token: sessionStorage.getItem("token")
        }

    }).done(function (data) {
        //Revisamos si susedio algun error
        if (data.error != null) {
            Swal.fire('Error', data.error, 'warning');

        } else {
            horarios = data;

            // Recorreremos el arreglo de productos posición por posición:
            for (var i = 0; i < horarios.length; i++) {
                // Agregamos un nuevo renglón a la tabla contenido sus respectivas columnas y valores:
                contenido = contenido + '<option value="' + horarios[i].id + '">' + horarios[i].horaInicio + " - " + horarios[i].horaFin +
                        '</option>';
            }
            // Insertamos el contenido de la tabla dentro de su cuerpo de la tabla
            $('#cmbHorarios').html(contenido);
        }
    });
}

function save() {
    var estatus = 1;
    var idCliente = $('#txtCodigoCliente').val();
    var idReparacion = $('#cmbReparacion').val();
    var fechaCita = $('#txtFechaCita').val();
    var idHorario = $('#cmbHorarios').val();

    $.ajax({
        type: "GET",
        url: "api/cita/insert",
        data: {
                estatus         : estatus,
                idCliente       : idCliente,
                idReparacion    : idReparacion,
                fechaCita       : fechaCita,
                idHorario       : idHorario,
                token           : sessionStorage.getItem("token")
              }
    
    }).done(function (data) {
        //Revisamos si susedio algun error
        if (data.error != null) {
            Swal.fire('¡¡¡ Alerta !!!', data.error, 'warning');

        } else if (data.exception != null) {
            Swal.fire('¡¡¡ Alerta !!!', data.exception, 'warning');
        
        } else if(data.result != null) {
            getAllAppointment();
            Swal.fire('Movimiento realizado', data.result, 'success');
            clearForm();
            $('#divAgregarCita').modal('hide');
        }
    });
}

function cancelAppointment(idCita) {
    var id = parseInt(idCita);
    $.ajax({
        type: "GET",
        url: "api/cita/delete",
        data: {
            idCita: id,
            token : sessionStorage.getItem("token")
        }
    }).done(function (data) {
        //Revisamos si se llegó a producir algún error:
        if (data.error != null) {
            Swal.fire('Error', data.error, 'warning');

        } else {
            Swal.fire('Cita Cancelada', data.result, 'success');
            getAllAppointment();
        }
    });

}

function clearForm() {
    $('#txtBuscarCliente').val('');
    $('#txtNombreCliente').val('');
    $('#txtCodigoCliente').val('');
    $('#cmbReparacion').val('');
    $('#txtFechaCita').val('');
    $('#cmbHorarios').val('');
}