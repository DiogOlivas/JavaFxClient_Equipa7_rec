package lp.JavaFxClient.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiService {
     private static final String BASE_URL = "http://localhost:8080/";
     private final HttpClient client = HttpClient.newHttpClient();

     public String get(String path) {
     try {
         HttpRequest request = HttpRequest.newBuilder()
             .uri(URI.create(BASE_URL + path))
             .GET()
             .build();
         return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
         } catch (Exception e) {
             return "ERROR: " + e.getMessage();
             }
         }

     public String post(String path, String json) {
         try {
         HttpRequest.BodyPublisher body =
             (json == null || json.isEmpty())
                 ? HttpRequest.BodyPublishers.noBody()
                 : HttpRequest.BodyPublishers.ofString(json);
         HttpRequest request = HttpRequest.newBuilder()
             .uri(URI.create(BASE_URL + path)) 
             .header("Content-Type", "application/json")
             .POST(body)
             .build();
         return client.send(request, HttpResponse.BodyHandlers.ofString()).body(); 
         }catch (Exception e) {
             return "ERROR: " + e.getMessage();
             }
         }
         public String put(String path, String json) {
        try {
        HttpRequest.BodyPublisher body =
            (json == null || json.isEmpty())
                ? HttpRequest.BodyPublishers.noBody()
                : HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + path))
            .header("Content-Type", "application/json")
            .PUT(body)
            .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        }catch (Exception e) {
            return "ERROR: " + e.getMessage();
            }
        }

         public String delete(String path) {
         try {
             HttpRequest request = HttpRequest.newBuilder()
                 .uri(URI.create(BASE_URL + path))
                     .DELETE()
                     .build();
         String body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
         return body == null ? "" : body;

         } catch (Exception e) {
             return "ERROR: " + e.getMessage();
             }
         }
}