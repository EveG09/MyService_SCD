/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var reparaciones = [];

function initializeSearcher() {
    //Codigo para implementar el filtro en la tabla
    $("#txtBuscar").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        $("#tbodyReparacion tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
}

function refreshTable() {
    var contenido = '';
    $.ajax({
        type: "GET",
        url: "api/reparacion/getAll",
        data: {token: sessionStorage.getItem("token")}

    }).done(function (data) {
        //Revisamos si hubo algún error:
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
            reparaciones = data;

            //Recorremos el arreglo JSON de reparacion
            for (var i = 0; i < reparaciones.length; i++) {
                contenido = contenido + '<tr>' +
                        '<td>' + reparaciones[i].nombre + '</td>' +
                        '<td>' + '$' + reparaciones[i].costo + '</td>' +
                        '<td>' + reparaciones[i].tipoReparacion + '</td>' +
                        '<td>' + reparaciones[i].descripcion + '</td>' +
                        '<td><button class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#divDetalleReparacion" onclick="showDetail(' + reparaciones[i].idReparacion + ');"><i class="fa fa-eye"></i></button>' + '</td>' +
                        '</tr>';
            }
            // Insertamos el contenido de la tabla dentro de el cuerpo de la misma
            $("#tbodyReparacion").html(contenido);
        }
    });
}

function save() {
    var reparacion = new Object();

    reparacion.idReparacion = 0;
    reparacion.nombre = ($('#txtNombre').val());
    reparacion.costo = parseFloat(($('#txtCosto').val()));
    reparacion.tipoReparacion = (($('#txtTipo').val()));
    reparacion.descripcion = ($('#txtDescripción').val());
    reparacion.estatus = 1;

    $.ajax({
        type: "POST",
        async: true,
        url: "api/reparacion/save",
        data: {
            idReparacion: reparacion.idReparacion,
            nombre: reparacion.nombre,
            costo: reparacion.costo,
            tipoReparacion: reparacion.tipoReparacion,
            descripcion: reparacion.descripcion,
            token: sessionStorage.getItem("token")
        }
        
    }).done(function (data) {
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
            refreshTable();
            Swal.fire({
                title: 'Movimiento realizado',
                html: `<span>¡Los datos de la reparación se han guardado correctamente! <i class="fa-solid fa-thumbs-up"></i></span>`,
                icon: 'success',
                showClass: {
                    popup: 'animated pulse'
                }
            });
            cleanForm();
            $('#divAgregarReparacion').modal('hide');
        }
    });
}

function update() {
    var reparacion = new Object();

    reparacion.idReparacion = parseInt($("#txtCodigoD").val());
    reparacion.nombre = ($('#txtNombreD').val());
    reparacion.costo = parseFloat(($('#txtCostoD').val()));
    reparacion.tipoReparacion = (($('#txtTipoD').val()));
    reparacion.descripcion = ($('#txtDescripciónD').val());
    reparacion.estatus = 1;
    
    $.ajax({
        type: "POST",
        async: true,
        url: "api/reparacion/save",
        data: {
            idReparacion: reparacion.idReparacion,
            nombre: reparacion.nombre,
            costo: reparacion.costo,
            tipoReparacion: reparacion.tipoReparacion,
            descripcion: reparacion.descripcion,
            token: sessionStorage.getItem("token")
        }

    }).done(function (data) {
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
            refreshTable();
            reparacion = data;
            $('#txtCodigo').val(reparacion.idReparacion);
            Swal.fire({
                title: 'Movimiento realizado',
                html: `<span>¡Los datos de la reparación se han guardado correctamente! <i class="fa-solid fa-thumbs-up"></i></span>`,
                icon: 'success',
                showClass: {
                    popup: 'animated pulse'
                }
            });
            cleanForm();
            $('#divDetalleReparacion').modal('hide');
        }
    });
}

function remove() {
    var id = parseInt($('#txtCodigoD').val());

    $.ajax({
        type: "POST",
        url: "api/reparacion/delete",
        async: true,
        data: {
            idReparacion: id,
            token: sessionStorage.getItem("token")
        }
        
    }).done(function (data) {
        //Revisamos si se llegó a producir algún error:
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
            refreshTable();
            Swal.fire({
                title: 'Repración eliminada',
                html: `<span>` + data.result + `<i class="fa-solid fa-thumbs-up mx-2"></i></span>`,
                icon: 'success',
                showClass: {
                    popup: 'animated pulse'
                }
            });
            cleanForm();
            $('#divDetalleReparacion').modal('hide');
        }
    });
}

function searchRepairById(id) {
    //Recorremos el arreglo posicion por posicion
    for (var i = 0; i < reparaciones.length; i++) {
        //Comparamos si el ID de la reparacion en la posicion actual es el mismo que el buscado
        if (reparaciones[i].idReparacion === id)
        {
            // Devolvemos la posicion de la reparacion
            return i;
        }
    }
    return -1;
}

function showDetail(id) {
    // Buscamos la posicion de la reparacion
    var pos = searchRepairById(id);
    // Llenamos los campos del formulario
    $('#txtCodigoD').val(reparaciones[pos].idReparacion);
    $('#txtNombreD').val(reparaciones[pos].nombre);
    $('#txtCostoD').val(reparaciones[pos].costo);
    $('#txtTipoD').val(reparaciones[pos].tipoReparacion);
    $('#txtDescripciónD').val(reparaciones[pos].descripcion);
}

function cleanForm(){
    $('#txtCodigo').val("");
    $('#txtNombre').val("");
    $('#txtCosto').val("");
    $('#txtTipo').val("");
    $('#txtDescripción').val("");
    $('#txtCodigoD').val("");;
    $('#txtNombreD').val("");;
    $('#txtCostoD').val("");;
    $('#txtTipoD').val("");;
    $('#txtDescripciónD').val("");
}

function printTable() {
    var print = document.getElementById('divTablaReparacion').innerHTML;
    var contenido = document.body.innerHTML;

    document.body.innerHTML = print;
    window.print();
    document.body.innerHTML = contenido;
}