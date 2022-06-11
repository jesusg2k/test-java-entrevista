package py.una.pol.personas.model;

import java.io.Serializable;
import java.sql.Timestamp;


public class Tarea implements Serializable {

	Long id_tarea;
	String descripcion_tarea="";

	Timestamp fecha_creacion =null;
	Timestamp fecha_completado =null;
	boolean tarea_completada;

	Long id_usuario;

	public Tarea(Long pcedula, String pnombre){
		this.id_tarea = pcedula;
		this.descripcion_tarea = pnombre;
	}

	public Tarea() {

	}

	public Long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public Timestamp getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(Timestamp fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	public Timestamp getFecha_completado() {
		return fecha_completado;
	}

	public void setFecha_completado(Timestamp fecha_completado) {
		this.fecha_completado = fecha_completado;
	}

	public boolean isTarea_completada() {
		return tarea_completada;
	}

	public void setTarea_completada(boolean tarea_completada) {
		this.tarea_completada = tarea_completada;
	}

	public Long getId_tarea() {
		return id_tarea;
	}

	public void setId_tarea(Long id_tarea) {
		this.id_tarea = id_tarea;
	}

	public String getDescripcion_tarea() {
		return descripcion_tarea;
	}

	public void setDescripcion_tarea(String descripcion_tarea) {
		this.descripcion_tarea = descripcion_tarea;
	}


}
