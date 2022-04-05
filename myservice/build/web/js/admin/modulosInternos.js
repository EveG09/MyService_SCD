/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Función que carga el contenido del módulo de Cliente:
function loadClientModule() {
    $.ajax({
        context: document.body,
        url: "gestion/admin/cliente/cliente.html"
    }).done(function (data) {
        //  document.getElementById("contenedorPrincipal").innerHTML=data;
        $("#contenedorPrincipal").html(data);
    });
}

// Función que carga el contenido del módulo de Colaborador:
function loadContributorModule() {
    $.ajax({
        context: document.body,
        url: "gestion/admin/colaborador/colaborador.html"
    }).done(function (data) {
        //  document.getElementById("contenedorPrincipal").innerHTML=data;
        $("#contenedorPrincipal").html(data);
    });
}

// Función que carga el contenido del módulo de Reparación:
function loadRepairModule() {
    $.ajax({
        context: document.body,
        url: "gestion/admin/reparacion/reparacion.html"
    }).done(function (data) {
        //  document.getElementById("contenedorPrincipal").innerHTML=data;
        $("#contenedorPrincipal").html(data);
    });
}

// Función que carga el contenido del módulo de Horario:
function loadHourModule() {
    $.ajax({
        context: document.body,
        url: "gestion/admin/horario/horario.html"
    }).done(function (data) {
        //  document.getElementById("contenedorPrincipal").innerHTML=data;
        $("#contenedorPrincipal").html(data);
    });
}

// Función que carga el contenido del módulo de Material:
function loadMaterialModule() {
    $.ajax({
        context: document.body,
        url: "gestion/admin/material/material.html"
    }).done(function (data) {
        //  document.getElementById("contenedorPrincipal").innerHTML=data;
        $("#contenedorPrincipal").html(data);
    });
}

// Función que carga el contenido del módulo de Cita:
function loadAppointmentModule() {
    $.ajax({
        context: document.body,
        url: "gestion/admin/cita/cita.html"
    }).done(function (data) {
        //  document.getElementById("contenedorPrincipal").innerHTML=data;
        $("#contenedorPrincipal").html(data);
    });
}

// Función que carga el contenido del módulo de Servicio:
function loadServiceModule() {
    $.ajax({
        context: document.body,
        url: "gestion/admin/servicio/servicio.html"
    }).done(function (data) {
        //  document.getElementById("contenedorPrincipal").innerHTML=data;
        $("#contenedorPrincipal").html(data);
    });
}

function closeModule() {
    $('#contenedorPrincipal').html('');
}
