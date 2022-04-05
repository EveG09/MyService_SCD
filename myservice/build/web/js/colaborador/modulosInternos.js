/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Función que carga el contenido del módulo de Reparación:
function loadRepairModule() {
    $.ajax({
        context: document.body,
        url: "gestion/colaborador/reparacion/reparacion.html"
    }).done(function (data) {
        //  document.getElementById("contenedorPrincipal").innerHTML=data;
        $("#contenedorPrincipal").html(data);
    });
}

// Función que carga el contenido del módulo de Horario:
function loadHourModule() {
    $.ajax({
        context: document.body,
        url: "gestion/colaborador/horario/horario.html"
    }).done(function (data) {
        //  document.getElementById("contenedorPrincipal").innerHTML=data;
        $("#contenedorPrincipal").html(data);
    });
}

// Función que carga el contenido del módulo de Material:
function loadMaterialModule() {
    $.ajax({
        context: document.body,
        url: "gestion/colaborador/material/material.html"
    }).done(function (data) {
        //  document.getElementById("contenedorPrincipal").innerHTML=data;
        $("#contenedorPrincipal").html(data);
    });
}

// Función que carga el contenido del módulo de Cita:
function loadAppointmentModule() {
    $.ajax({
        context: document.body,
        url: "gestion/colaborador/cita/cita.html"
    }).done(function (data) {
        //  document.getElementById("contenedorPrincipal").innerHTML=data;
        $("#contenedorPrincipal").html(data);
    });
}