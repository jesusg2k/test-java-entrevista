'use strict';
let listar_tareas = async() => {
    let tareas;
    await axios({
        method: 'get',
        url: 'http://localhost:8081/personas/rest/tareas/all-active',
        responseType: 'json'
    }).then(function(res) {
        tareas = res.data;
    })
        .catch(function(err) {
            console.log(err);
        });
    return tareas;
};