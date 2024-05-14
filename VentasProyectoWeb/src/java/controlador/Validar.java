package controlador;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import modelo.Empleado;
import modelo.EmpleadoDAO;

public class Validar extends HttpServlet {

    EmpleadoDAO edao = new EmpleadoDAO();
    Empleado em = new Empleado();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
         System.out.println("juan: ");
        if (accion.equalsIgnoreCase("Log In")) {
            
            String user = request.getParameter("txtuser");
            String pass = request.getParameter("txtpassword");

            System.out.println("clave: "+pass);
            
            Empleado item = new Empleado();

            item.setUser(user);
            item.setPass(pass);

            em = edao.validar(item);
            
            if (em.getUser() != null) {
                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                HttpSession sesion = request.getSession();

                System.out.println("Sesion: " + sesion.getId());

                sesion.setAttribute("user", em);

                request.getRequestDispatcher("Controlador?menu=Principal").forward(request, response);

            } else {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        }

        if (accion.equalsIgnoreCase("Log Out")) {
            HttpSession sesion = request.getSession();
            sesion.removeAttribute("user");
            sesion.invalidate();
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.resetBuffer();
            response.reset();
            request.getRequestDispatcher("Controlador?menu=Principal").forward(request, response);
        } else {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    

        if (accion.equalsIgnoreCase("Log Out")) {
            HttpSession sesion = request.getSession();
            sesion.removeAttribute("user");
            sesion.invalidate();
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.resetBuffer();
            response.reset();
            request.getRequestDispatcher("Controlador?menu=Principal").forward(request, response);
        } else {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
