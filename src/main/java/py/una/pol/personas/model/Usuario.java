package py.una.pol.personas.model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int id;
    private String nombre_usuario = "";

    public Usuario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }
}
