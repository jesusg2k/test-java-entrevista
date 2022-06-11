'use strict';
const tbody = document.querySelector('#tbl-tareas tbody');
let mostrar_datos = async() => {
    let tareas = await listar_tareas();
    tbody.innerHTML = '';
    for (let i = 0; i < tareas.length; i++) {
        let fila = tbody.insertRow();
        fila.insertCell().innerHTML = tareas[i]['id_tarea'];
        fila.insertCell().innerHTML = tareas[i]['descripcion_tarea'];
        fila.insertCell().innerHTML = tareas[i]['fecha_creacion'];
        fila.insertCell().innerHTML = tareas[i]['fecha_completado'];
        fila.insertCell().innerHTML = tareas[i]['tarea_completada'];
        fila.insertCell().innerHTML = tareas[i]['id_usuario'];
    }
};
mostrar_datos();