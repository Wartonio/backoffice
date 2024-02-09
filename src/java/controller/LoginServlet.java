/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author tchirwesh
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            try{          
                RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                dispatcher.forward(request, response);
            }catch(Exception e){
                e.printStackTrace();
                throw new ServletException(e);
            }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            String email = request.getParameter("email");
            String password = request.getParameter("password");
        
            
             // URL du contrôleur
            String apiUrl = "http://localhost:8085/api/login"; // Remplacez cela par l'URL correcte de votre serveur

            // Utilisation de HttpClient pour effectuer la requête POST
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Configurer la requête
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Construire le corps de la requête avec les informations d'authentification
                String jsonInputString = "{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}";

                // Envoyer les données
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Lire la réponse
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder responseContent = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        responseContent.append(responseLine.trim());
                    }

                    // Traiter la réponse comme nécessaire
                    String apiResponse = responseContent.toString();
                    System.out.println(apiResponse);

                    // Vous pouvez maintenant traiter la réponse de l'API comme nécessaire
                    // ...

                    // Envoyer la réponse au client
                    //response.getWriter().write(apiResponse);
                    response.sendRedirect("HomeServlet");
                }

                // Fermer la connexion
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().write("Erreur lors de l'appel de l'API");
            }
    }

    
}
