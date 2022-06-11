package py.una.pol.personas.dao;

import py.una.pol.personas.model.Tarea;
import py.una.pol.personas.model.Usuario;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UsuarioDao {
 
	
    @Inject
    private Logger log;

	public static Usuario convertirUsuario(ResultSet rs) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setId(rs.getInt("id_usuario"));
		usuario.setNombre_usuario(rs.getString("nombre_usuario"));
		return usuario;
	}

	public Usuario seleccionarPorIdUsuario(long id_usuario) {
		String SQL = "SELECT id_usuario, nombre_usuario  FROM usuario WHERE id_usuario = ? ";
		
		Usuario usuario = null;
		
		Connection conn = null; 
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
        	pstmt.setInt(1, (int) id_usuario);
        	ResultSet rs = pstmt.executeQuery();

        	while(rs.next()) {
               usuario = convertirUsuario(rs);
        	}
			System.out.println("Usuario encontrado = "+(usuario==null?usuario+"":usuario.getId()));
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
		return usuario;

	}

	
    public long insertar(Usuario p) throws SQLException {
        String SQL = "INSERT INTO usuario(nombre_usuario) VALUES(?)";
        long id = 0;
        Connection conn = null;
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, p.getNombre_usuario());
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


    public int asignar_tarea_a_usuario(Tarea tarea) throws Exception {
		String SQL = "UPDATE tarea SET usuario_id = ? WHERE id_tarea = ?";
		Connection conn = null;
		try
		{
			conn = Bd.connect();
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setLong(1, tarea.getId_usuario());
			pstmt.setLong(2, tarea.getId_tarea());
			System.out.println("sql a ejecutar => "+SQL);
			int affectedRows = pstmt.executeUpdate();
			// check the affected rows
			if (affectedRows > 0) {
				return 1;
			}else{
				throw new Exception("No se ha actualizado ninguna tarea");
			}
		} catch (Exception ex) {
			log.severe("Error en la actualizacion: " + ex.getMessage());
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
}
