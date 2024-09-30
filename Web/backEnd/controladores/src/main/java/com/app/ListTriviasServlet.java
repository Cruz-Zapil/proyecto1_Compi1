package com.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.recursos.ConexionDB;
import com.app.recursosdb.trivia.Trivia;
import com.google.gson.Gson;

@WebServlet("/list-trivias")

public class ListTriviasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Obtener el nombre de usuario desde la sesión
        HttpSession session = request.getSession(false);
        String idUser = (String) session.getAttribute("idUser");

        // Lógica para obtener las trivias del usuario
        List<Trivia> trivi = new ArrayList<>();

        ConexionDB conexionDB = new ConexionDB();
        conexionDB.recopilarArchivos();
        conexionDB.analizarTrivia();
      
        for (Trivia trivia : conexionDB.getListaTriva()) {

            if (trivia.getUsuario().equals(idUser)) {
                trivi.add(trivia);
            }   
        }

        // Convertir la lista a JSON usando una librería como Gson
        Gson gson = new Gson();
        String trivias = gson.toJson(trivi);

        // Enviar la respuesta JSON
        response.getWriter().write(trivias);

    }
}
