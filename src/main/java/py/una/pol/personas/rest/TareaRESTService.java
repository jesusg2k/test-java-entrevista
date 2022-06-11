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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import py.una.pol.personas.model.Tarea;
import py.una.pol.personas.service.TareaService;

/**
 * JAX-RS Example
 * <p/>
 * Esta clase produce un servicio RESTful para leer y escribir contenido de personas
 */
@Path("/tareas")
@RequestScoped
public class TareaRESTService {

    @Inject
    private Logger log;

    @Inject
    TareaService tareaService;

    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public List<Tarea> listar() {
        return tareaService.seleccionar();
    }


    @Path("/all-active")
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Tarea> listar_activas() {
        List<Tarea> all_tasks = tareaService.seleccionar();
        return filtro_tareas_completadas(all_tasks, false);
    }

    @Path("/all-completed")
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Tarea> listar_tareas_completadas() {
        List<Tarea> all_tasks = tareaService.seleccionar();
        return filtro_tareas_completadas(all_tasks, true);
    }


    List<Tarea> filtro_tareas_completadas(List<Tarea> all_tasks, boolean filtro_de_completado){
        ArrayList<Tarea> tareas_filtradas = new ArrayList<>();
        for(Tarea tarea: all_tasks){
            if(tarea.isTarea_completada() && filtro_de_completado){
                tareas_filtradas.add(tarea);
            }

            if(!tarea.isTarea_completada() && !filtro_de_completado){
                tareas_filtradas.add(tarea);
            }
        }
        return tareas_filtradas;
    }




    /**
     * Creates a new member from the values provided. Performs validation, and will return a JAX-RS response with either 200 ok,
     * or with a map of fields, and related errors.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crear(Tarea tarea) {
        Response.ResponseBuilder builder = null;
        try {
            tareaService.crear(tarea);
            // Create an "ok" response
            //builder = Response.ok();
            Map<String, String> responseObj = getResponse("OK", "201", "La tarea se creó exitosamente",
                    "tarea_enviada: { " +
                            "descripcion_tarea = "+tarea.getDescripcion_tarea()+"}");
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
    @Path("/tocomplete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response marcar_completado(Tarea tarea)
    {
        Response.ResponseBuilder builder = null;
        try {
            if(tareaService.seleccionarPorIdTarea(tarea.getId_tarea()) == null) {
                Map<String, String> responseObj = getResponse("ERROR", "500", "La tarea no existe",
                        "tarea_enviada: { " +
                                "id_tarea: "+tarea.getId_tarea());
                builder = Response.status(Response.Status.NOT_ACCEPTABLE).entity(responseObj);
            }else {
                tareaService.marcar_completada(tarea);
                Map<String, String> responseObj = getResponse("OK", "200", "La tarea se marcó como completada",
                        "tarea_enviada: { " +
                                "id_tarea: "+tarea.getId_tarea() + " , " +
                                "is_complete: "+tarea.isTarea_completada()+"}");
                builder = Response.status(202).entity(responseObj);
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

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar_descripcion(Tarea tarea)
    {
        Response.ResponseBuilder builder = null;
        try {
            Tarea tarea_tmp = tareaService.seleccionarPorIdTarea(tarea.getId_tarea());
            if(tarea_tmp == null) {
                Map<String, String> responseObj = getResponse("ERROR", "500", "Tarea no existe",
                        "tarea: { " +
                                "id_tarea: "+tarea.getId_tarea() + "" +
                                "descripcion_tarea: "+tarea.getDescripcion_tarea()+"}");
                builder = Response.status(Response.Status.NOT_ACCEPTABLE).entity(responseObj);
            }else {
                tareaService.actualizar_descripcion(tarea);
                Map<String, String> responseObj = getResponse("OK", "200", "Tarea actualizada",
                        "tarea: { " +
                        "id_tarea: "+tarea.getId_tarea() + "" +
                        "descripcion_tarea: "+tarea.getDescripcion_tarea()+"}");
                builder = Response.status(202).entity(responseObj);
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

    Map<String, String> getResponse(String status, String code, String msg, String data){
        Map<String, String> responseObj = new HashMap<>();
        responseObj.put("status", status);
        responseObj.put("code", code);
        responseObj.put("msg", msg);
        responseObj.put("data", data);
        return responseObj;
    }


   @DELETE
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response borrar(Tarea tarea)
   {      
	   Response.ResponseBuilder builder = null;
	   try {
		   if(tareaService.seleccionarPorIdTarea(tarea.getId_tarea()) == null) {
               Map<String, String> responseObj = getResponse("ERROR", "500", "La tarea no existe",
                       "tarea_enviada: { " +
                               "id_tarea: "+tarea.getId_tarea() + "}");
			   builder = Response.status(Response.Status.NOT_ACCEPTABLE).entity(responseObj);
		   }else {
			   tareaService.borrar(tarea.getId_tarea());
               Map<String, String> responseObj = getResponse("OK", "200", "La tarea se borró exitosamente",
                       "tarea_enviada: { " +
                               "id_tarea: "+tarea.getId_tarea() + "}");
               builder = Response.status(202).entity(responseObj);
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


    @Path("/deletealltareas")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete_all_tareas_completadas()
    {
        Response.ResponseBuilder builder = null;
        try {
            tareaService.borrar_all_tareas();
            Map<String, String> responseObj = getResponse("OK", "200", "Todas las tareas completadas fueron borradas exitosamente",
                    "");
            builder = Response.status(202).entity(responseObj);

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
   
}
