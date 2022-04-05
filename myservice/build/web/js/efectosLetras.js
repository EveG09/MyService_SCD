/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let app = document.getElementById('typewriter');

let typewriter = new Typewriter(app, {
    loop: true,
    delay: 75
});

typewriter
        .pauseFor(3500)
        .typeString('Una agencia única para todas tus necesidades del hogar')
        .pauseFor(3500)
        .deleteChars(10)
        .start();

let app2 = document.getElementById('tituloEquipo');

let typewriter2 = new Typewriter(app2, {
    loop: true,
    delay: 75
});

typewriter2
        .pauseFor(3500)
        .typeString('Equipo pequeño con resultados grandes')
        .pauseFor(3500)
        .deleteChars(10)
        .start();

let app3 = document.getElementById('servicios');

let typewriter3 = new Typewriter(app3, {
    loop: true,
    delay: 75
});

typewriter3
        .pauseFor(3500)
        .typeString('Conoce nuestros servicios')
        .pauseFor(3500)
        .deleteChars(10)
        .start();
