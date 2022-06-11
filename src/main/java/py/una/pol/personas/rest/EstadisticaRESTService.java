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
@Path("/estadisticas")
@RequestScoped
public class EstadisticaRESTService {

    @Inject
    private Logger log;

    @Inject
    UsuarioService usuarioService;
    @Inject
    TareaService tareaService;


    @Path("/tarea-mas-larga")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_tarea_mas_larga_concluida() {
        Response.ResponseBuilder builder = null;
        try {
            Tarea tarea_mayor_duracion = tareaService.seleccionar_tarea_mayor_duracion();
            if(tarea_mayor_duracion==null){
                Map<String, String> responseObj = getResponse("OK", "200", "No hay tareas concluidas",
                        "");
                builder = Response.status(201).entity(responseObj);
            }else{
                Map<String, String> object = new HashMap<>();
                object.put("id_tarea", tarea_mayor_duracion.getId_tarea()+"");
                object.put("descripcion_tarea", tarea_mayor_duracion.getDescripcion_tarea()+"");

                Map<String, String> responseObj = getResponse("OK", "200", "Tarea con mayor duracion recuperado",
                        object+"");
                builder = Response.status(201).entity(responseObj);
            }
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

    @Path("/tarea-mas-corta")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_tarea_mas_corta_concluida() {
        Response.ResponseBuilder builder = null;
        try {
            Tarea tarea_mayor_duracion = tareaService.seleccionar_tarea_menor_duracion();
            if(tarea_mayor_duracion==null){
                Map<String, String> responseObj = getResponse("OK", "200", "No hay tareas completadas",
                        "");
                builder = Response.status(201).entity(responseObj);
            }else{
                Map<String, String> object = new HashMap<>();
                object.put("id_tarea", tarea_mayor_duracion.getId_tarea()+"");
                object.put("descripcion_tarea", tarea_mayor_duracion.getDescripcion_tarea()+"");
                Map<String, String> responseObj = getResponse("OK", "200", "Tarea con menor duracion recuperado",
                        object+"");
                builder = Response.status(201).entity(responseObj);
            }
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

    @Path("/cantidad-tareas-concluidas-hoy")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_cant_tareas_concluidas_hoy() {
        Response.ResponseBuilder builder = null;
        try {
            int cant_completadas = tareaService.cant_tareas_completadas_hoy();
            Map<String, String> responseObj = getResponse("OK", "200", "Cantidad Tareas concluidas hoy",
                    cant_completadas+"");
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

    @Path("/cantidad-tareas-activadas-hoy")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_cant_tareas_activadas_hoy() {
        Response.ResponseBuilder builder = null;
        try {
            int cant_activadas = tareaService.cant_tareas_activadas_hoy();
            Map<String, String> responseObj = getResponse("OK", "200", "Cantidad Tareas activadas hoy",
                    cant_activadas+"");
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

    @Path("/porcentaje-completado")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get_porcentaje_completado() {
        Response.ResponseBuilder builder = null;
        try {
            int porcentaje_completado = tareaService.porcentaje_completado();
            Map<String, String> responseObj = getResponse("OK", "200", "Porcentaje de Tareas completadas",
                    porcentaje_completado+"");
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





    Map<String, String> getResponse(String status, String code, String msg, String data){
        Map<String, String> responseObj = new HashMap<>();
        responseObj.put("status", status);
        responseObj.put("code", code);
        responseObj.put("msg", msg);
        responseObj.put("data", data);
        return responseObj;
    }

   
}
