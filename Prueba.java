import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.http.*;

public class Prueba{
    private static final String host = "http://DEW-aasepie-2324.dsicv.upv.es:9090/CentroEducativo";
    public static void main(String args[])throws Exception{
        CookieManager cm = new CookieManager();
        HttpClient client = HttpClient.newBuilder().cookieHandler(cm).build();
        
        String key = login(client);
        CookieManager manager = (CookieManager)client.cookieHandler().get();
        System.out.println(manager.getCookieStore().getCookies());

        getSubjects(key, client);
    }

    public static String login(HttpClient client) throws IOException, InterruptedException{
        URI uri = URI.create(host+"/login");
        String dni = "12345678W";
        String pas = "123456";
        //String postInfo = "{\"dni\":\"111111111\",\"password\":\"654321\"}";
        String postInfo = "{\"dni\":\""+dni+"\",\"password\":\""+pas+"\"}";
        HttpRequest request = HttpRequest.newBuilder().uri(uri).header("content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(postInfo)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        
        
        System.out.println("{*}"+response.body());

        //add cookie
        HttpCookie cookie = new HttpCookie("JSESSIONID", response.body().toString());
        cookie.setPath("./");
        CookieManager cm = (CookieManager) client.cookieHandler().get();
        cm.getCookieStore().add(uri, cookie);

        return response.body();
    }

    public static void list(String key, HttpClient client) throws IOException, InterruptedException{
        URI uri = URI.create("http://DEW-aasepie-2324.dsicv.upv.es:9090/CentroEducativo/alumnos?key="+key);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).header("content-type", "application/json").GET().build();
        HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(res.body());
    }


    public static void getSubjects(String key, HttpClient client) throws IOException, InterruptedException{
        URI uri = URI.create(host+"/alumnos/12345678/asignaturas?key="+key);
        HttpRequest request = HttpRequest.newBuilder().GET().header("content-type", "application/json").uri(uri).build();
        HttpResponse<String> res= client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(res.body());
    }

    /* Métodos que se necesitan
     *  log in: --data '{"dni":"111111111","password":"654321"}' -X POST -H "content-type:application/json" http://$ip:9090/CentroEducativo/login -c cucu -b cucu)
     *  listar: curl -s -X GET 'http://127.0.0.1:9090/CentroEducativo/alumnos?key='$(cat key) -H "accept: application/json" -c cucu -b cucu 
     */
        
} 

