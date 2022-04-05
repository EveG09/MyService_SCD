/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Función que carga el contenido del módulo de Cita:
function loadAppointmentModule() {
    $.ajax({
        context: document.body,
        url: "gestion/cliente/cita/cita.html"
    }).done(function (data) {
        //  document.getElementById("contenedorPrincipal").innerHTML=data;
        $("#contenedorPrincipal").html(data);
    });
}

