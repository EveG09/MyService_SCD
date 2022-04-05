var admin = null;
var colaborador = null;
var cliente = null;

function loadLoginModule() {
    $.ajax({
        context : document.body,
        url: "gestion/login/login.html"
  
    }).done(function(data){
    //  document.getElementById("contenedorPrincipal").innerHTML=data;
        $("#contenedorInicio").html(data);
    }); 
}

function loadStartModule() {
   window.location.replace("index.html");
}

function loginAdmin() {
    // Creamos un nuevo objeto
    var usuario = new Object();
    usuario = new Object();

    usuario.nombreUsuario = $('#txtNombreUsuario').val();
    usuario.contrasenia = $('#txtContrasenia').val();

    $.ajax({
        type: "POST",
        async: true,
        url: "api/login/validateAdmin",
        data: {
                nombreUsuario : usuario.nombreUsuario,
                contrasenia   : usuario.contrasenia
            }
    
    }).done(function (data) {
        if (data == null) {
            Swal.fire({
                title: 'Acceso denegado',
                text: data.error,
                icon: 'warning',
                showClass: {
                    popup: 'animate__animated animate__tada',
                    icon: 'animate__animated animate__heartBeat'
                }
            });
            $('#txtNombreUsuario').val("");
            $('#txtContrasenia').val("");

        // Revisamos si hubo algun error
        } else if (data.error != null) {
            Swal.fire({
                title: 'Acceso denegado',
                text: data.error,
                icon: 'warning',
                showClass: {
                    popup: 'animate__animated animate__tada',
                    icon: 'animate__animated animate__heartBeat'
                }
            });

        } else if (data.id != 0) {
            Swal.fire('Bienvenido usuario', 'Datos correctos', 'success');
            sessionStorage.setItem('admin', JSON.stringify(data));
            sessionStorage.setItem('token', data.usuario.token);
            sessionStorage.setItem('idUsuario', data.usuario.id);
            window.location.replace("main.html");
        }
    });
}

function loginColaborador() {
    // Creamos un nuevo objeto
    var usuario = new Object();
    usuario = new Object();

    usuario.nombreUsuario = $('#txtNombreUsuario').val();
    usuario.contrasenia = $('#txtContrasenia').val();

    $.ajax({
        type: "POST",
        async: true,
        url: "api/login/validateColaborador",
        data: {
                nombreUsuario : usuario.nombreUsuario,
                contrasenia   : usuario.contrasenia
            }
    
    }).done(function (data) {
        if (data == null) {
            Swal.fire({
                title: 'Acceso denegado',
                text: data.error,
                icon: 'warning',
                showClass: {
                    popup: 'animate__animated animate__tada',
                    icon: 'animate__animated animate__heartBeat'
                }
            });
            $('#txtNombreUsuario').val("");
            $('#txtContrasenia').val("");

        // Revisamos si hubo algun error
        } else if (data.error != null) {
            Swal.fire({
                title: 'Acceso denegado',
                text: data.error,
                icon: 'warning',
                showClass: {
                    popup: 'animate__animated animate__tada',
                    icon: 'animate__animated animate__heartBeat'
                }
            });

        } else if (data.id != 0) {
            Swal.fire('Bienvenido usuario', 'Datos correctos', 'success');
            sessionStorage.setItem('colaborador', JSON.stringify(data));
            sessionStorage.setItem('token', data.usuario.token);
            sessionStorage.setItem('idUsuario', data.usuario.id);
            window.location.replace("mainColaborador.html");
        }
    });
}

function loginCliente() {
    // Creamos un nuevo objeto
    var usuario = new Object();
    usuario = new Object();

    usuario.nombreUsuario = $('#txtNombreUsuario').val();
    usuario.contrasenia = $('#txtContrasenia').val();

    $.ajax({
        type: "POST",
        async: true,
        url: "api/login/validateCliente",
        data: {
                nombreUsuario : usuario.nombreUsuario,
                contrasenia   : usuario.contrasenia
            }
    
    }).done(function (data) {
        if (data == null) {
            Swal.fire({
                title: 'Acceso denegado',
                text: data.error,
                icon: 'warning',
                showClass: {
                    popup: 'animate__animated animate__tada',
                    icon: 'animate__animated animate__heartBeat'
                }
            });
            $('#txtNombreUsuario').val("");
            $('#txtContrasenia').val("");

        // Revisamos si hubo algun error
        } else if (data.error != null) {
            Swal.fire({
                title: 'Acceso denegado',
                text: data.error,
                icon: 'warning',
                showClass: {
                    popup: 'animate__animated animate__tada',
                    icon: 'animate__animated animate__heartBeat'
                }
            });

        } else if (data.id != 0) {
            Swal.fire('Bienvenido usuario', 'Datos correctos', 'success');
            sessionStorage.setItem('cliente', JSON.stringify(data));
            sessionStorage.setItem('token', data.usuario.token);
            sessionStorage.setItem('idUsuario', data.usuario.id);
            window.location.replace("mainCliente.html");
        }
    });
}

function validateLogin() {
    if($('#cmbRoles').val() === 'Administrador') {
        loginAdmin();
    
    } else if($('#cmbRoles').val() === 'Colaborador') {
        loginColaborador()();
    
    } else if($('#cmbRoles').val() === 'Cliente') {
        loginCliente();
    }
}

function initializeAdminPage() {
    admin = JSON.parse(sessionStorage.getItem('admin'));
    $('#spanNombre').html(admin.persona.nombre + ' ' + admin.persona.apellidoPaterno);
    $('#spanPuesto').html(admin.usuario.rol);
    $('#imgEmpleadoFoto2').prop('src', 'data:image/png;base64,' + admin.foto);
}

function initializeColaboradorPage() {
    colaborador = JSON.parse(sessionStorage.getItem('colaborador'));
    $('#spanNombre').html(colaborador.persona.nombre + ' ' + colaborador.persona.apellidoPaterno);
    $('#spanPuesto').html(colaborador.usuario.rol);
    $('#imgEmpleadoFoto2').prop('src', 'data:image/png;base64,' + colaborador.foto);
}

function initializeClientePage() {
    cliente = JSON.parse(sessionStorage.getItem('cliente'));
    $('#spanNombre').html(cliente.persona.nombre + ' ' + cliente.persona.apellidoPaterno);
    $('#spanPuesto').html(cliente.usuario.rol);
}

function out() {     
    $.ajax({
        type: "POST",
        async: true,
        url: "api/login/out",
        data: {id : sessionStorage.getItem("idUsuario")}
    
    }).done(function (data) {             
        if (data.result != '') {
            sessionStorage.clear();
            window.location.replace("index.html");

        } else if (data.error != '') {
            Swal.fire("Cierre de sesión fallido", data.error, "error");
        }
    });
}