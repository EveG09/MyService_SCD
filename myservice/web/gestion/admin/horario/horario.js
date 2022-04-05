/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var horarios = [];

function initializeSearcher() {
    //Codigo para implementar el filtro en la tabla
    $("#txtBuscar").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        $("#tbodyHorario tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
    refreshTable();
}

function save() {
    if ($('#txtHoraInicio').val() === '' || $('#txtHoraFinalizacion').val() === '') {
        Swal.fire({
            title: '¡¡¡ Alerta !!!',
            html: `<span>¡No se ha seleccionado ningún registro de horario!<i class="fa-solid fa-face-angry mx-1"></i></span>`,
            icon: 'warning',
            showClass: {
                popup: 'animate__animated animate__tada',
                icon: 'animate__animated animate__heartBeat'
            }
        });

    } else {
        var horario = new Object();

        horario.id = 0;
        horario.horaInicio = ($('#txtHoraInicio').val());
        horario.horaFin = ($('#txtHoraFinalizacion').val());

        $.ajax({
            type: "POST",
            url: "api/horario/save",
            data: {
                idHorario: horario.id,
                horaInicio: horario.horaInicio,
                horaFin: horario.horaFin, 
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
                    html: `<span>¡Los datos de horario se han guardado correctamente! <i class="fa-solid fa-thumbs-up"></i></span>`,
                    icon: 'success',
                    showClass: {
                        popup: 'animate__animated animate__pulse'
                    }
                });
                clearForm();
                $('#divAgregarHorario').modal('hide');
            }
        });
    }
}

function update() {
    var horario = new Object();

    horario.id = parseInt($('#txtDetalleCodigoH').val());
    horario.horaInicio = ($('#txtDetalleHoraInicio').val());
    horario.horaFin = ($('#txtDetalleHoraFinalizacion').val());

    $.ajax({
        type: "POST",
        url: "api/horario/save",
        data: {
            idHorario: horario.id,
            horaInicio: horario.horaInicio,
            horaFin: horario.horaFin,
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
                html: `<span>¡Los datos de horario se han guardado correctamente! <i class="fa-solid fa-thumbs-up"></i></span>`,
                icon: 'success',
                showClass: {
                    popup: 'animate__animated animate__pulse'
                }
            });
            clearForm();
            $('#divDetalleHorario').modal('hide');
        }
    });
}

function remove() {
    // Declaramos la variable para obtener el ID del producto a eliminar
    var id = 0;
    id = parseInt($('#txtDetalleCodigoH').val());
    
    $.ajax({
        type: "POST",
        url: "api/horario/delete",
        data: {
                idHorario: id,
                token: sessionStorage.getItem("token")
        }
        
    }).done(function (data) {
        // Revisamos si se llegó a producir algún error:
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
                title: 'Horario eliminado',
                html: `<span>` + data.result + `<i class="fa-solid fa-thumbs-up mx-2"></i></span>`,
                icon: 'success',
                showClass: {
                    popup: 'animate__animated animate__pulse'
                }
            });
            clearForm();
            $('#divDetalleHorario').modal('hide');
        }
    });
}

function refreshTable() {
    //Esta variable contendra el contenido HTML de la tabla
    var contenido = '';

    $.ajax({
        type: "GET",
        url: "api/horario/getAll",
        data: {token: sessionStorage.getItem("token")}
        
    }).done(function (data) {
        
        //vRevisamos si hubo algun error:
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
            horarios = data;
            
            // Recorremos el arreglo JSON de horariod
            for (var i = 0; i < data.length; i++) {
                contenido = contenido + '<tr>' +
                        '<td>' + horarios[i].horaInicio + '</td>' +
                        '<td>' + horarios[i].horaFin + '</td>' +
                        '<td><button class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#divDetalleHorario" onclick="showDetail(' + horarios[i].id + ');"><i class="fa fa-eye"></i></button>' + '</td>' +
                        '</tr>';
            }
            // Insertamos el contenido de la tabla dentro de su cuerpo
            $('#tbodyHorario').html(contenido);
        }
    });
}

function showDetail(idHorario) {
    // Buscamos la posicion del Horario
    var pos = searchHorarioById(idHorario);
    
    // Llenamos los campos del formulario
    $('#txtDetalleCodigoH').val(horarios[pos].id);
    $('#txtDetalleHoraInicio').val(horarios[pos].horaInicio);
    $('#txtDetalleHoraFinalizacion').val(horarios[pos].horaFin);
}

function searchHorarioById(id) {
    // Recorremos el arreglo posicion por posicion
    for (var i = 0; i < horarios.length; i++) {
        // Comparamos si el ID del producto en la posicion actual es el mismo que el buscado
        if (horarios[i].id === id) {
            // Devolvemos la posicion del producto
            return i;
        }
    }
    return -1;
}

function clearForm() {
    $('#txtCodigoH').val('');
    $('#txtHoraInicio').val('');
    $('#txtHoraFinalizacion').val('');
}

function printTable() {
    var print = document.getElementById('divTablaHora').innerHTML;
    var contenido = document.body.innerHTML;

    document.body.innerHTML = print;
    window.print();
    document.body.innerHTML = contenido;
}
