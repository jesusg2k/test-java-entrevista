'use strict';
const tbody = document.querySelector('#tbl-tareas tbody');
let mostrar_datos = async() => {
    let tareas = await listar_estadistica();
    tbody.innerHTML = '';
    for (let i = 0; i < tareas.length; i++) {
        let fila = tbody.insertRow();
        fila.insertCell().innerHTML = JSON.stringify(tareas[i]);
    }
};
mostrar_datos();