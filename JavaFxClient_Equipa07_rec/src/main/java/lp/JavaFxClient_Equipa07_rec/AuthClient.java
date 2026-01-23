package lp.JavaFxClient_Equipa07_rec;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthClient {
    public static boolean login(String username, String password) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            String json = String.format("""
                {
                    "username": "%s",
                    "password": "%s"
                }
            """, username, password);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/users/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<Void> response =
                    client.send(request, HttpResponse.BodyHandlers.discarding());

            return response.statusCode() == 200;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

