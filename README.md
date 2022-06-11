Laboratorio de WebServices

**Elementos a utilizar**
* JavaEE, JDK 1.8 
* IDE de preferencia, utilizado IDEA INTELIJ para este proyecto. 
* Instalar el motor de base de datos Mysql
* Servidor wildfly-26.1.1.Final

**Importar proyecto**
 * Abrir el IDE IDEA
 * Menú File -> Open -> Buscar la carpeta 'personas' y abrirlo como Maven Project.
 * Finalizar
 * El IDE comenzará a importar las librerías de Maven 
 * (ó en todo caso entrar en pom.xml y sincronizar las dependencias)
 
**Base de datos**
 * Crear una base de datos llamada db_tareas.
 * Configurar los datos de configuración y acceso en la clase "Bd.java" dentro del package 'dao'.


``` 
 Reemplazar root y password por sus contraseñas del Mysql Server.
 mysql -uroot -ppassword
 create database db_tareas
 use db_tareas
 source "..\personas\src\main\java\py\una\pol\personas\bdscript\backup-tareas" 
 (se puede arrastrar el archivo .sql a la consola o importarlo desde otro programa)
```


**Servidor wildfly-26.1.1.Final**
* Se puede descargar desde su página oficial, una vez descargado, se debe descomprimir
* Por motivos de que a veces el puerto 8080 puede estar ocupado (justamente tuve ese problema)
* Se va hacer un offset de 1 puerto, es decir que si normalmente iba a trabajar en :8080, ahora lo hará en :8081
* Buscar en archivo en C:\Users\User\Documents\Entrevista\servidor\wildfly-26.1.1.Final\standalone\configuration\standalone.xml
* Por defecto: <socket-binding-group name="standard-sockets" default-interface="public" port-offset="${jboss.socket.binding.port-offset:0}"
* Cambiar el último 0 por 1 y se aplicará el offset al reiniciar el iniciar el servidor.
* <socket-binding-group name="standard-sockets" default-interface="public" port-offset="${jboss.socket.binding.port-offset:1}"
* En caso de que el puerto de Wildfly no sea 8081, se deberá modificar en "TestUnitario" la variable puerto por el correspondiente

** Para iniciar el servidor **
* Para iniciar el servidor Wildfly podemos ejecutar manualmente el archivo ubicado en: 
* "wildfly-26.1.1.Final\bin\standalone.bat"


* Para hacer el despliegue de nuestra aplicación web y API REST. 
* Debemos colocar el artifact "personas.war" en la carpeta "wildfly-26.1.1.Final\standalone\deployments" de Wildfly.
* Automáticamente, va empezar a desplegar la aplicación y en segundas estará en linea.
* El archivo "personas.war" lo puedes encontrar en este proyecto en "\personas\target\personas.war"


** APLICACIÓN WEB ** 
* Para acceder a la aplicación web puedes hacerlo mediante el navegador ingresando en
* http://localhost:8081/personas/index.html
* En la aplicación web se encontrarán 4 vistas diferentes con tablas de resultados
* Todas las tareas, tareas completadas, tareas activas y el "dashboard" de estadísticas
* recordando que 8081 es el puerto que este usando wildfly.

**Mini POSTMAN Incluido**
```
Menu Principal
1. Alta nueva tarea
2. Baja de tarea
3. Editar descripcion tarea
4. Marcar tarea como completada
5. Eliminar todas las tareas completadas
6. Ver Lista de Usuarios
7. Crear Usuario Prueba
8. Asignar usuario a tarea
9. Salir
```

* Para facilitar algunas pruebas se realizó una clase ejecutable que interactua con la API REST implementada en el backend.

**Deployar en Servidor**
 * Desde el IDE Eclipse, configurar el servidor de aplicaciones Wildfly (Verificar la guía de clase anterior sobre el laboratorio de servidor de aplicaciones JavaEE)
 * Deploy del proyecto "personas" en el servidor Wildfly


**END POINTS**


**SERVICIO REST DE ESTADISTICAS**
 * GET http://localhost:8080/personas/rest/estadisticas/tarea-mas-larga
 * GET http://localhost:8080/personas/rest/estadisticas/tarea-mas-corta
 * GET http://localhost:8080/personas/rest/estadisticas/cantidad-tareas-concluidas-hoy
 * GET http://localhost:8080/personas/rest/estadisticas/cantidad-tareas-activadas-hoy
 * GET http://localhost:8080/personas/rest/estadisticas/porcentaje-completado

**SERVICIO REST DE ASIGNACION TAREAS**
* GET http://localhost:8080/personas/rest/asignaciontareas/all-usuarios
* POST http://localhost:8080/personas/rest/asignaciontareas/crear-usuario
* POST http://localhost:8080/personas/rest/asignaciontareas/ -> ASIGNA USUARIO A TAREA


**SERVICIO REST DE ASIGNACION TAREAS**
* GET http://localhost:8080/personas/rest/tareas/all-active -> DEVUELVE TAREAS ACTIVAS
* GET http://localhost:8080/personas/rest/tareas/all-completed -> DEVUELVE TAREAS COMPLETADAS 
* POST http://localhost:8080/personas/rest/tareas/ -> CREA NUEVA TAREA
* POST http://localhost:8080/personas/rest/tareas/tocomplete -> MARCA COMO COMPLETADO
* POST http://localhost:8080/personas/rest/tareas/update -> ACTUALIZA TAREA
* DELETE http://localhost:8080/personas/rest/tareas -> ELIMINA UNA TAREA
* DELETE http://localhost:8080/personas/rest/tareas/deletealltareas -> ELIMINA TODAS LAS TAREAS COMPLETADAS


	 