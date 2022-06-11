'use strict';
let listar_estadistica = async() => {
    const datos_estadistica = ["Datos de estadisticas enviados desde API REST"];
    await axios({
        method: 'get',
        url: 'http://localhost:8081/personas/rest/estadisticas/tarea-mas-larga',
        responseType: 'json'
    }).then(function(res) {
        datos_estadistica.push(res.data['msg'] + " => "+res.data['data']);
    }).catch(function(err) {
        datos_estadistica.push('La tarea mas larga es: ERROR API');
            console.log(err);
        });
    await axios({
        method: 'get',
        url: 'http://localhost:8081/personas/rest/estadisticas/tarea-mas-corta',
        responseType: 'json'
    }).then(function(res) {
        datos_estadistica.push(res.data['msg'] + " => "+res.data['data']);
    }).catch(function(err) {
        datos_estadistica.push('La tarea mas corta es:  ERROR API');
        console.log(err);
    });

    await axios({
        method: 'get',
        url: 'http://localhost:8081/personas/rest/estadisticas/cantidad-tareas-concluidas-hoy',
        responseType: 'json'
    }).then(function(res) {
        datos_estadistica.push(res.data['msg'] + " => "+res.data['data']);
    }).catch(function(err) {
        datos_estadistica.push('La cantidad de tareas completadas hoy:  ERROR API');
        console.log(err);
    });


    await axios({
        method: 'get',
        url: 'http://localhost:8081/personas/rest/estadisticas/cantidad-tareas-activadas-hoy',
        responseType: 'json'
    }).then(function(res) {
        datos_estadistica.push(res.data['msg'] + " => "+res.data['data']);
    }).catch(function(err) {
        datos_estadistica.push('La cantidad de tareas creadas hoy: ERROR API');
        console.log(err);
    });



    await axios({
        method: 'get',
        url: 'http://localhost:8081/personas/rest/estadisticas/porcentaje-completado',
        responseType: 'json'
    }).then(function(res) {
        datos_estadistica.push(res.data['msg'] + " => "+res.data['data']);
    }).catch(function(err) {
        datos_estadistica.push('El porcentaje de tareas que se completaron fue: ERROR API');
        console.log(err);
    });




    return datos_estadistica;
};