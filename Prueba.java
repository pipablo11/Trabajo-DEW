import java.net.URI;
import java.net.http.*;

public class Prueba{
    public static void main(String args[]){
        URI uri = URI.create("http://DEW-ppicsil-2324.dsicv.upv.es:9090/CentroEducativo/login");
        String postInfo = "{\"dni\":\"111111111\",\"password\":\"654321\"}";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).header("content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(postInfo)).build();
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
        } catch(Exception e){System.out.println(e.getMessage());}
    }
        
} 

//metodo para iniciar usuario --data '{"dni":"111111111","password":"654321"}' -X POST -H "content-type:application/json" http://$ip:9090/CentroEducativo/login -c cucu -b cucu)