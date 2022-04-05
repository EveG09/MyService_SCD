/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var materiales= [];

function initializeSearcher() {
    //Codigo para implementar el filtro en la tabla
    $("#txtBuscar").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        $("#tbodyMaterial tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
    refreshTable();
}

function save() {
    if ($('#txtNombre').val() === '' || $('#txtMarca').val() === '' || $('#txtPrecio').val() === '') {
        Swal.fire({
            title: '¡¡¡ Alerta !!!',
            html: `<span>¡No se ha seleccionado ningún registro de material!<i class="fa-solid fa-face-angry mx-1"></i></span>`,
            icon: 'warning'
        });

    } else {
        var material = new Object();

        material.id = 0;
        material.nombre = ($('#txtNombre').val());
        material.marca = ($('#txtMarca').val());
        material.precio = ($('#txtPrecio').val());
        material.estatus = 1;

        $.ajax({
            type: "POST",
            url: "api/material/save",
            data: {
                idMaterial: material.id,
                nombre: material.nombre,
                marca: material.marca,
                precio: material.precio,
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
                    html: `<span>¡Los datos del material se han guardado correctamente! <i class="fa-solid fa-thumbs-up"></i></span>`,
                    icon: 'success',
                    showClass: {
                        popup: 'animate__animated animate__pulse'
                    }
                });
                clearForm();
                $('#divAgregarMaterial').modal('hide');
            }
        });
    }
}

function update() {
    var material = new Object();

    material.id = parseInt($('#txtDetalleCodigo').val());
    material.nombre = ($('#txtDetalleNombre').val());
    material.marca = ($('#txtDetalleMarca').val());
    material.precio = ($('#txtDetallePrecio').val());
    material.estatus = 1;

    $.ajax({
        type: "POST",
        url: "api/material/save",
        data: {
            idMaterial: material.id,
            nombre: material.nombre,
            marca: material.marca,
            precio: material.precio,
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
                html: `<span>¡Los datos del material se han guardado correctamente! <i class="fa-solid fa-thumbs-up"></i></span>`,
                icon: 'success',
                showClass: {
                    popup: 'animate__animated animate__pulse'
                }
            });
            clearForm();
            $('#divDetalleMaterial').modal('hide');
        }
    });
}

function remove() {
    // Declaramos la variable para obtener el ID del material a eliminar
    var id = parseInt($('#txtDetalleCodigo').val());
    
    $.ajax({
        type: "POST",
        url: "api/material/delete",
        async: true,
        data: {
                idMaterial: id,
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
                title: 'Material eliminado',
                html: `<span>` + data.result + `<i class="fa-solid fa-thumbs-up mx-2"></i></span>`,
                icon: 'success',
                showClass: {
                    popup: 'animate__animated animate__pulse'
                }
            });
            clearForm();
            $('#divDetalleMaterial').modal('hide');
        }
    });
}

function refreshTable() {
    //Esta variable contendra el contenido HTML de la tabla
    var contenido = '';

    $.ajax({
        type: "GET",
        url: "api/material/getAll",
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
            materiales = data;
            
            // Recorremos el arreglo JSON de materiald
            for (var i = 0; i < data.length; i++) {
                contenido = contenido + '<tr>' +
                        '<td>' + materiales[i].nombre + '</td>' +
                        '<td>' + materiales[i].marca + '</td>' +
                        '<td>' + materiales[i].precio + '</td>' +
                        '<td><button class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#divDetalleMaterial" onclick="showDetail(' + materiales[i].id + ');"><i class="fa fa-eye"></i></button>' + '</td>' +
                        '</tr>';
            }
            // Insertamos el contenido de la tabla dentro de su cuerpo
            $('#tbodyMaterial').html(contenido);
        }
    });
}

function showDetail(idMaterial) {
    // Buscamos la posicion del Material
    var pos = searchMaterialById(idMaterial);
    
    // Llenamos los campos del formulario
    $('#txtDetalleCodigo').val(materiales[pos].id);
    $('#txtDetalleNombre').val(materiales[pos].nombre);
    $('#txtDetalleMarca').val(materiales[pos].marca);
    $('#txtDetallePrecio').val(materiales[pos].precio);
}

function searchMaterialById(id) {
    // Recorremos el arreglo posicion por posicion
    for (var i = 0; i < materiales.length; i++) {
        // Comparamos si el ID del producto en la posicion actual es el mismo que el buscado
        if (materiales[i].id === id) {
            // Devolvemos la posicion del producto
            return i;
        }
    }
    return -1;
}

function clearForm() {
    $('#txtCodigo').val('');
    $('#txtNombre').val('');
    $('#txtMarca').val('');
    $('#txtPrecio').val('');
}

function printTable() {
    var print = document.getElementById('divTablaMaterial').innerHTML;
    var contenido = document.body.innerHTML;

    document.body.innerHTML = print;
    window.print();
    document.body.innerHTML = contenido;
}
