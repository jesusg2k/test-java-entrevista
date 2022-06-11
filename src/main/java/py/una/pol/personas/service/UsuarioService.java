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


import py.una.pol.personas.dao.Bd;
import py.una.pol.personas.dao.TareasDAO;
import py.una.pol.personas.dao.UsuarioDao;
import py.una.pol.personas.model.Tarea;
import py.una.pol.personas.model.Usuario;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class UsuarioService {

    @Inject
    private Logger log;

    @Inject
    private UsuarioDao dao;

    @Inject
    private TareasDAO dao_tareas;


    public void crear_usuario(Usuario usuario) throws Exception {
        log.info("Creando usuario: " + usuario.getNombre_usuario() + " ");
        try {
        	dao.insertar(usuario);
        }catch(Exception e) {
        	log.severe("ERROR al crear usuario: " + e.getLocalizedMessage() );
        	throw e;
        }
        log.info("Usuario creado con éxito: " + usuario.getNombre_usuario());
    }

    public Usuario seleccionarPorId(Long id_usuario) {
        return dao.seleccionarPorIdUsuario(id_usuario);
    }



    public void asignar_tarea_a_usuario(Tarea tarea) throws Exception {
        log.info("Asignando tarea: " + tarea.getId_tarea() + " a Usuario ID: " +tarea.getId_usuario());
        try {
            List<Tarea> tareas = dao_tareas.seleccionar();
            int cant_tareas_activas = 0;
            Long id_usuario_validar = tarea.getId_usuario();
            log.info("Chequeando tareas activas");
            for(Tarea t: tareas){
                if(id_usuario_validar.equals(t.getId_usuario()) && !t.isTarea_completada()){
                    cant_tareas_activas++;
                }
            }
            if(cant_tareas_activas>=5){
                throw new Exception("Este usuario tiene 5 tareas activas");
            }
            log.info("Este usuario tiene "+cant_tareas_activas+" tareas activas");
            dao.asignar_tarea_a_usuario(tarea);
        }catch(Exception e) {
            log.severe("ERROR al crear tarea: " + e.getLocalizedMessage() );
            throw e;
        }
        log.info("Tarea asignada con éxito: (ID TAREA - " + tarea.getId_tarea()+")");
    }

    public List<Usuario> listar() {
        String query = "SELECT id_usuario, nombre_usuario FROM usuario";

        List<Usuario> lista = new ArrayList<>();

        Connection conn = null;
        try
        {
            conn = Bd.connect();
            ResultSet rs = conn.createStatement().executeQuery(query);

            while(rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id_usuario"));
                u.setNombre_usuario(rs.getString("nombre_usuario"));
                lista.add(u);
            }
        } catch (SQLException ex) {
            log.severe("Error en la seleccion: " + ex.getMessage());
        }
        finally  {
            try{
                conn.close();
            }catch(Exception ef){
                log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
            }
        }
        return lista;
    }
}
