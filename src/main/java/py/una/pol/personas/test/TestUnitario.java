package py.una.pol.personas.test;

import static org.junit.Assert.*;

import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TestUnitario {
    /* Es el puerto del endpoint, por defecto en wildfly puede ser 8080
    * hay que cambiarlo en caso de cambiar el numero del puerto o hacer un offset.  */
    public static int puerto = 8081;

    @Test
    public void test_JUnit() {
        /*Vamos a verificar crear una nueva tarea, verificar la lista de tareas y completar la ultima. */
        String tareas_json = "";
        try{
            System.out.println("Test 1:");
            try{
                tareas_json = test_get_lista_tareas();
            }catch(Exception e){
                System.out.println("Ocurrió un error al conseguir las tareas "+e);
                throw e;
            }
            System.out.println("\n\n");
            System.out.println("Test 2:");
            try{
                crear_nueva_tarea();
            }catch(Exception e){
                System.out.println("Ocurrió al crear la nueva tarea "+e);
                throw e;
            }
            System.out.println("\n\n");
            System.out.println("Test 3:");
            try{
                JSONArray jsonarray = new JSONArray(tareas_json);
                /*verificamos que la lista no este vacia (deberia tener al menos el objeto creado recientemente)*/
                assertNotEquals(jsonarray.length(),0);
                JSONObject jsonobject = jsonarray.getJSONObject(jsonarray.length()-1);
                System.out.println("Se va completar la tarea => "+jsonobject.toString());
                int id_tarea = jsonobject.getInt("id_tarea");
                tarea_a_completar(id_tarea);
            }catch (Exception e){
                System.out.println("Ocurrió al completar la tarea"+e);
                throw e;
            }
        }catch (Exception e){
            System.out.println("Test cancelado por error: "+e);
        }





      /*  System.out.println("This is the testcase in this class");
        String str1="This is the testcase in this class";
        assertEquals("This is the testcase in this class", str1);*/
    }


    private void tarea_a_completar(int id_tarea) throws JSONException, IOException {
        try{
            URL url = new URL("http://localhost:"+puerto+"/personas/rest/tareas/tocomplete");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept", "application/json");
            JSONObject json = new JSONObject();
            int x = (int) (System.currentTimeMillis()%100);
            json.put("id_tarea", id_tarea);
            String payload = json.toString();
            byte[] out = payload.getBytes(StandardCharsets.UTF_8);
            OutputStream stream = connection.getOutputStream();
            stream.write(out);
            System.out.println(connection.getResponseCode() + " " + connection.getResponseMessage()); // THis is optional
            boolean respuesta_fallida = connection.getResponseCode() < 200  || connection.getResponseCode() >= 300;
            if (connection.getResponseCode() < 200  || connection.getResponseCode() >= 300 ) {
                System.out.println("Error HTTP - código : " + connection.getResponseCode()+" : "+connection.getResponseMessage());
            }
            if(connection.getResponseCode()==404){
                System.out.println("Verifique que el puerto del endpoint sea: "+puerto);
            }
            assertFalse(respuesta_fallida);
            System.out.println("Tarea completada con éxito");
            connection.disconnect();
        }catch (Exception e){
            throw e;
        }
    }
    private void crear_nueva_tarea() throws JSONException, IOException {
        try{
            URL url = new URL("http://localhost:"+puerto+"/personas/rest/tareas");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Accept", "application/json");
            JSONObject json = new JSONObject();
            int x = (int) (System.currentTimeMillis()%100);
            json.put("descripcion_tarea", "TAREA GENERADA POR TEST - ALEATORIO "+x);
            String payload = json.toString();
            byte[] out = payload.getBytes(StandardCharsets.UTF_8);
            OutputStream stream = connection.getOutputStream();
            stream.write(out);
            System.out.println(connection.getResponseCode() + " " + connection.getResponseMessage()); // THis is optional

            boolean respuesta_fallida = connection.getResponseCode() < 200  || connection.getResponseCode() >= 300;
            if (connection.getResponseCode() < 200  || connection.getResponseCode() >= 300 ) {
                System.out.println("Error HTTP - código : " + connection.getResponseCode()+" : "+connection.getResponseMessage());
            }
            if(connection.getResponseCode()==404){
                System.out.println("Verifique que el puerto del endpoint sea: "+puerto);
            }
            assertFalse(respuesta_fallida);
            System.out.println("Tarea creada con éxito");
            connection.disconnect();
        }catch (Exception e){
            throw e;
        }
    }

    private String test_get_lista_tareas() throws IOException {
        try {
            URL url= new URL("http://localhost:"+puerto+"/personas/rest/tareas");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("GET");

            boolean respuesta_fallida = conn.getResponseCode() < 200  || conn.getResponseCode() >= 300;
            if (conn.getResponseCode() < 200  || conn.getResponseCode() >= 300 ) {
                System.out.println("Error HTTP - código : " + conn.getResponseCode()+" : "+conn.getResponseMessage());
            }
            if(conn.getResponseCode()==404){
                System.out.println("Verifique que el puerto del endpoint sea: "+puerto);
            }
            assertFalse(respuesta_fallida);

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            String tareas_json = "";
            System.out.println("Impresión del contenido de la respuesta: \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                tareas_json += output;
            }
            conn.disconnect();
            System.out.println("Lista de tareas obtenidas exitosamente");
            return tareas_json;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}