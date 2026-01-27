package lp.JavaFxClient_Equipa07_rec;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthClient {
	  private static final String BASE_URL = "http://localhost:8080/users";

	    public static String login(String username, String password) {
	        String endpoint = "/login";
	        String json = String.format("""
	                {
	                    "username": "%s",
	                    "password": "%s"
	                }
	                """, username, password);

	        return sendPostRequest(endpoint, json);
	    }

	    public static String register(String username, String password, String email) {
	        String endpoint = "/register";
	        String json = String.format("""
	                {
	                    "username": "%s",
	                    "password": "%s",
	                    "email": "%s"
	                }
	                """, username, password, email);

	        return sendPostRequest(endpoint, json);
	    }

	    public static String changePassword(String username, String oldPassword, String newPassword) {
	        String endpoint = "/change-password";
	        String json = String.format("""
	                {
	                    "username": "%s",
	                    "oldPassword": "%s",
	                    "newPassword": "%s"
	                }
	                """, username, oldPassword, newPassword);

	        return sendPostRequest(endpoint, json);
	    }

	    private static String sendPostRequest(String endpoint, String json) {
	        try {
	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(BASE_URL + endpoint))
	                    .header("Content-Type", "application/json")
	                    .POST(HttpRequest.BodyPublishers.ofString(json))
	                    .build();

	            //HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
	            //return response.statusCode() == 200;
	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            if (response.statusCode() == 200 || response.statusCode() == 201) {
	                return response.body(); // return the JSON body
	            } else {
	                System.err.println("Request failed: " + response.statusCode() + " - " + response.body());
	                return null;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Error";
	        }
	    }
}
