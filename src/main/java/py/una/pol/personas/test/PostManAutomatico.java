package py.una.pol.personas.test;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.Assert.assertFalse;

public class PostManAutomatico {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while(true){
            mostrar_menu_principal();
            System.out.println("Seleccione la opción: ");
            try{
                int op = scanner.nextInt();
                scanner.nextLine();
                if(op >= 1 && op <= 8){
                    procesar_opcion(op);
                }else{
                    if(op == 9){
                        break;
                    }
                    System.out.println("La opción es invalida");
                }
            }catch(Exception e){
                scanner.nextLine();
                System.out.println("Ocurrió un error al leer la opcion"+e);
            }
        }
    }


    private static void mostrar_menu_principal() {
        System.out.println("Menu Principal");
        System.out.println("1. Alta nueva tarea ");
        System.out.println("2. Baja de tarea");
        System.out.println("3. Editar descripcion tarea");
        System.out.println("4. Marcar tarea como completada");
        System.out.println("5. Eliminar todas las tareas completadas");
        System.out.println("6. Ver Lista de Usuarios");
        System.out.println("7. Crear Usuario Prueba");
        System.out.println("8. Asignar usuario a tarea");
        System.out.println("9. Salir");
    }
    private static void procesar_opcion(int op) {
        if(op == 1) crear_tarea();
        if(op == 2) eliminar_tarea();
        if(op == 3) actualizar_tarea();
        if(op == 4) marcar_como_completada();
        if(op == 5) eliminar_tareas_completadas();
        if(op == 6) ver_lista_usuarios();
        if(op == 7) crear_usuario_prueba();
        if(op == 8) asignar_tarea_a_usuario();

    }

    private static void eliminar_tareas_completadas() {
        try{
            realizar_peticion_POST("tareas/deletealltareas", "DELETE", "");
        }catch(Exception e){
            System.out.println("Ocurrió un error inesperado: "+e);
        }
    }

    private static void marcar_como_completada() {
        try{
            System.out.println("Ingrese el id de la tarea a completar: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            JSONObject json = new JSONObject();
            json.put("id_tarea", id);
            realizar_peticion_POST("tareas/tocomplete", "POST", json.toString());
        }catch(Exception e){
            System.out.println("Ocurrió un error inesperado: "+e);
        }
    }

    private static void eliminar_tarea() {
        try{
            System.out.println("Ingrese el id de la tarea a eliminar: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            JSONObject json = new JSONObject();
            json.put("id_tarea", id);
            realizar_peticion_POST("tareas", "DELETE", json.toString());
        }catch(Exception e){
            System.out.println("Ocurrió un error inesperado: "+e);
        }
    }

    private static void asignar_tarea_a_usuario() {
        try{
            System.out.println("Ingrese el id de la tarea a asignar: ");
            int id_tarea = scanner.nextInt();
            System.out.println("Ingrese el id del usuario a asignar: ");
            int usuario_id = scanner.nextInt();
            scanner.nextLine();
            JSONObject json = new JSONObject();
            json.put("id_tarea", id_tarea);
            json.put("id_usuario", usuario_id);
            realizar_peticion_POST("asignaciontareas", "POST", json.toString());
        }catch(Exception e){
            System.out.println("Ocurrió un error inesperado: "+e);
        }
    }

    private static void crear_usuario_prueba() {
        try{
            JSONObject json = new JSONObject();
            json.put("nombre_usuario", "Juan Pablo");
            realizar_peticion_POST("asignaciontareas/crear-usuario", "POST", json.toString());
        }catch(Exception e){
            System.out.println("Ocurrió un error inesperado: "+e);
        }
    }

    private static void ver_lista_usuarios() {
        try {
            URL url= new URL("http://localhost:"+TestUnitario.puerto+"/personas/rest/asignaciontareas/all-usuarios");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("GET");

            boolean respuesta_fallida = conn.getResponseCode() < 200  || conn.getResponseCode() >= 300;
            if (conn.getResponseCode() < 200  || conn.getResponseCode() >= 300 ) {
                System.out.println("Error HTTP - código : " + conn.getResponseCode()+" : "+conn.getResponseMessage());
            }
            if(conn.getResponseCode()==404){
                System.out.println("Verifique que el puerto del endpoint sea: "+TestUnitario.puerto);
            }
            assertFalse(respuesta_fallida);

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            String usuarios = "";
            System.out.println("Impresión del contenido de la respuesta: \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                usuarios += output;
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void crear_tarea() {

        try{
            System.out.println("Ingrese la descripcion de la nueva tarea: ");
            String descripcion = scanner.nextLine();
            JSONObject json = new JSONObject();
            json.put("descripcion_tarea", descripcion);
            realizar_peticion_POST("tareas", "POST", json.toString());
        }catch(Exception e){
            System.out.println("Ocurrió un error inesperado: "+e);
        }
    }


    private static void actualizar_tarea() {

        try{
            System.out.println("Ingrese el ID de la tarea a actualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Ingrese la descripcion de la nueva tarea: ");
            String descripcion = scanner.nextLine();
            JSONObject json = new JSONObject();
            json.put("id_tarea", id);
            json.put("descripcion_tarea", descripcion);
            realizar_peticion_POST("tareas/update", "POST", json.toString());
        }catch(Exception e){
            System.out.println("Ocurrió un error inesperado: "+e);
        }
    }

    private static void realizar_peticion_POST(String ruta,String verbose_http, String payload) throws Exception {
        try{
            System.out.println(" payload => "+payload );
            System.out.println("http://localhost:"+TestUnitario.puerto+"/personas/rest/"+ruta);
            URL url = new URL("http://localhost:"+TestUnitario.puerto+"/personas/rest/"+ruta);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(verbose_http);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept", "application/json");

            byte[] out = payload.getBytes(StandardCharsets.UTF_8);
            OutputStream stream = connection.getOutputStream();
            stream.write(out);
            System.out.println(connection.getResponseCode() + " " + connection.getResponseMessage()); // THis is optional

            boolean respuesta_fallida = connection.getResponseCode() < 200  || connection.getResponseCode() >= 300;
            if (connection.getResponseCode() < 200  || connection.getResponseCode() >= 300 ) {
                System.out.println("Error HTTP - código : " + connection.getResponseCode()+" : "+connection.getResponseMessage());
            }
            if(connection.getResponseCode()==404){
                System.out.println("Verifique que el puerto del endpoint sea: "+TestUnitario.puerto);
            }
            connection.disconnect();
        }catch (Exception e){
            System.out.println("Ocurrió un error: "+e);
        }
    }


}
