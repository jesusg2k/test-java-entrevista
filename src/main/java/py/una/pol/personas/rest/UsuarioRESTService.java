/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package py.una.pol.personas.rest;

import py.una.pol.personas.model.Tarea;
import py.una.pol.personas.model.Usuario;
import py.una.pol.personas.service.TareaService;
import py.una.pol.personas.service.UsuarioService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * JAX-RS Example
 * <p/>
 * Esta clase produce un servicio RESTful para leer y escribir contenido de personas
 */
@Path("/asignaciontareas")
@RequestScoped
public class UsuarioRESTService {

    @Inject
    private Logger log;

    @Inject
    UsuarioService usuarioService;
    @Inject
    TareaService tareaService;

    @Path("/all-usuarios")
    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public List<Usuario> listar() {
        return usuarioService.listar();
    }


    @Path("/crear-usuario")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crear_usuario(Usuario usuario) {
        Response.ResponseBuilder builder = null;
        try {
            usuarioService.crear_usuario(usuario);
            Map<String, String> responseObj = getResponse("OK", "201", "El usuario se creó exitosamente",
                    "usuario: { " +
                            "nombre = "+usuario.getNombre_usuario()+"}");
            builder = Response.status(201).entity(responseObj);
        } catch (SQLException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = getResponse("ERROR", "500", e.getMessage(), "");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = getResponse("ERROR", "400", e.getMessage(), "");
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return builder.build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response asignar_tarea_a_usuario(Tarea tarea) {
        Response.ResponseBuilder builder = null;
        try {
            if(tarea.getId_tarea() == null){
                throw new Exception("No se recibió el id de la tarea");
            }
            log.info("Se recibió el ID TAREA");
            if(tarea.getId_usuario() == null){
                throw new Exception("No se recibió el id del usuario");
            }
            log.info("Se recibió el ID Usuario");

            log.info("Se realizó la busqueda por ID del Usuario");
            Usuario usuario_tmp = usuarioService.seleccionarPorId(tarea.getId_usuario());
            if(usuario_tmp == null) {
                throw new Exception("No existe usuario con el id");
            }

            try{
                log.info("Se realizó la busqueda por ID de la Tarea");
                long id_tarea = tarea.getId_tarea();
                System.out.println("Se buscará la tarea con ID => "+id_tarea);
                Tarea tarea_tmp = tareaService.seleccionarPorIdTarea(id_tarea);
                if(tarea_tmp == null) {
                    throw new Exception("No existe tarea con el id");
                }
            }catch (Exception e){
                log.warning("OCURRIÓ ERROR AL REALIZAR LA BUSQUEDA: "+e.getMessage()+" "+e);
                throw e;
            }

            log.info("Se va asignar la tarea");
            usuarioService.asignar_tarea_a_usuario(tarea);
            Map<String, String> responseObj = getResponse("OK", "200", "Tarea asignada a Usuario",
                    "tarea: { " +
                            "id_tarea: "+tarea.getId_tarea() + " , " +
                            "id_usuario: "+tarea.getId_usuario());
            builder = Response.status(200).entity(responseObj);

        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = getResponse("ERROR", "500",  e.getMessage(), "tarea: { " +
                    "id_tarea: "+tarea.getId_tarea() + " , " +
                    "id_usuario: "+tarea.getId_usuario());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return builder.build();
    }



    Map<String, String> getResponse(String status, String code, String msg, String data){
        Map<String, String> responseObj = new HashMap<>();
        responseObj.put("status", status);
        responseObj.put("code", code);
        responseObj.put("msg", msg);
        responseObj.put("data", data);
        return responseObj;
    }

   
}
