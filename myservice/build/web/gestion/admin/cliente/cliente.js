/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var clientes = [];

function initializeSearcher() {
    //Codigo para implementar el filtro en la tabla
    $("#txtBuscar").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        $("#tbodyCliente tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
    refreshTable();
}

function save() {
    // Generamos un nuevo objeto
    var cliente = new Object();
    cliente.persona = new Object();
    cliente.usuario = new Object();
    
    cliente.id = 0;
    cliente.persona.nombre = $('#txtNombre').val();
    cliente.persona.apellidoPaterno = $('#txtApellidoPaterno').val();
    cliente.persona.apellidoMaterno = $('#txtApellidoMaterno').val();
    cliente.correo = $('#txtCorreo').val();
    cliente.persona.telefono = parseInt($('#txtTelefono').val());
    cliente.persona.genero = $('#cmbGenero').val();
    cliente.persona.rfc = ($('#txtRfc').val());
    cliente.persona.domicilio = $('#txtDomicilio').val();
    cliente.usuario.nombreUsuario = $('#txtUsuario').val();
    cliente.usuario.contrasenia = $('#txtContrasenia').val();
    cliente.usuario.rol = "Cliente";
    cliente.numeroUnico = $('#txtNumeroCliente').val();
    cliente.estatus = 1;
    
    $.ajax({
        type: "POST",
        async: true,
        url: "api/cliente/save",
        data: {cliente: JSON.stringify(cliente),
            token: sessionStorage.getItem("token")}

    }).done(function (data) {
        //Revisamos si hubo algun error:
        if (data.error != null) {
            Swal.fire({
                title: '¡¡¡ Alerta !!!',
                html: `<span>` + data.error + `<i class="fa-solid fa-face-angry mx-1"></i></span>`,
                icon: 'warning',
                showClass: {
                    popup: 'animate__animated animate__tada',
                    icon: 'animate__animated animate__heartBeat'
                }
            });
            
        } else {
            refrescarTabla();
            cliente = data;
            $('#txtCodigoCliente').val(cliente.id);
            $('#txtCodigoPersona').val(cliente.persona.id);
            $('#txtCodigoUsuario').val(cliente.usuario.id);
            $('#txtNumeroCliente').val(cliente.numeroUnico);

            Swal.fire('Movimiento realizado', 'Los datos del cliente se han guardado correctamente.', 'success');
            limpiarFormulario();
        }
    });
}

function refreshTable() {
    var contenido = "";
    
    // Hacemos la petición al Servicio REST que nos consulta los clientes:
    $.ajax({
        type: "GET",
        url: "api/cliente/getAll",
        data: {token: sessionStorage.getItem("token")}

    }).done(function (data) {
        //Revisamos si hubo algun error:
        if (data.error != null) {
            Swal.fire({
                title: '¡¡¡ Alerta !!!',
                html: `<span>` + data.error + `<i class="fa-solid fa-face-angry mx-1"></i></span>`,
                icon: 'warning',
                showClass: {
                    popup: 'animate__animated animate__tada',
                    icon: 'animate__animated animate__heartBeat'
                }
            });
            
        } else {
            clientes = data;

            //Recorremos el arreglo de clientes posición por posición:
            for (var i = 0; i < clientes.length; i++) {
            // Agregamos un nuevo renglon a la tabla contenido sus respectivas columnas y valores:
            contenido = contenido + '<tr>' +
                                    '<td>' + clientes[i].numeroUnico + '</td>' +
                                    '<td>' + clientes[i].persona.nombre + ' ' + clientes[i].persona.apellidoPaterno + ' ' +
                                             clientes[i].persona.apellidoMaterno + ' ' +
                                    '</td>' +
                                    '<td>' + clientes[i].persona.rfc + '</td>' +
                                    '<td>' + clientes[i].persona.domicilio + '</td>' +
                                    '<td><button class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#divDetalleCliente" onclick="showDetail(' + clientes[i].id + ');"><i class="far fa-eye"></i></button>' + '</td>' +
                                    '</tr>';
            }
            //Insertamos el contenido generado previamente dentro del cuerpo de la tabla:
            $('#tbodyCliente').html(contenido);
        }
    });
}

function showDetail(idCliente) {
    var pos = searchClienteById(idCliente);
    
    // Datos de Persona:
    $('#txtDetalleCodigoCliente').val(clientes[pos].id);
    $('#txtDetalleCodigoPersona').val(clientes[pos].persona.id);
    $('#txtDetalleCodigoUsuario').val(clientes[pos].usuario.id);
    $('#txtDetalleNombre').val(clientes[pos].persona.nombre);
    $('#txtDetalleApellidoPaterno').val(clientes[pos].persona.apellidoPaterno);
    $('#txtDetalleApellidoMaterno').val(clientes[pos].persona.apellidoMaterno);
    $('#txtDetalleDomicilio').val(clientes[pos].persona.domicilio);
    $('#txtDetalleTelefono').val(clientes[pos].persona.telefono);
    $('#txtDetalleRfc').val(clientes[pos].persona.rfc);
    $('#txtDetalleCorreo').val(clientes[pos].correo);
    $('#cmbDetalleGenero').val(clientes[pos].persona.genero);
    
    // Datos de Usuario:
    $('#txtDetalleUsuario').val(clientes[pos].usuario.nombreUsuario);
    $('#txtDetalleContrasenia').val(clientes[pos].usuario.contrasenia);
    $('#txtDetalleRol').val(clientes[pos].usuario.rol);

    //Datos del Cliente:
    $('#txtDetalleNumeroCliente').val(clientes[pos].numeroUnico);
}

function searchClienteById(id) {
    for (var i = 0; i < clientes.length; i++) {
        if (clientes[i].id === id) {
            return i;
        }
    }
    return -1;
}

function cleanForm() {    
    // Datos de Persona:
    $('#txtCodigoCliente').val('');
    $('#txtCodigoPersona').val('');
    $('#txtCodigoUsuario').val('');
    $('#txtNombre').val('');
    $('#txtApellidoPaterno').val('');
    $('#txtApellidoMaterno').val('');
    $('#txtDomicilio').val('');
    $('#txtTelefono').val('');
    $('#txtRfc').val('');
    $('#txtCorreo').val('');
    $('#cmbGenero').val('');
    
    // Datos de Uusario:
    $('#txtUsuario').val('');
    $('#txtContrasenia').val('');
    $('#txtRol').val('');

    // Datos de Cliente:
    $('#txtNumeroCliente').val('');
}

function printTable() {
    var print = document.getElementById('divTablaCliente').innerHTML;
    var contenido = document.body.innerHTML;

    document.body.innerHTML = print;
    window.print();
    document.body.innerHTML = contenido;
}