/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import co.flock.FlockApiClient;
import co.flock.model.message.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

/**
 *
 * @author athithyan
 */
@WebServlet(urlPatterns = {"/ToneAnalyzerBot"})
public class ToneAnalyzerBot extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ToneAnalyzerBot</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ToneAnalyzerBot at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            System.out.println(request.getContextPath() + request);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } finally {
            reader.close();
        }
        TextToBot(sb.toString());
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } finally {
            reader.close();
        }
        TextToBot(sb.toString());
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public static void TextToBot(String request) throws IOException {

        String postText = Analyzer.greatestTrait(request);
        postText = appropriateReplay(postText);

        HttpClient client = new HttpClient();
        StringRequestEntity requestEntity;
        try {
            requestEntity = new StringRequestEntity("{\n" + "\"to\": \"u:ywxfywyxzxxtrw22\",\n"
                    + "\"text\": \"" + postText + "\"\n" + "}\n", "application/json", "UTF-8");
            PostMethod postMethod = new PostMethod("https://api.flock.co/v1/chat.sendMessage?token=42c627bd-8017-431c-8946-f8df193c48e4");
            postMethod.setRequestEntity(requestEntity);
            client.executeMethod(postMethod);

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ToneAnalyzerBot.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String appropriateReplay(String str) {
        switch (str) {
            case ("Sadness"):
                return "We are sorry to disappoint you, Just tell us how can we serve you ";
            case ("Joy"):
                return "We are so happy to work upto your satisfaction, Let us know, how can we help you";
            case ("Anger"):
                return "You are our first priority, So calm down and help us provide solutions to solve your problem";
            case ("Disgust"):
                return "We feel bad for serving you that way, We assure you to provide a better service from now";
            case ("Fear"):
                return "Is there any technical error you fear for? Reach us out, We are here for you";
        }
        return "";
    }

}
