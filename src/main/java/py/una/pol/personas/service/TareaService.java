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
package py.una.pol.personas.service;


import javax.ejb.Stateless;
import javax.inject.Inject;

import py.una.pol.personas.dao.TareasDAO;
import py.una.pol.personas.model.Tarea;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class TareaService {

    @Inject
    private Logger log;

    @Inject
    private TareasDAO dao;

    public void crear(Tarea p) throws Exception {
        log.info("Creando tarea: " + p.getDescripcion_tarea() + " ");
        try {
        	dao.insertar(p);
        }catch(Exception e) {
        	log.severe("ERROR al crear tarea: " + e.getLocalizedMessage() );
        	throw e;
        }
        log.info("Tarea creada con éxito: " + p.getDescripcion_tarea() );
    }


    public void actualizar_descripcion(Tarea p) throws Exception {
        log.info("Actualizando tarea: " + p.getId_tarea() + " - " +p.getDescripcion_tarea() + " "+ " Completado: "+((p.isTarea_completada())?"SI":"NO"));
        try {
            dao.actualizar_descripcion(p);
        }catch(Exception e) {
            log.severe("ERROR al crear tarea: " + e.getLocalizedMessage() );
            throw e;
        }
        log.info("Tarea actualizada con éxito: " + p.getDescripcion_tarea() );
    }

    public void marcar_completada(Tarea p) throws Exception {
        log.info("Actualizando tarea: " + p.getId_tarea() + " - " +p.getDescripcion_tarea() + " "+ " Completado: "+((p.isTarea_completada())?"SI":"NO"));
        try {
            dao.marcar_completada(p);
        }catch(Exception e) {
            log.severe("ERROR al crear tarea: " + e.getLocalizedMessage() );
            throw e;
        }
        log.info("Tarea creada con éxito: " + p.getDescripcion_tarea() );


    }
    
    public List<Tarea> seleccionar() {
    	return dao.seleccionar();
    }

    public Tarea seleccionar_tarea_mayor_duracion() throws Exception {
        try{
            return dao.seleccionarTareaMayorDuracion();
        }catch(Exception e){
            log.warning("Error en function seleccionarID - TareaService");
            throw new Exception("Error al conseguir la tarea");
        }
    }


    public Tarea seleccionar_tarea_menor_duracion() throws Exception {
        try{
            return dao.seleccionarTareaMenorDuracion();
        }catch(Exception e){
            log.warning("Error en function seleccionarID - TareaService");
            throw new Exception("Error al conseguir la tarea");
        }
    }

    public Tarea seleccionarPorIdTarea(long id_tarea) throws Exception {
        try{
            log.info("Se invocará el dao.seleccionarIdTarea");
            return dao.seleccionarPorIdTarea(id_tarea);
        }catch(Exception e){
            log.warning("Error en function seleccionarID - TareaService");
            throw new Exception("Error al conseguir la tarea");
        }
    }

    public int cant_tareas_completadas_hoy() throws Exception {
        return dao.cantidad_tareas_concluidas_hoy();
    }

    public int cant_tareas_activadas_hoy() throws Exception {
        return dao.cantidad_tareas_activadas_hoy();
    }

    public int porcentaje_completado() throws Exception {
        return dao.porcentaje_completado();
    }
    
    public long borrar(long id_tarea) throws Exception {
    	return dao.borrar(id_tarea);
    }

    public long borrar_all_tareas() throws Exception {
        return dao.borrar_todas_las_tareas();
    }
}
