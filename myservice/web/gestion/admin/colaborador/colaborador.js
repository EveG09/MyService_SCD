/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var colaboradores = [];

function save() {
    // Creamos un nuevo objeto
    var colaborador = new Object();
    colaborador.persona = new Object();
    colaborador.usuario = new Object();
  
    colaborador.id = 0;
    colaborador.persona.nombre = $('#txtNombre').val();
    colaborador.persona.apellidoPaterno = $('#txtApellidoPaterno').val();
    colaborador.persona.apellidoMaterno = $('#txtApellidoMaterno').val();
    colaborador.persona.genero = $('#cmbGenero').val();
    colaborador.persona.domicilio = $('#txtDomicilio').val();
    colaborador.persona.telefono = $('#txtTelefono').val();
    colaborador.persona.rfc = $('#txtRfc').val();
    colaborador.usuario.nombreUsuario = $('#txtUsuario').val();
    colaborador.usuario.contrasenia = $('#txtContrasenia').val();
    colaborador.usuario.rol = "Colaborador";
    colaborador.numeroColaborador = $('#txtNumeroColaborador').val();
    colaborador.puesto = $('#txtPuesto').val();
    colaborador.descripcion = $('#txtDescripcion').val();
    colaborador.estatus = 1;
    colaborador.foto = $('#txtCodigoImagen').val();
    colaborador.rutaFoto = $('#txtCodigoImagen').val();

    $.ajax({
        type: "POST",
        async: true,
        url: "api/colaborador/save",
        data: {colaborador: JSON.stringify(colaborador),
               token: sessionStorage.getItem("token")}

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
            colaborador = data;
            $('#txtCodigoColaborador').val(colaborador.id);
            $('#txtCodigoPersona').val(colaborador.persona.id);
            $('#txtCodigoUsuario').val(colaborador.usuario.id);
            $('#txtNumeroColaborador').val(colaborador.numeroColaborador);
            Swal.fire({
                title: 'Movimiento realizado',
                html: `<span>¡Los datos del colaborador se han guardado correctamente! <i class="fa-solid fa-thumbs-up"></i></span>`,
                icon: 'success',
                showClass: {
                    popup: 'animated pulse'
                }
            });
            cleanForm();
        }
    });
}

function refreshTable() {
    var contenido = "";
    
    // Hacemos la petición al Servicio REST que nos consulta los clientes:
    $.ajax({
        type: "GET",
        url: "api/colaborador/getAll",
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
            colaboradores = data;

            //Recorremos el arreglo de clientes posición por posición:
            for (var i = 0; i < colaboradores.length; i++) {
            // Agregamos un nuevo renglon a la tabla contenido sus respectivas columnas y valores:
            contenido = contenido + '<tr>' +
                                    '<td>' + colaboradores[i].numeroColaborador + '</td>' +
                                    '<td>' + colaboradores[i].persona.nombre + ' ' + colaboradores[i].persona.apellidoPaterno + ' ' +
                                             colaboradores[i].persona.apellidoMaterno + ' ' +
                                    '</td>' +
                                    '<td>' + colaboradores[i].persona.rfc + '</td>' +
                                    '<td>' + colaboradores[i].persona.domicilio + '</td>' +
                                    '<td><button class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#divDetalleCliente" onclick="showDetail(' + colaboradores[i].id + ');"><i class="far fa-eye"></i></button>' + '</td>' +
                                    '</tr>';
            }
            //Insertamos el contenido generado previamente dentro del cuerpo de la tabla:
            $('#tbodyColaborador').html(contenido);
        }
    });
}

function cleanForm() {
    $('#txtCodigoColaborador').val('');
    $('#txtCodigoPersona').val('');
    $('#txtCodigoUsuario').val('');
    $('#txtNombre').val('');
    $('#txtApellidoPaterno').val('');
    $('#txtApellidoMaterno').val('');
    $('#txtTelefono').val('');
    $('#txtDomicilio').val('');
    $('#txtUsuario').val('');
    $('#txtContrasenia').val('');
    $('#txtRol').val('');
    $('#txtRfc').val('');
    $('#cmbGenero').val('');
    $('#txtNumeroColaborador').val('');
    $('#txtPuesto').val('');
    $('#txtCodigoImagen').val('');
    $('#imgColaboradorFoto').prop('src', 'media/img/avatar.png');
    var inputFile = document.getElementById('inputFileFoto');
    inputFile.value = '';
}

function uploadPhotography() {
    // Recuperamos el input de tipo File donde se selecciona la foto
    var inputFile = document.getElementById('inputFileFoto');
    //Revisamos que el usuario haya seleccionado un archivo:
    if (inputFile.files && inputFile.files[0]) {
        //Creamos el objeto que leerá la imagen
        var reader = new FileReader();
        //Agregamos un oyente al lector del archivo para que en cuanto el usuario cargue la imagen,
        //esta se lea y se convierta de forma automatica en una cadena de Base64:
        reader.onload = function (e){
            var fotoB64 = e.target.result;
            $("#imgColaboradorFoto").attr("src",fotoB64);
            $("#txtCodigoImagen").val(fotoB64.substring(22,fotoB64.length));
        };
        
        //Leemos el archivo que selecciono el usuario y lo convertimos en una cadena con la Base64
        reader.readAsDataURL(inputFile.files[0]);
    }
}