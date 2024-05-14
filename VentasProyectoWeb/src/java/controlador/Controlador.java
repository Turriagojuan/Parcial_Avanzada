package controlador;

import config.GenerarSerie;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import modelo.Cliente;
import modelo.ClienteDAO;
import modelo.Empleado;
import modelo.EmpleadoDAO;
import modelo.Producto;
import modelo.ProductoDAO;
import modelo.Venta;
import modelo.VentaDAO;

public class Controlador extends HttpServlet {

    Empleado em = new Empleado();
    EmpleadoDAO edao = new EmpleadoDAO();

    Producto pr = new Producto();
    ProductoDAO pdao = new ProductoDAO();

    Cliente cl = new Cliente();
    ClienteDAO cdao = new ClienteDAO();

    Venta v = new Venta();
    List<Venta> lista = new ArrayList<>();
    int item, cod, cant;
    String descripcion, numeroserie;
    double precio, subtotal, total;
    VentaDAO vdao = new VentaDAO();

    Cliente c = null;

    int ide;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        String menu = request.getParameter("menu");

        if (menu.equals("Principal")) {
            request.getRequestDispatcher("Principal.jsp").forward(request, response);
        }

        if (menu.equals("Empleado")) {
            switch (accion) {
                case "Listar":
                    List lista = edao.listar();
                    request.setAttribute("empleados", lista);
                    break;

                case "Add":
                    String dni = request.getParameter("txtDni");
                    String nom = request.getParameter("txtNombres");
                    String tel = request.getParameter("txtTel");
                    String est;
                    
                    if(request.getParameter("txtEstado").equals("Activo"))
                        est = "1";
                    else
                        est = "2";
                        
                    String user = request.getParameter("txtUsuario");
                    String pass = asegurarClave(request.getParameter("txtPass"));

                    em.setDni(dni);
                    em.setNom(nom);
                    em.setTel(tel);
                    em.setEstado(est);
                    em.setUser(user);
                    em.setPass(pass);

                    edao.agregar(em);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;

                case "Edit":
                    ide = Integer.parseInt(request.getParameter("id"));
                    Empleado e = edao.listarId(ide);
                    request.setAttribute("empleado", e);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;

                case "Update":
                    String dni2 = request.getParameter("txtDni");
                    String nom2 = request.getParameter("txtNombres");
                    String tel2 = request.getParameter("txtTel");
                    String est2;
                    
                    if(request.getParameter("txtEstado").equals("Activo"))
                        est2 = "1";
                    else
                        est2 = "2";
                    
                    String user2 = request.getParameter("txtUsuario");
                    String pass2 = asegurarClave(request.getParameter("txtPass"));

                    em.setDni(dni2);
                    em.setNom(nom2);
                    em.setTel(tel2);
                    em.setEstado(est2);
                    em.setUser(user2);
                    em.setId(ide);
                    em.setPass(pass2);

                    edao.actualizar(em);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;

                case "Delete":
                    ide = Integer.parseInt(request.getParameter("id"));
                    edao.delete(ide);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;

                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("Empleado.jsp").forward(request, response);
        }

        if (menu.equals("Cliente")) {
            switch (accion) {
                case "Listar":
                    List lista = cdao.listar();
                    request.setAttribute("clientes", lista);
                    break;

                case "Add":
                    String dni = request.getParameter("txtDni");
                    String nom = request.getParameter("txtName");
                    String dir = request.getParameter("txtDir");
                    String est;
                    
                    if(request.getParameter("txtStt").equals("Activo"))
                        est = "1";
                    else
                        est = "2";

                    cl.setDni(dni);
                    cl.setNombres(nom);
                    cl.setDireccion(dir);
                    cl.setEstado(est);

                    cdao.agregar(cl);
                    request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                    break;

                case "Edit":
                    ide = Integer.parseInt(request.getParameter("id"));
                    Cliente c = cdao.listarId(ide);
                    request.setAttribute("cliente", c);
                    request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                    break;

                case "Update":
                    String dni2 = request.getParameter("txtDni");
                    String nom2 = request.getParameter("txtName");
                    String dir2 = request.getParameter("txtDir");
                    String est2;
                    
                    if(request.getParameter("txtStt").equals("Activo"))
                        est2 = "1";
                    else
                        est2 = "2";

                    cl.setDni(dni2);
                    cl.setNombres(nom2);
                    cl.setDireccion(dir2);
                    cl.setEstado(est2);
                    cl.setId(ide);

                    cdao.actualizar(cl);
                    request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                    break;

                case "Delete":
                    ide = Integer.parseInt(request.getParameter("id"));
                    cdao.delete(ide);
                    request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                    break;

                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("Clientes.jsp").forward(request, response);
        }

        if (menu.equals("Producto")) {
            switch (accion) {
                case "Listar":
                    List lista = pdao.listar();
                    request.setAttribute("productos", lista);
                    break;

                case "Add":
                    String desc = request.getParameter("txtDes");
                    String prec = request.getParameter("txtPrice");
                    String stock = request.getParameter("txtSto");
                    String est;
                    
                    if(request.getParameter("txtStt").equals("Disponible"))
                        est = "1";
                    else
                        est = "2";

                    pr.setDescripcion(desc);
                    pr.setPrecio(Double.parseDouble(prec));
                    pr.setStock(Integer.parseInt(stock));
                    pr.setEstado(est);

                    pdao.agregar(pr);
                    request.getRequestDispatcher("Controlador?menu=Producto&accion=Listar").forward(request, response);
                    break;

                case "Edit":
                    ide = Integer.parseInt(request.getParameter("id"));
                    Producto p = pdao.listarId(ide);
                    request.setAttribute("producto", p);
                    request.getRequestDispatcher("Controlador?menu=Producto&accion=Listar").forward(request, response);
                    break;

                case "Update":
                    String desc2 = request.getParameter("txtDes");
                    String prec2 = request.getParameter("txtPrice");
                    String stock2 = request.getParameter("txtSto");
                    String est2;
                    
                    if(request.getParameter("txtStt").equals("Disponible"))
                        est2 = "1";
                    else
                        est2 = "2";

                    pr.setDescripcion(desc2);
                    pr.setPrecio(Double.parseDouble(prec2));
                    pr.setStock(Integer.parseInt(stock2));
                    pr.setEstado(est2);
                    pr.setId(ide);

                    pdao.actualizar(pr);
                    request.getRequestDispatcher("Controlador?menu=Producto&accion=Listar").forward(request, response);
                    break;

                case "Delete":
                    ide = Integer.parseInt(request.getParameter("id"));
                    pdao.delete(ide);
                    request.getRequestDispatcher("Controlador?menu=Producto&accion=Listar").forward(request, response);
                    break;

                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("Producto.jsp").forward(request, response);
        }

        if (menu.equals("NuevaVenta")) {
            switch (accion) {
                case "Search":
                    String dni = request.getParameter("codigocliente");
                    cl.setDni(dni);
                    c = cdao.buscar(dni);
                    request.setAttribute("c", c);
                    request.setAttribute("nserie", numeroserie);
                    break;

                case "ProductSearch":
                    int id = Integer.parseInt(request.getParameter("codigoproducto"));
                    pr = pdao.listarId(id);
                    request.setAttribute("c", c);
                    request.setAttribute("producto", pr);
                    request.setAttribute("total", total);
                    request.setAttribute("lista", lista);
                    request.setAttribute("nserie", numeroserie);
                    break;

                case "Add":
                    request.setAttribute("nserie", numeroserie);
                    request.setAttribute("c", c);
                    total = 0.0;
                    item = item + 1;
                    cod = pr.getId();
                    descripcion = request.getParameter("nomproducto");
                    precio = Double.parseDouble(request.getParameter("precio"));
                    cant = Integer.parseInt(request.getParameter("cant"));
                    subtotal = precio * cant;
                    v = new Venta();
                    v.setItem(item);
                    v.setIdproducto(cod);
                    v.setDescripcionP(descripcion);
                    v.setPrecio(precio);
                    v.setCantidad(cant);
                    v.setSubtotal(subtotal);
                    lista.add(v);

                    for (int i = 0; i < lista.size(); i++) {
                        total += lista.get(i).getSubtotal();
                    }
                    request.setAttribute("total", total);
                    request.setAttribute("lista", lista);
                    break;
                case "GenerarVenta":
                    //ACTUALIZAR STOCK
                    for (int i = 0; i < lista.size(); i++) {
                        Producto p = new Producto();
                        int cantidad = lista.get(i).getCantidad();
                        int idproducto = lista.get(i).getIdproducto();
                        ProductoDAO aO = new ProductoDAO();
                        p = aO.buscar(idproducto);
                        int sac = p.getStock() - cantidad;
                        aO.actualizarstock(idproducto, sac);
                    }
                    //GUARDAR VENTA
                    v.setIdcliente(c.getId());
                    v.setIdempleado(1);
                    v.setNumserie(numeroserie);
                    v.setFecha("2019-06-14");
                    v.setMonto(total);
                    v.setEstado("1");
                    vdao.guardarVenta(v);
                    //GUARDAR DETALLE VENTAS
                    int idv = Integer.parseInt(vdao.IdVentas());
                    for (int i = 0; i < lista.size(); i++) {
                        v = new Venta();
                        v.setId(idv);
                        v.setIdproducto(lista.get(i).getIdproducto());
                        v.setCantidad(lista.get(i).getCantidad());
                        v.setPrecio(lista.get(i).getPrecio());
                        vdao.guardarDetalleventa(v);

                    }

                    break;

                default:
                    v = new Venta();
                    lista = new ArrayList<>();
                    item = 0;
                    total = 0.0;

                    numeroserie = vdao.GenerarSerie();
                    if (numeroserie == null) {
                        numeroserie = "0000001";
                        request.setAttribute("nserie", numeroserie);
                    } else {
                        int incrementar = Integer.parseInt(numeroserie);
                        GenerarSerie gs = new GenerarSerie();
                        numeroserie = gs.NumeroSerie(incrementar);
                        request.setAttribute("nserie", numeroserie);
                    }
                    request.getRequestDispatcher("RegistrarVenta.jsp").forward(request, response);
            }
            request.getRequestDispatcher("RegistrarVenta.jsp").forward(request, response);
        }
    }
    
    public String asegurarClave(String textoClaro) {
        String claveSha = null;

        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(textoClaro.getBytes());
            claveSha = Base64.getEncoder().encodeToString(sha256.digest());
            System.out.println("Clave sha es: " + claveSha);
            System.out.println("Longitud:" + claveSha.length());
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error en instanciar sha256 " + ex.getMessage());
        }

        return claveSha;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
