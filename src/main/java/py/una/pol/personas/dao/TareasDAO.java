package py.una.pol.personas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import py.una.pol.personas.model.Tarea;

@Stateless
public class TareasDAO {
 
	
    @Inject
    private Logger log;
    

	public List<Tarea> seleccionar() {
		String query = "SELECT id_tarea, descripcion_tarea, fecha_creada, fecha_completada, tarea_completada, usuario_id  FROM tarea";
		
		List<Tarea> lista = new ArrayList<>();
		
		Connection conn = null; 
        try 
        {
        	conn = Bd.connect();
        	ResultSet rs = conn.createStatement().executeQuery(query);

        	while(rs.next()) {
                Tarea t = convertirTarea(rs);
        		lista.add(t);
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

    static Tarea convertirTarea(ResultSet rs) throws SQLException {
        Tarea p = new Tarea();
        p.setId_tarea(rs.getLong("id_tarea"));
        p.setDescripcion_tarea(rs.getString("descripcion_tarea"));
        p.setFecha_creacion(rs.getTimestamp("fecha_creada"));
        p.setFecha_completado(rs.getTimestamp("fecha_completada"));
        p.setTarea_completada(rs.getBoolean("tarea_completada"));
        p.setId_usuario(rs.getLong("usuario_id"));
        return p;
    }


    public Tarea seleccionarPorIdTarea(long id_tarea) throws SQLException {
        System.out.println("Se va buscar la tarea: "+id_tarea);
		String SQL = "SELECT * FROM tarea WHERE id_tarea = ? ";
		Tarea p = null;
		Connection conn = null; 
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
        	pstmt.setInt(1, (int) id_tarea);
        	ResultSet rs = pstmt.executeQuery();
        	while(rs.next()) {
                p = convertirTarea(rs);
        	}
            System.out.println("Tarea encontrado = "+(p==null?p+"":p.getId_tarea()));
        } catch (SQLException ex) {
        	log.severe("Error en la seleccion: " + ex.getMessage());
            throw ex;
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
		return p;

	}

    public int cantidad_tareas_concluidas_hoy() throws Exception {
        String SQL = "SELECT count(*) as concluida_dia FROM tarea where date(fecha_completada) = date(CURRENT_TIMESTAMP)";
        Tarea p = null;
        Connection conn = null;
        try
        {
            int cant = 0;
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                cant = rs.getInt("concluida_dia");
            }
            return cant;
        } catch (Exception ex) {
            log.severe("Error en la seleccion: " + ex.getMessage());
            throw ex;
        }
        finally  {
            try{
                conn.close();
            }catch(Exception ef){
                log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
            }
        }
    }

    public int porcentaje_completado() throws Exception {
        String SQL_completado = "SELECT count(*) as total_completado FROM tarea where tarea_completada = 1";
        String SQL_total = "SELECT count(*) as total_existente FROM tarea";

        Connection conn = null;
        try
        {
            int cant_total = 0;
            int cant_completado = 0;
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL_completado);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                cant_completado = rs.getInt("total_completado");
            }

            PreparedStatement pstmt2 = conn.prepareStatement(SQL_total);
            ResultSet rs2 = pstmt2.executeQuery();
            while(rs2.next()) {
                cant_total = rs2.getInt("total_existente");
            }

            if(cant_total!=0){
                return (int) ((float)cant_completado/(float)cant_total*100);
            }else{
                return 0;
            }
        } catch (Exception ex) {
            log.severe("Error en la seleccion: " + ex.getMessage());
            throw ex;
        }
        finally  {
            try{
                conn.close();
            }catch(Exception ef){
                log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
            }
        }
    }

    public int cantidad_tareas_activadas_hoy() throws Exception {
        String SQL = "SELECT count(*) as activadas_hoy FROM tarea where date(fecha_creada) = date(CURRENT_TIMESTAMP)";
        Tarea p = null;
        Connection conn = null;
        try
        {
            int cant = 0;
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                cant = rs.getInt("activadas_hoy");
            }
            return cant;
        } catch (Exception ex) {
            log.severe("Error en la seleccion: " + ex.getMessage());
            throw ex;
        }
        finally  {
            try{
                conn.close();
            }catch(Exception ef){
                log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
            }
        }
    }

    public Tarea seleccionarTareaMayorDuracion() throws Exception {
        String SQL = "SELECT * FROM tarea where tarea_completada = 1 order  by  (fecha_completada - fecha_creada) desc limit 1;";
        Tarea p = null;
        Connection conn = null;
        try
        {
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                p = convertirTarea(rs);
            }
            System.out.println("Tarea encontrado = "+(p==null?p+"":p.getId_tarea()));
        } catch (Exception ex) {
            log.severe("Error en la seleccion: " + ex.getMessage());
            throw ex;
        }
        finally  {
            try{
                conn.close();
            }catch(Exception ef){
                log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
            }
        }
        return p;
    }



    public Tarea seleccionarTareaMenorDuracion() throws Exception {
        String SQL = "SELECT * FROM tarea where tarea_completada = 1 order  by  (fecha_completada - fecha_creada) asc limit 1;";
        Tarea p = null;
        Connection conn = null;
        try
        {
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                p = convertirTarea(rs);
            }
            System.out.println("Tarea encontrado = "+(p==null?p+"":p.getId_tarea()));
        } catch (Exception ex) {
            log.severe("Error en la seleccion: " + ex.getMessage());
            throw ex;
        }
        finally  {
            try{
                conn.close();
            }catch(Exception ef){
                log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
            }
        }
        return p;

    }

	
    public long insertar(Tarea p) throws SQLException {
        String SQL = "INSERT INTO tarea(descripcion_tarea) VALUES(?)";
        long id = 0;
        Connection conn = null;
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, p.getDescripcion_tarea());
 
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                	throw ex;
                }
            }
        } catch (SQLException ex) {
        	throw ex;
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
        return id;
    }
	


    public long actualizar_descripcion(Tarea p) throws Exception {
        String SQL = "UPDATE tarea SET descripcion_tarea = ? WHERE id_tarea = ?";
 
        long id = 0;
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, p.getDescripcion_tarea());
            pstmt.setLong(2, p.getId_tarea());
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                return 1;
            }else{
                throw new Exception("No se ha actualizado ninguna tarea");
            }
        } catch (SQLException ex) {
        	log.severe("Error en la actualizacion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
        return id;
    }


    public long marcar_completada(Tarea p) throws Exception {
        String SQL = "UPDATE tarea SET tarea_completada = 1, fecha_completada = CURRENT_TIMESTAMP WHERE id_tarea = ? and tarea_completada = 0";
        long id = 0;
        Connection conn = null;
        try
        {
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, p.getId_tarea());
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                return 1;
            }else{
                throw new Exception("No se ha actualizado ninguna tarea");
            }
        } catch (SQLException ex) {
            log.severe("Error en la actualizacion: " + ex.getMessage());
        }
        finally  {
            try{
                conn.close();
            }catch(Exception ef){
                log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
            }
        }
        return id;
    }

    public long borrar_todas_las_tareas() throws Exception {

        String SQL = "DELETE FROM tarea WHERE id_tarea > 0 and tarea_completada = 1";
        Connection conn = null;

        try
        {
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL);

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                return 1;
            }else{
                throw new Exception("No se ha eliminado ningun registro");
            }
        } catch (Exception ex) {
            log.severe("Error en la eliminación: " + ex.getMessage());
            throw ex;
        }
        finally  {
            try{
                conn.close();
            }catch(Exception ef){
                log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
                throw ef;
            }
        }
    }


    public long borrar(long id_tarea) throws Exception {

        String SQL = "DELETE FROM tarea WHERE id_tarea = ? ";
 
        long id = 0;
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, id_tarea);
 
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                return 1;
            }else{
                throw new Exception("No se ha eliminado ningun registro");
            }
        } catch (Exception ex) {
        	log.severe("Error en la eliminación: " + ex.getMessage());
        	throw ex;
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		log.severe("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        		throw ef;
        	}
        }
    }
    

}
